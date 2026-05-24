package com.example.myapplication.player

import com.example.myapplication.data.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter

class MusicPlayer(private val settingsRepository: SettingsRepository) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _currentTrack = MutableStateFlow<String?>(null)
    val currentTrack = _currentTrack.asStateFlow()

    private val _currentTrackId = MutableStateFlow<Long?>(null)
    val currentTrackId = _currentTrackId.asStateFlow()

    private val _currentQueueTrack = MutableStateFlow<QueueTrack?>(null)
    val currentQueueTrack = _currentQueueTrack.asStateFlow()

    private val _positionMs = MutableStateFlow(0L)
    val positionMs = _positionMs.asStateFlow()

    private val _durationMs = MutableStateFlow(0L)
    val durationMs = _durationMs.asStateFlow()

    private val _repeatMode = MutableStateFlow(REPEAT_MODE_OFF)
    val repeatMode = _repeatMode.asStateFlow()

    private val _shuffleEnabled = MutableStateFlow(false)
    val shuffleEnabled = _shuffleEnabled.asStateFlow()

    private val _volume = MutableStateFlow(100)
    val volume = _volume.asStateFlow()

    private var queue: List<QueueTrack> = emptyList()
    private var currentIndex: Int = -1

    private val mediaPlayerFactory = MediaPlayerFactory()
    private val mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer()
    private val playedShuffleIndices = mutableSetOf<Int>()

    init {
        mediaPlayer.events().addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
            override fun playing(mediaPlayer: MediaPlayer?) {
                _isPlaying.value = true
            }

            override fun paused(mediaPlayer: MediaPlayer?) {
                _isPlaying.value = false
            }

            override fun stopped(mediaPlayer: MediaPlayer?) {
                _isPlaying.value = false
            }

            override fun timeChanged(mediaPlayer: MediaPlayer?, newTime: Long) {
                _positionMs.value = newTime
            }

            override fun lengthChanged(mediaPlayer: MediaPlayer?, newLength: Long) {
                if (newLength > 0) {
                    _durationMs.value = newLength
                }
            }

            override fun finished(mediaPlayer: MediaPlayer?) {
                _isPlaying.value = false
                scope.launch(Dispatchers.Main) {
                    onTrackFinished()
                }
            }

            override fun error(mediaPlayer: MediaPlayer?) {
                System.err.println("[MusicPlayer] VLCJ Media Player Error")
                _isPlaying.value = false
                scope.launch(Dispatchers.Main) {
                    onTrackFinished()
                }
            }
        })

        // Listen for equalizer setting changes
        scope.launch {
            settingsRepository.equalizerEnabled.collect { enabled ->
                syncEqualizer()
            }
        }

        // Setup bands update collection
        scope.launch {
            settingsRepository.equalizerPreset.collect {
                syncEqualizer()
            }
        }
    }

    fun playQueue(tracks: List<QueueTrack>, startIndex: Int) {
        if (tracks.isEmpty()) return
        queue = tracks
        playedShuffleIndices.clear()
        val safeIndex = startIndex.coerceIn(0, tracks.lastIndex)
        playTrack(safeIndex)
    }

    fun updateQueue(tracks: List<QueueTrack>) {
        val oldTrack = queue.getOrNull(currentIndex)
        queue = tracks
        val newTrack = queue.getOrNull(currentIndex)
        if (newTrack != null && oldTrack != null && oldTrack.id == newTrack.id) {
            if (oldTrack.url.startsWith("soundcloud://") && !newTrack.url.startsWith("soundcloud://")) {
                playTrack(currentIndex)
            }
        }
    }

    fun togglePlayPause() {
        if (mediaPlayer.status().isPlaying()) {
            mediaPlayer.controls().pause()
            _isPlaying.value = false
        } else {
            mediaPlayer.controls().play()
            _isPlaying.value = true
        }
    }

    fun seekTo(positionMs: Long) {
        mediaPlayer.controls().setTime(positionMs)
        _positionMs.value = positionMs
    }

    fun skipNext() {
        if (queue.isEmpty()) return
        if (_shuffleEnabled.value) {
            playTrack(queue.indices.random())
        } else {
            val nextIndex = currentIndex + 1
            if (nextIndex < queue.size) {
                playTrack(nextIndex)
            } else {
                playTrack(0)
            }
        }
    }

    fun skipPrevious() {
        if (queue.isEmpty()) return
        if (_positionMs.value > 5000L) {
            seekTo(0)
            return
        }
        if (_shuffleEnabled.value) {
            playTrack(queue.indices.random())
        } else {
            val prevIndex = currentIndex - 1
            if (prevIndex >= 0) {
                playTrack(prevIndex)
            } else {
                playTrack(queue.lastIndex)
            }
        }
    }

    fun cycleRepeatMode() {
        val nextMode = when (_repeatMode.value) {
            REPEAT_MODE_OFF -> REPEAT_MODE_ALL
            REPEAT_MODE_ALL -> REPEAT_MODE_ONE
            else -> REPEAT_MODE_OFF
        }
        _repeatMode.value = nextMode
    }

    fun toggleShuffle() {
        val enabled = !_shuffleEnabled.value
        _shuffleEnabled.value = enabled
        if (enabled) {
            playedShuffleIndices.clear()
            playedShuffleIndices.add(currentIndex)
        }
    }

    fun setVolume(value: Int) {
        val safe = value.coerceIn(0, 100)
        _volume.value = safe
        try {
            mediaPlayer.audio().setVolume(safe)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getNextMediaItemIndex(): Int {
        if (queue.isEmpty()) return -1
        val currentMode = _repeatMode.value
        if (currentMode == REPEAT_MODE_ONE) return currentIndex
        if (_shuffleEnabled.value) {
            val remaining = queue.indices.filterNot { playedShuffleIndices.contains(it) || it == currentIndex }
            if (remaining.isNotEmpty()) return remaining.random()
            return queue.indices.random()
        } else {
            val next = currentIndex + 1
            if (next < queue.size) return next
            if (currentMode == REPEAT_MODE_ALL) return 0
            return -1
        }
    }

    fun release() {
        scope.cancel()
        mediaPlayer.release()
        mediaPlayerFactory.release()
    }

    private fun playTrack(index: Int) {
        if (index !in queue.indices) return
        currentIndex = index
        val track = queue[index]
        _currentQueueTrack.value = track

        mediaPlayer.controls().stop()

        if (track.url.startsWith("soundcloud://")) {
            println("[MusicPlayer] Track ${track.id} has unresolved URL: ${track.url}, waiting for resolution...")
            _currentTrack.value = track.title
            _currentTrackId.value = track.id
            _isPlaying.value = false
            _positionMs.value = 0L
            _durationMs.value = 0L
            return
        }

        try {
            var finalUrl = track.url
            if (finalUrl.startsWith("file:")) {
                try {
                    val uri = java.net.URI(finalUrl)
                    finalUrl = java.io.File(uri).absolutePath
                } catch (e: Exception) {
                    finalUrl = finalUrl.substringAfter("file:")
                    if (finalUrl.startsWith("//")) {
                        finalUrl = finalUrl.substring(2)
                    }
                }
            }

            println("[MusicPlayer] Playing track ${track.id}: '${track.title}'")
            println("[MusicPlayer]   URL: ${finalUrl.take(120)}...")

            _currentTrack.value = track.title
            _currentTrackId.value = track.id
            _positionMs.value = 0L
            _durationMs.value = 0L

            syncEqualizer()
            mediaPlayer.media().play(finalUrl)
            _isPlaying.value = true
        } catch (e: Exception) {
            System.err.println("[MusicPlayer] Exception playing track: ${track.title}")
            e.printStackTrace()
            scope.launch(Dispatchers.Main) {
                onTrackFinished()
            }
        }
    }

    private fun onTrackFinished() {
        val currentMode = _repeatMode.value
        if (currentMode == REPEAT_MODE_ONE) {
            playTrack(currentIndex)
            return
        }

        if (queue.isEmpty()) return

        if (_shuffleEnabled.value) {
            playedShuffleIndices.add(currentIndex)
            if (playedShuffleIndices.size >= queue.size) {
                playedShuffleIndices.clear()
                if (currentMode == REPEAT_MODE_OFF) {
                    _isPlaying.value = false
                    return
                }
            }
            val remainingIndices = queue.indices.filterNot { playedShuffleIndices.contains(it) }
            if (remainingIndices.isNotEmpty()) {
                val nextIndex = remainingIndices.random()
                playTrack(nextIndex)
            } else {
                playTrack(queue.indices.random())
            }
        } else {
            val nextIndex = currentIndex + 1
            if (nextIndex < queue.size) {
                playTrack(nextIndex)
            } else {
                if (currentMode == REPEAT_MODE_ALL) {
                    playTrack(0)
                } else {
                    _isPlaying.value = false
                }
            }
        }
    }

    private fun syncEqualizer() {
        val enabled = settingsRepository.equalizerEnabled.value
        if (!enabled) {
            mediaPlayer.audio().setEqualizer(null)
            return
        }

        val equalizer = mediaPlayerFactory.equalizer().newEqualizer()
        val mapping = mapOf(
            0 to 1,
            1 to 3,
            2 to 5,
            3 to 7,
            4 to 9
        )

        mapping.forEach { (ourBandIdx, vlcBandIdx) ->
            val levelMilliBels = settingsRepository.getBandLevel(ourBandIdx)
            val gainDb = (levelMilliBels / 100.0f).coerceIn(-20.0f, 20.0f)
            equalizer.setAmp(vlcBandIdx, gainDb)
        }

        equalizer.setAmp(0, equalizer.amp(1))
        equalizer.setAmp(2, (equalizer.amp(1) + equalizer.amp(3)) / 2f)
        equalizer.setAmp(4, (equalizer.amp(3) + equalizer.amp(5)) / 2f)
        equalizer.setAmp(6, (equalizer.amp(5) + equalizer.amp(7)) / 2f)
        equalizer.setAmp(8, (equalizer.amp(7) + equalizer.amp(9)) / 2f)

        mediaPlayer.audio().setEqualizer(equalizer)
    }

    data class QueueTrack(
        val id: Long,
        val url: String,
        val title: String,
        val artist: String,
        val artworkUrl: String?
    )

    companion object {
        const val REPEAT_MODE_OFF = 0
        const val REPEAT_MODE_ALL = 2
        const val REPEAT_MODE_ONE = 1
    }
}
