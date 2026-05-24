package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import com.example.myapplication.data.SettingsRepository
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.DownloadState
import com.example.myapplication.data.FavoriteTrack
import com.example.myapplication.data.MixSection
import com.example.myapplication.data.SoundCloudMix
import com.example.myapplication.data.SoundCloudTrack
import com.example.myapplication.data.Playlist
import com.example.myapplication.data.SoundCloudApi
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.QueueMusic
import kotlin.math.max
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.foundation.Image
import org.jetbrains.skia.Image as SkiaImage
import java.net.URL
import java.io.File
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.web.LoadingState

// Desktop Compatibility Layers & Helpers

@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    // No-op on desktop
}

fun Modifier.statusBarsPadding() = this
fun Modifier.navigationBarsPadding() = this
fun Modifier.systemBarsPadding() = this

object LocalContext {
    val current: Any? = null
}

class MockHapticFeedback {
    fun performHapticFeedback(type: Any?) {}
}

object LocalHapticFeedback {
    val current = MockHapticFeedback()
}

object HapticFeedbackType {
    val LongPress = Any()
}

object Toast {
    const val LENGTH_SHORT = 0
    const val LENGTH_LONG = 1
    fun makeText(context: Any?, text: String, duration: Int): Toast {
        println("Toast: $text")
        return this
    }
    fun show() {}
}

private val imageCache = java.util.concurrent.ConcurrentHashMap<String, ImageBitmap>()

@Composable
fun AsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val bitmap = rememberImageLoader(model)
    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    } else {
        Box(modifier = modifier.background(Color.White.copy(alpha = 0.05f)))
    }
}

@Composable
fun SubcomposeAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    loading: @Composable () -> Unit = {},
    error: @Composable () -> Unit = {}
) {
    val bitmap = rememberImageLoader(model)
    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    } else if (model == null) {
        error()
    } else {
        loading()
    }
}

@Composable
fun rememberImageLoader(model: Any?): ImageBitmap? {
    var bitmap by remember(model) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(model) {
        if (model == null) return@LaunchedEffect
        val urlStr = model.toString()
        if (urlStr.isBlank()) return@LaunchedEffect
        
        val cached = imageCache[urlStr]
        if (cached != null) {
            bitmap = cached
            return@LaunchedEffect
        }

        withContext(Dispatchers.IO) {
            try {
                val bytes = when {
                    urlStr.startsWith("http://") || urlStr.startsWith("https://") -> {
                        URL(urlStr).openStream().use { it.readBytes() }
                    }
                    urlStr.startsWith("file:/") -> {
                        val file = File(java.net.URI(urlStr))
                        if (file.exists()) file.readBytes() else null
                    }
                    else -> {
                        val file = File(urlStr)
                        if (file.exists()) file.readBytes() else null
                    }
                }
                if (bytes != null) {
                    val loaded = SkiaImage.makeFromEncoded(bytes).toComposeImageBitmap()
                    imageCache[urlStr] = loaded
                    bitmap = loaded
                }
            } catch (e: Exception) {
                // Ignore
            }
        }
    }
    return bitmap
}

fun showFileDialog(
    title: String,
    mode: Int = java.awt.FileDialog.LOAD,
    multiple: Boolean = false,
    fileExtensions: List<String> = emptyList(),
    onFilesSelected: (List<File>) -> Unit
) {
    val dialog = java.awt.FileDialog(null as java.awt.Frame?, title, mode)
    dialog.isMultipleMode = multiple
    if (fileExtensions.isNotEmpty()) {
        dialog.setFilenameFilter { _, name ->
            fileExtensions.any { ext -> name.endsWith(ext, ignoreCase = true) }
        }
    }
    dialog.isVisible = true
    val files = dialog.files.toList()
    if (files.isNotEmpty()) {
        onFilesSelected(files)
    }
}

@Composable
fun DesktopSoundCloudLoginWebView(
    viewModel: MusicViewModel,
    onLoadingStateChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberWebViewState("https://soundcloud.com/signin")
    val navigator = rememberWebViewNavigator()
    val jsBridge = rememberWebViewJsBridge(navigator)

    LaunchedEffect(state) {
        state.webSettings.customUserAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
    }

    LaunchedEffect(state.loadingState) {
        val loading = state.loadingState
        onLoadingStateChanged(loading !is LoadingState.Finished)

        if (loading is LoadingState.Finished) {
            val jsCode = """
                (function() {
                    function checkHeaders(headers, url) {
                        let auth = null;
                        if (headers) {
                            if (typeof headers.get === 'function') {
                                auth = headers.get('Authorization') || headers.get('authorization');
                            } else {
                                auth = headers['Authorization'] || headers['authorization'];
                            }
                        }
                        let clientId = null;
                        if (url) {
                            try {
                                let u = new URL(url, window.location.href);
                                clientId = u.searchParams.get('client_id');
                            } catch(e) {}
                        }
                        if (auth && auth.startsWith('OAuth ') && clientId) {
                            let token = auth.substring(6).trim();
                            if (window.kmpJsBridge) {
                                window.kmpJsBridge.callNative(
                                    "onCredentialsCaptured",
                                    JSON.stringify({ clientId: clientId, oauthToken: token })
                                );
                            }
                        }
                    }

                    const originalFetch = window.fetch;
                    window.fetch = function(input, init) {
                        let url = '';
                        if (typeof input === 'string') {
                            url = input;
                        } else if (input instanceof Request) {
                            url = input.url;
                        }
                        let headers = init ? init.headers : null;
                        if (input instanceof Request) {
                            headers = headers || input.headers;
                        }
                        checkHeaders(headers, url);
                        return originalFetch.apply(this, arguments);
                    };

                    const originalOpen = XMLHttpRequest.prototype.open;
                    const originalSetRequestHeader = XMLHttpRequest.prototype.setRequestHeader;
                    const originalSend = XMLHttpRequest.prototype.send;

                    XMLHttpRequest.prototype.open = function(method, url) {
                        this._url = url;
                        this._headers = {};
                        return originalOpen.apply(this, arguments);
                    };

                    XMLHttpRequest.prototype.setRequestHeader = function(header, value) {
                        this._headers[header] = value;
                        return originalSetRequestHeader.apply(this, arguments);
                    };

                    XMLHttpRequest.prototype.send = function() {
                        checkHeaders(this._headers, this._url);
                        return originalSend.apply(this, arguments);
                    };
                })();
            """.trimIndent()
            navigator.evaluateJavaScript(jsCode)
        }
    }

    LaunchedEffect(jsBridge) {
        jsBridge.register(object : IJsMessageHandler {
            override fun methodName(): String = "onCredentialsCaptured"

            override fun handle(
                message: JsMessage,
                navigator: WebViewNavigator?,
                callback: (String) -> Unit
            ) {
                try {
                    val json = com.google.gson.JsonParser.parseString(message.params).asJsonObject
                    val clientId = json.get("clientId").asString
                    val oauthToken = json.get("oauthToken").asString
                    viewModel.onCredentialsCaptured(clientId, oauthToken)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    WebView(
        state = state,
        navigator = navigator,
        webViewJsBridge = jsBridge,
        modifier = modifier
    )
}


@Composable
fun MusicScreen(viewModel: MusicViewModel) {
    val haptic = LocalHapticFeedback.current
    val isLoggedOut by viewModel.isLoggedOut.collectAsState(initial = true)
    val tracks by viewModel.tracks.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val repeatMode by viewModel.repeatMode.collectAsState()
    val shuffleEnabled by viewModel.shuffleEnabled.collectAsState()
    val currentTrackTitle by viewModel.currentTrackTitle.collectAsState()
    val selectedTrack by viewModel.selectedTrack.collectAsState()
    val currentPlayingTrack by viewModel.currentPlayingTrack.collectAsState()
    val currentTrackId by viewModel.currentTrackId.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val downloadedFolderArtworkUri by viewModel.downloadedFolderArtworkUri.collectAsState()
    val clientId by viewModel.clientId.collectAsState()
    val oauthToken by viewModel.oauthToken.collectAsState()
    val mixSection by viewModel.mixSection.collectAsState()
    val stationSection by viewModel.stationSection.collectAsState()
    val mixesLoading by viewModel.mixesLoading.collectAsState()
    val loadingMixId by viewModel.loadingMixId.collectAsState()
    val selectedMix by viewModel.selectedMix.collectAsState()
    val mixTracks by viewModel.mixTracks.collectAsState()
    val playbackPositionMs by viewModel.playbackPositionMs.collectAsState()
    val playbackDurationMs by viewModel.playbackDurationMs.collectAsState()
    val volume by viewModel.volume.collectAsState()
    val screen by viewModel.screen.collectAsState()
    val userId by viewModel.userId.collectAsState()
    val playlists by viewModel.playlists.collectAsState(initial = emptyList())
    val selectedPlaylist by viewModel.selectedPlaylist.collectAsState()
    val isClientIdExpired by viewModel.isClientIdExpired.collectAsState()
    val homeSelectedTab by viewModel.homeSelectedTab.collectAsState()
    var showTrackActionsDialog by remember { mutableStateOf(false) }

    val downloadedTracks = favorites.filter { it.downloadState == DownloadState.DOWNLOADED }

    BackHandler(enabled = selectedTrack != null && !isLoggedOut) {
        viewModel.closeTrack()
    }

    BackHandler(enabled = selectedMix != null && selectedTrack == null && !isLoggedOut) {
        viewModel.closeMix()
    }

    BackHandler(enabled = selectedTrack == null && selectedMix == null && screen != AppScreen.HOME && !isLoggedOut) {
        when (screen) {
            AppScreen.SEARCH -> viewModel.closeSearch()
            AppScreen.DOWNLOADS -> viewModel.closeDownloads()
            AppScreen.SETTINGS -> viewModel.closeSettings()
            AppScreen.MIX_DETAIL -> viewModel.closeMix()
            AppScreen.PLAYLIST_DETAIL -> viewModel.closePlaylist()
            AppScreen.MY_MUSIC -> viewModel.closeMyMusic()
            AppScreen.HOME -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ExpressiveBackground()

        if (isLoggedOut) {
            SoundCloudLoginScreen(viewModel = viewModel)
        } else {
            Row(modifier = Modifier.fillMaxSize()) {
                Sidebar(
                    currentScreen = screen,
                    onNavigate = { targetScreen ->
                        viewModel.navigateTo(targetScreen)
                    }
                )
                
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    when (screen) {
                        AppScreen.HOME -> HomeScreen(
                            mixSection = mixSection,
                            stationSection = stationSection,
                            mixesLoading = mixesLoading,
                            loadingMixId = loadingMixId,
                            hasOauthToken = oauthToken.isNotBlank(),
                            mixesError = if (screen == AppScreen.HOME) errorMessage else null,
                            clientId = clientId,
                            isClientIdExpired = isClientIdExpired,
                            onAutoRefreshClientId = viewModel::tryAutoRefreshClientId,
                            onOpenSettings = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.openSettings()
                            },
                            onPlayMix = { mix ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.playMix(mix)
                            },
                            onOpenMix = { mix ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.openMix(mix)
                            },
                            onReloadMixes = viewModel::loadMixes
                        )

                        AppScreen.SEARCH -> SearchScreen(
                            query = searchQuery,
                            tracks = tracks,
                            favorites = favorites,
                            currentTrackId = currentTrackId,
                            isLoading = isLoading,
                            errorMessage = errorMessage,
                            onBack = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.closeSearch()
                            },
                            onQueryChange = viewModel::onSearchQueryChange,
                            onPlayTrack = { track ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.playTrack(track)
                            },
                            onFavoriteClick = { track ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.toggleFavorite(track)
                            },
                            onFocusChanged = { viewModel.setTextFieldFocused(it) }
                        )

                        AppScreen.DOWNLOADS -> DownloadsScreen(
                            tracks = downloadedTracks,
                            folderArtworkUri = downloadedFolderArtworkUri,
                            currentTrackId = currentTrackId,
                            onBack = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.closeDownloads()
                            },
                            onChangeArtwork = viewModel::updateDownloadedFolderArtworkUri,
                            onPlayTrack = { track ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.playFavorite(track)
                            },
                            onDeleteDownload = { track ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.deleteDownloadedTrack(track)
                            },
                            onImportTracks = viewModel::importLocalTracks
                        )

                        AppScreen.MY_MUSIC -> MyMusicScreen(
                            playlists = playlists,
                            onCreatePlaylist = viewModel::createPlaylist,
                            onDeletePlaylist = viewModel::deletePlaylist,
                            onOpenPlaylist = viewModel::openPlaylist,
                            onFocusChanged = { viewModel.setTextFieldFocused(it) }
                        )

                        AppScreen.SETTINGS -> {
                            val likesSyncStatus by viewModel.likesSyncStatus.collectAsState()
                            SettingsScreen(
                                settingsRepository = viewModel.settingsRepository,
                                likesSyncStatus = likesSyncStatus,
                                startLikesSync = viewModel::startLikesSync,
                                stopLikesSync = viewModel::stopLikesSync,
                                resetLikesSyncStatus = viewModel::resetLikesSyncStatus,
                                onBack = viewModel::closeSettings,
                                onRelogin = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.logout()
                                },
                                onClearCache = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.refreshMixesAndStations()
                                }
                            )
                        }

                        AppScreen.PLAYLIST_DETAIL -> {
                            selectedPlaylist?.let { playlist ->
                                PlaylistDetailScreen(
                                    playlist = playlist,
                                    currentTrackId = currentTrackId,
                                    onBack = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.closePlaylist()
                                    },
                                    onPlayTrack = { track ->
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.playPlaylistTrack(playlist, track)
                                    },
                                    onRemoveTrack = { track ->
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.removeTrackFromPlaylist(playlist.id, track.id)
                                    },
                                    onChangeArtwork = { uri ->
                                        viewModel.updatePlaylistArtwork(playlist.id, uri)
                                    },
                                    onMoveDownloadedToDownloads = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.moveDownloadedTracksToDownloads(playlist)
                                    }
                                )
                            }
                        }

                        AppScreen.MIX_DETAIL -> Unit // Handled by selectedMix visibility
                    }

                    androidx.compose.animation.AnimatedVisibility(
                        visible = screen == AppScreen.MIX_DETAIL && selectedMix != null && selectedTrack == null,
                        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                    ) {
                        selectedMix?.let { mix ->
                            MixDetailScreen(
                                mix = mix,
                                tracks = mixTracks,
                                currentTrackId = currentTrackId,
                                favorites = favorites,
                                onBack = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.closeMix()
                                },
                                onPlayTrack = { track ->
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.playMixTrack(track)
                                },
                                onFavoriteClick = { track ->
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.toggleFavorite(track)
                                }
                            )
                        }
                    }

                    androidx.compose.animation.AnimatedVisibility(
                        visible = currentTrackTitle != null && selectedTrack == null,
                        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                            .navigationBarsPadding()
                    ) {
                        currentPlayingTrack?.let { track ->
                            val favorite = favorites.firstOrNull { it.id == track.id }
                            PlayerBar(
                                track = track,
                                isPlaying = isPlaying,
                                isFavorite = favorite != null,
                                repeatMode = repeatMode,
                                shuffleEnabled = shuffleEnabled,
                                positionMs = playbackPositionMs,
                                durationMs = max(playbackDurationMs, track.duration),
                                volume = volume,
                                onVolumeChange = viewModel::setVolume,
                                onTogglePlay = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.togglePlayPause()
                                },
                                onOpen = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.openTrack(track)
                                },
                                onPrevious = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.skipPrevious()
                                },
                                onNext = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.skipNext()
                                },
                                onShuffle = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.toggleShuffle()
                                },
                                onRepeat = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.cycleRepeatMode()
                                },
                                onSeek = viewModel::seekTo,
                                onFavoriteClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.toggleFavorite(track)
                                }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = selectedTrack != null,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                selectedTrack?.let { track ->
                    val favorite = favorites.firstOrNull { it.id == track.id }
                    TrackDetailScreen(
                        track = track,
                        isFavorite = favorite != null,
                        favoriteTrack = favorite,
                        downloadState = favorite?.downloadState,
                        isPlaying = isPlaying,
                        repeatMode = repeatMode,
                        shuffleEnabled = shuffleEnabled,
                        positionMs = playbackPositionMs,
                        durationMs = max(playbackDurationMs, track.duration),
                        volume = volume,
                        onVolumeChange = viewModel::setVolume,
                        onBack = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.closeTrack()
                        },
                        onTogglePlay = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.togglePlayPause()
                        },
                        onSeek = viewModel::seekTo,
                        onFavoriteClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.toggleFavorite(track)
                        },
                        onPrevious = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.skipPrevious()
                        },
                        onNext = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.skipNext()
                        },
                        onRepeat = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.cycleRepeatMode()
                        },
                        onShuffle = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.toggleShuffle()
                        },
                        onDeleteDownload = { fav ->
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.deleteDownloadedTrack(fav)
                        },
                        onLongPressCover = {
                            showTrackActionsDialog = true
                        }
                    )
                }
            }
        }
    }

    if (showTrackActionsDialog && selectedTrack != null) {
        TrackActionsDialog(
            track = selectedTrack!!,
            playlists = playlists,
            onDismiss = { showTrackActionsDialog = false },
            onAddToPlaylist = { playlist ->
                viewModel.addTrackToPlaylist(playlist.id, selectedTrack!!)
            },
            onCreatePlaylist = { name ->
                viewModel.createPlaylist(name)
            },
            onShare = {
                selectedTrack?.permalinkUrl?.let { url ->
                    try {
                        val clipboard = java.awt.Toolkit.getDefaultToolkit().systemClipboard
                        val selection = java.awt.datatransfer.StringSelection(url)
                        clipboard.setContents(selection, selection)
                        println("Ссылка скопирована в буфер обмена: $url")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            },
            onFocusChanged = { viewModel.setTextFieldFocused(it) }
        )
    }
}

@Composable
private fun HomeScreen(
    mixSection: MixSection?,
    stationSection: MixSection?,
    mixesLoading: Boolean,
    loadingMixId: String?,
    hasOauthToken: Boolean,
    mixesError: String?,
    clientId: String,
    isClientIdExpired: Boolean,
    onAutoRefreshClientId: () -> Unit,
    onOpenSettings: () -> Unit,
    onPlayMix: (SoundCloudMix) -> Unit,
    onOpenMix: (SoundCloudMix) -> Unit,
    onReloadMixes: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Главная",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-2).sp
                    )
                )
            }

            if (clientId.isBlank()) {
                ClientIdWarningCard(onOpenSettings = onOpenSettings)
            } else if (isClientIdExpired) {
                ClientIdExpiredWarningCard(onOpenSettings = onOpenSettings, onAutoRefresh = onAutoRefreshClientId)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                MixesSection(
                    mixSection = mixSection,
                    isLoading = mixesLoading,
                    loadingMixId = loadingMixId,
                    hasOauthToken = hasOauthToken,
                    errorMessage = mixesError,
                    onPlayMix = onPlayMix,
                    onOpenMix = onOpenMix,
                    onReload = onReloadMixes,
                    onOpenSettings = onOpenSettings
                )
            }

            if (stationSection != null) {
                item {
                    MixesSection(
                        mixSection = stationSection,
                        isLoading = mixesLoading,
                        loadingMixId = loadingMixId,
                        hasOauthToken = hasOauthToken,
                        errorMessage = null,
                        onPlayMix = onPlayMix,
                        onOpenMix = onOpenMix,
                        onReload = {},
                        onOpenSettings = onOpenSettings
                    )
                }
            }
        }
    }
}

@Composable
private fun MyMusicScreen(
    playlists: List<Playlist>,
    onCreatePlaylist: (String) -> Unit,
    onDeletePlaylist: (String) -> Unit,
    onOpenPlaylist: (Playlist) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    var showCreatePlaylistDialog by remember { mutableStateOf(false) }
    var playlistNameInput by remember { mutableStateOf("") }
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Моя музыка",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-2).sp
                    )
                )
                FilledTonalIconButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showCreatePlaylistDialog = true
                    },
                    modifier = Modifier.size(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Создать плейлист", modifier = Modifier.size(28.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Плейлисты",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            if (playlists.isEmpty()) {
                item {
                    EmptyState(
                        text = "У вас пока нет плейлистов. Создайте первый плейлист, чтобы собирать любимую музыку!",
                        icon = Icons.Default.QueueMusic,
                        actionText = "Создать плейлист",
                        onAction = {
                            showCreatePlaylistDialog = true
                        }
                    )
                }
            } else {
                items(playlists, key = { "playlist-${it.id}" }) { playlist ->
                    PlaylistCard(
                        playlist = playlist,
                        onClick = { onOpenPlaylist(playlist) },
                        onDelete = { onDeletePlaylist(playlist.id) }
                    )
                }
            }
        }
    }

    if (showCreatePlaylistDialog) {
        AlertDialog(
            onDismissRequest = { showCreatePlaylistDialog = false },
            title = { Text("Создать плейлист") },
            text = {
                OutlinedTextField(
                    value = playlistNameInput,
                    onValueChange = { playlistNameInput = it },
                    placeholder = { Text("Название плейлиста") },
                    singleLine = true,
                    modifier = Modifier.onFocusChanged { onFocusChanged(it.isFocused) }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (playlistNameInput.isNotBlank()) {
                            onCreatePlaylist(playlistNameInput)
                            showCreatePlaylistDialog = false
                            playlistNameInput = ""
                        }
                    }
                ) {
                    Text("Создать")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreatePlaylistDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}

@Composable
private fun Sidebar(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .width(260.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 12.dp)
        ) {
            // Brand header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Column {
                    Text(
                        text = "YouCloud",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Premium Desktop",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Navigation Links
            val navItems = listOf(
                Triple("Home", Icons.Default.Home, AppScreen.HOME),
                Triple("Search", Icons.Default.Search, AppScreen.SEARCH),
                Triple("My Music", Icons.Default.QueueMusic, AppScreen.MY_MUSIC),
                Triple("Downloaded", Icons.Default.Folder, AppScreen.DOWNLOADS)
            )

            navItems.forEach { (title, icon, screen) ->
                val active = currentScreen == screen || 
                    (screen == AppScreen.HOME && currentScreen == AppScreen.MIX_DETAIL) ||
                    (screen == AppScreen.MY_MUSIC && currentScreen == AppScreen.PLAYLIST_DETAIL)
                
                val backgroundAlpha = if (active) 0.12f else 0f
                val textColor = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                val textWeight = if (active) FontWeight.Bold else FontWeight.Medium

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = backgroundAlpha))
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onNavigate(screen)
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor,
                        fontWeight = textWeight
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Settings button
            val settingsActive = currentScreen == AppScreen.SETTINGS
            val settingsBackgroundAlpha = if (settingsActive) 0.12f else 0f
            val settingsColor = if (settingsActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = settingsBackgroundAlpha))
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onNavigate(AppScreen.SETTINGS)
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = settingsColor,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleMedium,
                    color = settingsColor,
                    fontWeight = if (settingsActive) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    settingsRepository: SettingsRepository,
    likesSyncStatus: LikesSyncStatus,
    startLikesSync: () -> Unit,
    stopLikesSync: () -> Unit,
    resetLikesSyncStatus: () -> Unit,
    onBack: () -> Unit,
    onRelogin: () -> Unit,
    onClearCache: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TopBar(title = "Настройки", onBack = onBack)
        }

        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Синхронизация SoundCloud",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Автоматически добавляйте все треки, которые вы лайкнули на SoundCloud, в любимые и скачивайте их для прослушивания офлайн.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    
                    if (likesSyncStatus.state != SyncState.IDLE) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        val statusText = when (likesSyncStatus.state) {
                            SyncState.FETCHING_LIKES -> "Получение лайкнутых треков..."
                            SyncState.DOWNLOADING -> "Скачивание треков: ${likesSyncStatus.currentTrackIndex} из ${likesSyncStatus.totalTracks}"
                            SyncState.COMPLETED -> "Синхронизация завершена!"
                            SyncState.FAILED -> "Ошибка: ${likesSyncStatus.errorMessage}"
                            else -> ""
                        }
                        
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (likesSyncStatus.state == SyncState.FAILED) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                        
                        if (likesSyncStatus.state == SyncState.DOWNLOADING) {
                            Text(
                                text = "Скачивается: ${likesSyncStatus.currentTrackTitle}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            
                            val progress = if (likesSyncStatus.totalTracks > 0) {
                                (likesSyncStatus.downloadedCount + likesSyncStatus.failedCount).toFloat() / likesSyncStatus.totalTracks
                            } else 0f
                            
                            LinearProgressIndicator(
                                progress = progress,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                            
                            Text(
                                text = "Успешно: ${likesSyncStatus.downloadedCount} | Ошибки: ${likesSyncStatus.failedCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (likesSyncStatus.state == SyncState.FETCHING_LIKES || likesSyncStatus.state == SyncState.DOWNLOADING) {
                            Button(
                                onClick = stopLikesSync,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Остановить", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Button(
                                onClick = startLikesSync,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = if (likesSyncStatus.state == SyncState.COMPLETED || likesSyncStatus.state == SyncState.FAILED) "Синхронизировать заново" else "Синхронизировать лайки",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            if (likesSyncStatus.state == SyncState.COMPLETED || likesSyncStatus.state == SyncState.FAILED) {
                                Button(
                                    onClick = resetLikesSyncStatus,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                ) {
                                    Text("Сбросить", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Аккаунт SoundCloud",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Вы вошли в систему. Если у вас возникли проблемы со стримингом или вы хотите сменить аккаунт, вы можете войти заново.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onRelogin,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Войти заново", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            val context = LocalContext.current
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Данные и кэш",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Сброс локального кэша миксов и станций. Это принудительно обновит списки треков из SoundCloud.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onClearCache()
                            Toast.makeText(context, "Кэш миксов и станций успешно очищен", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text("Сбросить кэш и обновить", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            val eqEnabled by settingsRepository.equalizerEnabled.collectAsState()
            val eqPreset by settingsRepository.equalizerPreset.collectAsState()
            EqualizerCard(
                settingsRepository = settingsRepository,
                eqEnabled = eqEnabled,
                eqPreset = eqPreset
            )
        }

        item {
            val activeThemePreset by settingsRepository.themePreset.collectAsState()
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Цветовая тема (Material You)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Выберите палитру из 3-х гармоничных цветов для персонализации интерфейса приложения.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        com.example.myapplication.ui.theme.ThemePresets.forEach { preset ->
                            val isSelected = preset.name == activeThemePreset

                            Surface(
                                onClick = {
                                    settingsRepository.setThemePreset(preset.name)
                                },
                                shape = RoundedCornerShape(20.dp),
                                border = if (isSelected) {
                                    BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                                } else {
                                    BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                                },
                                color = if (isSelected) {
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                } else {
                                    MaterialTheme.colorScheme.surface
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = preset.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CircleColor(preset.primary)
                                        CircleColor(preset.secondary)
                                        CircleColor(preset.tertiary)

                                        if (isSelected) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Выбрано",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CircleColor(color: Color) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(color)
            .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
    )
}

@Composable
private fun SearchScreen(
    query: String,
    tracks: List<SoundCloudTrack>,
    favorites: List<FavoriteTrack>,
    currentTrackId: Long?,
    isLoading: Boolean,
    errorMessage: String?,
    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
    onPlayTrack: (SoundCloudTrack) -> Unit,
    onFavoriteClick: (SoundCloudTrack) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(100)
        focusRequester.requestFocus()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TopBar(title = "Поиск", onBack = onBack)
        }

        item {
            SearchField(
                query = query,
                onQueryChange = onQueryChange,
                focusRequester = focusRequester,
                onFocusChanged = onFocusChanged
            )
        }

        // Removed LinearProgressIndicator to prevent layout jumping

        if (errorMessage != null) {
            item { MessageCard(errorMessage) }
        }

        if (tracks.isEmpty() && !isLoading) {
            item {
                EmptyState(
                    if (query.isBlank()) "Напиши, что хочешь услышать."
                    else "Ничего не нашлось."
                )
            }
        } else {
            items(tracks, key = { "search-${it.id}" }) { track ->
                TrackCard(
                    track = track,
                    isFavorite = favorites.any { it.id == track.id },
                    isSelected = track.id == currentTrackId,
                    onClick = { onPlayTrack(track) },
                    onFavoriteClick = { onFavoriteClick(track) }
                )
            }
        }
    }
}

@Composable
private fun DownloadsScreen(
    tracks: List<FavoriteTrack>,
    folderArtworkUri: String?,
    currentTrackId: Long?,
    onBack: () -> Unit,
    onChangeArtwork: (String?) -> Unit,
    onPlayTrack: (FavoriteTrack) -> Unit,
    onDeleteDownload: (FavoriteTrack) -> Unit,
    onImportTracks: (List<File>) -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TopBar(title = "Скачанные", onBack = onBack)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                FolderHero(
                    count = tracks.size,
                    artworkUri = folderArtworkUri,
                    onChangeArtwork = {
                        showFileDialog(
                            title = "Выберите обложку",
                            fileExtensions = listOf(".png", ".jpg", ".jpeg")
                        ) { files ->
                            onChangeArtwork(files.first().toURI().toString())
                        }
                    },
                    onImportLocalTrack = {
                        showFileDialog(
                            title = "Выберите аудиофайлы",
                            multiple = true,
                            fileExtensions = listOf(".mp3", ".wav", ".m4a")
                        ) { files ->
                            onImportTracks(files)
                        }
                    }
                )
            }

            if (tracks.isEmpty()) {
                item { EmptyState("Здесь появятся треки, которые ты сохранишь на устройство.") }
            } else {
                items(tracks, key = { "downloaded-${it.id}" }) { track ->
                    DownloadedTrackCard(
                        track = track,
                        isSelected = track.id == currentTrackId,
                        onClick = { onPlayTrack(track) },
                        onDeleteDownload = { onDeleteDownload(track) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpressiveBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0B0E)) // Very deep slate/black
    ) {
        // Subtle ambient neon blur circles in corners to make the app pop
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.5f)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF2C194D).copy(alpha = 0.35f), // Soft dark purple/violet glow
                            Color.Transparent
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.6f)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), // Ambient glow matching the active theme
                            Color(0xFF1E1C24).copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

@Composable
private fun SearchLaunchCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    var isPressed by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else if (isHovered) 1.01f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "searchScale"
    )

    Surface(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        color = if (isHovered)
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        else
            MaterialTheme.colorScheme.surface.copy(alpha = 0.25f),
        border = BorderStroke(1.dp, if (isHovered) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Press -> isPressed = true
                            PointerEventType.Release -> isPressed = false
                            PointerEventType.Enter -> isHovered = true
                            PointerEventType.Exit -> {
                                isHovered = false
                                isPressed = false
                            }
                        }
                    }
                }
            }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(26.dp))
            Text(
                text = "Найти трек",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
     @Composable
private fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChanged(it.isFocused) },
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        placeholder = { Text("Найти трек") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
        )
    )
}

@Composable
private fun DownloadedFolderCard(count: Int, artworkUri: String?, onClick: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    var isHovered by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else if (isHovered) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "downloadsScale"
    )

    Card(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter -> isHovered = true
                            PointerEventType.Exit -> isHovered = false
                        }
                    }
                }
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isHovered)
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            else
                MaterialTheme.colorScheme.surface.copy(alpha = 0.25f)
        ),
        border = BorderStroke(1.dp, if (isHovered) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isHovered) 6.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FolderArtwork(artworkUri, 82.dp)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Скачанные",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "$count треков на устройстве",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun FolderHero(
    count: Int,
    artworkUri: String?,
    onChangeArtwork: () -> Unit,
    onImportLocalTrack: (() -> Unit)? = null
) {
    val haptic = LocalHapticFeedback.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(42.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FolderArtwork(artworkUri, 220.dp)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Скачанные",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                )
                Text(
                    text = "$count треков доступны оффлайн",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onChangeArtwork()
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f).height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Icon(Icons.Default.Image, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Обложка", maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                if (onImportLocalTrack != null) {
                    Button(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onImportLocalTrack()
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f).height(52.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Импорт", maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(title: String, onBack: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FilledTonalIconButton(onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onBack()
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SectionHeader(title: String, subtitle: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MixesSection(
    mixSection: MixSection?,
    isLoading: Boolean,
    loadingMixId: String?,
    hasOauthToken: Boolean,
    errorMessage: String?,
    onPlayMix: (SoundCloudMix) -> Unit,
    onOpenMix: (SoundCloudMix) -> Unit,
    onReload: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(
            title = mixSection?.title ?: "Your Mixes",
            subtitle = when {
                !hasOauthToken -> "Добавь OAuth-токен в настройках, чтобы увидеть персональные миксы."
                isLoading -> "Загружаем подборку your-moods…"
                mixSection == null -> "Подборка пока не загрузилась."
                else -> "${mixSection.mixes.size} миксов от SoundCloud"
            }
        )

        if (!hasOauthToken) {
            ElevatedCard(
                onClick = onOpenSettings,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                    Text(
                        text = "Открыть настройки и вставить OAuth",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            return
        }

        // Removed LinearProgressIndicator to prevent layout jumping

        if (errorMessage != null && mixSection == null) {
            MessageCard(errorMessage)
            Button(onClick = onReload) {
                Text("Повторить")
            }
        }

        mixSection?.mixes?.let { mixes ->
            if (mixes.isEmpty()) {
                EmptyState("Подборка your-moods пуста.")
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(end = 4.dp)
                ) {
                    items(mixes, key = { it.id }) { mix ->
                        MixCard(
                            mix = mix,
                            isLoading = loadingMixId == mix.id,
                            onPlay = { onPlayMix(mix) },
                            onClick = { onOpenMix(mix) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MixCard(
    mix: SoundCloudMix,
    isLoading: Boolean,
    onPlay: () -> Unit,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    Card(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = Modifier
            .width(190.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box {
                if (mix.artworkUrl != null) {
                    TrackArtwork(mix.artworkUrl, 166.dp)
                } else {
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier.size(166.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(48.dp))
                        }
                    }
                }

                // Play button overlay
                Surface(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onPlay()
                    },
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                        .size(48.dp),
                    tonalElevation = 8.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (isLoading) {
                            // Subtle loading: just change icon or add a very faint pulse
                            // Removing CircularProgressIndicator as requested to prevent visual noise/shifting
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)) {
                Text(
                    text = mix.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = mix.description?.takeIf { it.isNotBlank() } ?: "${mix.trackIds.size} треков",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    lineHeight = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun MixDetailScreen(
    mix: SoundCloudMix,
    tracks: List<SoundCloudTrack>,
    currentTrackId: Long?,
    favorites: List<FavoriteTrack>,
    onBack: () -> Unit,
    onPlayTrack: (SoundCloudTrack) -> Unit,
    onFavoriteClick: (SoundCloudTrack) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 120.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            FilledTonalIconButton(onClick = onBack) {
                                Icon(Icons.Default.ArrowBack, contentDescription = null)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        
                        TrackArtwork(mix.artworkUrl, 240.dp)
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = mix.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                        
                        if (!mix.description.isNullOrBlank()) {
                            Text(
                                text = mix.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp)
                            )
                        }
                    }
                }

                // Removed CircularProgressIndicator to prevent layout jumping

                items(tracks, key = { "mix-track-${it.id}" }) { track ->
                    TrackCard(
                        track = track,
                        isFavorite = favorites.any { it.id == track.id },
                        isSelected = track.id == currentTrackId,
                        onClick = { onPlayTrack(track) },
                        onFavoriteClick = { onFavoriteClick(track) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TrackCard(
    track: SoundCloudTrack,
    isFavorite: Boolean,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    Card(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.92f)
            } else {
                MaterialTheme.colorScheme.surface.copy(alpha = 0.88f)
            },
            contentColor = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TrackArtwork(track.artworkUrl, size = 68.dp)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title ?: "Unknown Track",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.user?.username ?: "Unknown Artist",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            val favoriteScale by animateFloatAsState(
                targetValue = if (isFavorite) 1.2f else 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
            )

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .graphicsLayer {
                        scaleX = favoriteScale
                        scaleY = favoriteScale
                    }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else if (isFavorite) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    }
                )
            }
        }
    }
}

@Composable
private fun DownloadedTrackCard(
    track: FavoriteTrack,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onDeleteDownload: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    Card(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
            } else {
                MaterialTheme.colorScheme.surface
            },
            contentColor = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TrackArtwork(track.artworkUrl, 64.dp)
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = onDeleteDownload) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
@Composable
private fun TrackDetailScreen(
    track: SoundCloudTrack,
    isFavorite: Boolean,
    favoriteTrack: FavoriteTrack?,
    downloadState: DownloadState?,
    isPlaying: Boolean,
    repeatMode: Int,
    shuffleEnabled: Boolean,
    positionMs: Long,
    durationMs: Long,
    volume: Int,
    onVolumeChange: (Int) -> Unit,
    onBack: () -> Unit,
    onTogglePlay: () -> Unit,
    onSeek: (Long) -> Unit,
    onFavoriteClick: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onRepeat: () -> Unit,
    onShuffle: () -> Unit,
    onDeleteDownload: (FavoriteTrack) -> Unit,
    onLongPressCover: () -> Unit
) {
    val vibrator: Any? = null
    val haptic = LocalHapticFeedback.current
    var lastVibratedRatio by remember { mutableStateOf(0f) }
    var sliderProgress by remember { mutableStateOf<Float?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Rich dynamic gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f),
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 32.dp, vertical = 24.dp)
            ) {
                // Top row with Back and download actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledTonalIconButton(onClick = onBack, modifier = Modifier.size(52.dp)) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DownloadBadge(downloadState ?: DownloadState.NONE)
                        if (favoriteTrack?.downloadState == DownloadState.DOWNLOADED) {
                            FilledTonalIconButton(
                                onClick = { onDeleteDownload(favoriteTrack) },
                                modifier = Modifier.size(52.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Split layout for Desktop: Left artwork, Right controls/info
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // LEFT COLUMN: Artwork
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        val artworkScale by animateFloatAsState(
                            targetValue = if (isPlaying) 1f else 0.85f,
                            animationSpec = spring(stiffness = Spring.StiffnessLow),
                            label = "artworkScale"
                        )
                        val artworkPressedScale = remember { Animatable(1f) }
                        val coroutineScope = rememberCoroutineScope()

                        Box(
                            modifier = Modifier
                                .graphicsLayer {
                                    scaleX = artworkScale * artworkPressedScale.value
                                    scaleY = artworkScale * artworkPressedScale.value
                                }
                                .pointerInput(track.permalinkUrl) {
                                    detectTapGestures(
                                        onLongPress = {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                            coroutineScope.launch {
                                                artworkPressedScale.animateTo(1.12f, animationSpec = tween(150))
                                                artworkPressedScale.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                                                onLongPressCover()
                                            }
                                        }
                                    )
                                }
                        ) {
                            TrackArtwork(track.artworkUrl, size = 360.dp)
                        }
                    }

                    // RIGHT COLUMN: Info, Progress, Playback Controls, Option Buttons, Volume Slider
                    Column(
                        modifier = Modifier
                            .weight(1.2f)
                            .fillMaxHeight()
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = track.title ?: "Unknown Track",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    textAlign = TextAlign.Start,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    letterSpacing = (-1).sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = track.user?.username ?: "Unknown Artist",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                    textAlign = TextAlign.Start
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            VolumeButton(
                                volume = volume,
                                onVolumeChange = onVolumeChange,
                                iconSize = 28.dp,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(68.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(36.dp))

                        // Seekbar (slider + times)
                        Slider(
                            value = sliderProgress ?: if (durationMs > 0) {
                                positionMs.coerceIn(0L, durationMs).toFloat() / durationMs.toFloat()
                            } else {
                                0f
                            },
                            onValueChange = { ratio ->
                                sliderProgress = ratio
                                if (kotlin.math.abs(ratio - lastVibratedRatio) >= 0.02f) {
                                    lastVibratedRatio = ratio
                                }
                            },
                            onValueChangeFinished = {
                                sliderProgress?.let { ratio ->
                                    onSeek((ratio * durationMs).toLong())
                                }
                                sliderProgress = null
                            },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                formatDuration(positionMs),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                formatDuration(durationMs),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(36.dp))

                        // Playback Control Buttons (Previous, Play, Next)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FilledTonalIconButton(onClick = onPrevious, modifier = Modifier.size(64.dp)) {
                                Icon(Icons.Default.SkipPrevious, contentDescription = null, modifier = Modifier.size(32.dp))
                            }
                            ExpressivePlayButton(
                                isPlaying = isPlaying,
                                onClick = onTogglePlay,
                                modifier = Modifier
                                    .height(84.dp)
                                    .width(128.dp),
                                iconSize = 42.dp
                            )
                            FilledTonalIconButton(onClick = onNext, modifier = Modifier.size(64.dp)) {
                                Icon(Icons.Default.SkipNext, contentDescription = null, modifier = Modifier.size(32.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // Option Buttons (Favorite, Repeat, Shuffle)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ExpressiveControlIconButton(
                                selected = isFavorite,
                                onClick = onFavoriteClick,
                                icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                                modifier = Modifier.size(68.dp)
                            )

                            ExpressiveControlIconButton(
                                selected = repeatMode != com.example.myapplication.player.MusicPlayer.REPEAT_MODE_OFF,
                                onClick = onRepeat,
                                icon = if (repeatMode == com.example.myapplication.player.MusicPlayer.REPEAT_MODE_ONE) Icons.Default.RepeatOne else Icons.Default.Repeat,
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                                modifier = Modifier.size(68.dp)
                            )

                            ExpressiveControlIconButton(
                                selected = shuffleEnabled,
                                onClick = onShuffle,
                                icon = Icons.Default.Shuffle,
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                                modifier = Modifier.size(68.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FolderArtwork(artworkUri: String?, size: Dp) {
    val shape = RoundedCornerShape(if (size > 100.dp) 32.dp else 18.dp)
    if (artworkUri.isNullOrBlank()) {
        Box(
            modifier = Modifier
                .size(size)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f), shape)
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), shape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Folder,
                contentDescription = null,
                modifier = Modifier.size(size * 0.45f),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    } else {
        SubcomposeAsyncImage(
            model = artworkUri,
            contentDescription = null,
            modifier = Modifier
                .size(size)
                .clip(shape),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .size(size)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f), shape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Folder,
                        contentDescription = null,
                        modifier = Modifier.size(size * 0.45f),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                }
            },
            error = {
                Box(
                    modifier = Modifier
                        .size(size)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f), shape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Folder,
                        contentDescription = null,
                        modifier = Modifier.size(size * 0.45f),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}

@Composable
private fun TrackArtwork(artworkUrl: String?, size: Dp) {
    AsyncImage(
        model = artworkUrl?.replace("large", "t500x500"),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(if (size > 100.dp) 36.dp else 18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun DownloadBadge(state: DownloadState) {
    Surface(
        color = when (state) {
            DownloadState.DOWNLOADED -> MaterialTheme.colorScheme.primaryContainer
            DownloadState.DOWNLOADING -> MaterialTheme.colorScheme.secondaryContainer
            DownloadState.FAILED -> MaterialTheme.colorScheme.errorContainer
            DownloadState.NONE -> MaterialTheme.colorScheme.surfaceVariant
        },
        shape = RoundedCornerShape(100.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state == DownloadState.DOWNLOADING) {
                CircularProgressIndicator(
                    modifier = Modifier.size(14.dp),
                    strokeWidth = 2.dp
                )
            }
            Text(
                text = when (state) {
                    DownloadState.DOWNLOADED -> "Загружено"
                    DownloadState.DOWNLOADING -> "Загрузка"
                    DownloadState.FAILED -> "Ошибка"
                    DownloadState.NONE -> "Онлайн"
                },
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun EmptyState(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.25f),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
            if (actionText != null && onAction != null) {
                Button(
                    onClick = onAction,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = actionText, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun MessageCard(message: String) {
    Surface(
        color = MaterialTheme.colorScheme.errorContainer,
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

@Composable
private fun PlayerBar(
    track: SoundCloudTrack,
    isPlaying: Boolean,
    isFavorite: Boolean,
    repeatMode: Int,
    shuffleEnabled: Boolean,
    positionMs: Long,
    durationMs: Long,
    volume: Int,
    onVolumeChange: (Int) -> Unit,
    onTogglePlay: () -> Unit,
    onOpen: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    onSeek: (Long) -> Unit,
    onFavoriteClick: () -> Unit
) {
    var sliderProgress by remember { mutableStateOf<Float?>(null) }

    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 12.dp,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable(onClick = onOpen)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side: Track Info
            Row(
                modifier = Modifier.weight(0.3f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TrackArtwork(track.artworkUrl, size = 56.dp)
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f, fill = false)) {
                    Text(
                        text = track.title ?: "Unknown Track",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = track.user?.username ?: "Unknown Artist",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Middle side: Controls & Slider
            Column(
                modifier = Modifier.weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ExpressiveControlIconButton(
                        selected = shuffleEnabled,
                        onClick = onShuffle,
                        icon = Icons.Default.Shuffle,
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                        modifier = Modifier.size(40.dp),
                        selectedCornerSize = 10.dp
                    )
                    
                    androidx.compose.material3.FilledTonalIconButton(
                        onClick = onPrevious,
                        modifier = Modifier.size(40.dp),
                        colors = androidx.compose.material3.IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    ExpressivePlayButton(
                        isPlaying = isPlaying,
                        onClick = onTogglePlay,
                        modifier = Modifier.size(48.dp),
                        iconSize = 24.dp
                    )
                    
                    androidx.compose.material3.FilledTonalIconButton(
                        onClick = onNext,
                        modifier = Modifier.size(40.dp),
                        colors = androidx.compose.material3.IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    ExpressiveControlIconButton(
                        selected = repeatMode != com.example.myapplication.player.MusicPlayer.REPEAT_MODE_OFF,
                        onClick = onRepeat,
                        icon = if (repeatMode == com.example.myapplication.player.MusicPlayer.REPEAT_MODE_ONE) Icons.Default.RepeatOne else Icons.Default.Repeat,
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                        modifier = Modifier.size(40.dp),
                        selectedCornerSize = 10.dp
                    )
                }
                
                Spacer(modifier = Modifier.height(14.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = formatDuration(positionMs),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                    
                    Slider(
                        value = sliderProgress ?: if (durationMs > 0) {
                            positionMs.coerceIn(0L, durationMs).toFloat() / durationMs.toFloat()
                        } else {
                            0f
                        },
                        onValueChange = { sliderProgress = it },
                        onValueChangeFinished = {
                            sliderProgress?.let { ratio ->
                                onSeek((ratio * durationMs).toLong())
                            }
                            sliderProgress = null
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(12.dp)
                    )
                    
                    Text(
                        text = formatDuration(durationMs),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                }
            }

            // Right side: Volume & Expand Button
            Row(
                modifier = Modifier.weight(0.3f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VolumeButton(
                    volume = volume,
                    onVolumeChange = onVolumeChange,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                IconButton(onClick = onOpen) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Expand",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

private fun formatDuration(milliseconds: Long): String {
    val totalSeconds = milliseconds.coerceAtLeast(0L) / 1_000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}

@Composable
fun ExpressiveControlIconButton(
    selected: Boolean,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selectedColor: Color,
    unselectedColor: Color,
    modifier: Modifier = Modifier,
    selectedCornerSize: androidx.compose.ui.unit.Dp = 16.dp
) {
    val haptic = LocalHapticFeedback.current
    val cornerSize by animateDpAsState(
        targetValue = if (selected) selectedCornerSize else 50.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cornerSize"
    )

    val scale by animateFloatAsState(
        targetValue = if (selected) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val containerColor by animateColorAsState(
        targetValue = if (selected) selectedColor else unselectedColor,
        animationSpec = tween(400),
        label = "containerColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (selected) contentColorFor(selectedColor) else MaterialTheme.colorScheme.onSecondaryContainer,
        animationSpec = tween(400),
        label = "contentColor"
    )

    Surface(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        color = containerColor,
        shape = RoundedCornerShape(cornerSize),
        modifier = modifier.graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
@Composable
fun ExpressivePlayButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp
) {
    val haptic = LocalHapticFeedback.current
    val cornerSize by animateDpAsState(
        targetValue = if (isPlaying) 20.dp else 50.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cornerSize"
    )

    val scale by animateFloatAsState(
        targetValue = if (isPlaying) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val containerColor by animateColorAsState(
        targetValue = if (isPlaying) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
        animationSpec = tween(450),
        label = "containerColor"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isPlaying) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer,
        animationSpec = tween(450),
        label = "iconColor"
    )

    Surface(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        color = containerColor,
        shape = RoundedCornerShape(cornerSize),
        modifier = modifier.graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
private fun ClientIdWarningCard(onOpenSettings: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    ElevatedCard(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onOpenSettings()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.85f),
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Не указан SoundCloud client_id",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Нажмите, чтобы открыть настройки и ввести рабочий ключ, иначе поиск и воспроизведение работать не будут.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun ClientIdExpiredWarningCard(onOpenSettings: () -> Unit, onAutoRefresh: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.85f),
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "SoundCloud client_id устарел",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Текущий ключ SoundCloud больше недействителен. Попробуйте обновить его автоматически или укажите рабочий вручную.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onOpenSettings()
                    }
                ) {
                    Text("Настройки", color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onAutoRefresh()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Обновить автоматически")
                }
            }
        }
    }
}

@Composable
private fun EqualizerPresetChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun EqualizerCard(
    settingsRepository: SettingsRepository,
    eqEnabled: Boolean,
    eqPreset: String
) {
    val eqInfo = remember { settingsRepository.getEqualizerInfo() }
    var bandLevels by remember(eqPreset) {
        mutableStateOf(
            (0 until eqInfo.numBands).map { band ->
                settingsRepository.getBandLevel(band)
            }
        )
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Эквалайзер",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Настройка звуковых частот",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = eqEnabled,
                    onCheckedChange = { settingsRepository.setEqualizerEnabled(it) }
                )
            }

            if (eqEnabled) {
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Пресеты",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val presetList = listOf("Flat", "Bass Boost", "Rock", "Pop", "Classical", "Jazz", "Vocal")
                    items(presetList) { preset ->
                        val isSelected = eqPreset == preset
                        EqualizerPresetChip(
                            text = preset,
                            selected = isSelected,
                            onClick = {
                                settingsRepository.setEqualizerPreset(preset)
                                val presetBands = when (preset) {
                                    "Bass Boost" -> listOf(600, 400, 0, 0, 0)
                                    "Rock" -> listOf(500, 300, -300, 200, 500)
                                    "Pop" -> listOf(-200, -100, 300, 200, -200)
                                    "Classical" -> listOf(500, 300, -200, 400, 400)
                                    "Jazz" -> listOf(400, 200, -200, 200, 500)
                                    "Vocal" -> listOf(-200, 0, 500, 400, 0)
                                    else -> listOf(0, 0, 0, 0, 0) // Flat
                                }
                                for (i in 0 until eqInfo.numBands) {
                                    val level = presetBands.getOrNull(i) ?: 0
                                    settingsRepository.setBandLevel(i, level)
                                }
                                bandLevels = (0 until eqInfo.numBands).map { band ->
                                    settingsRepository.getBandLevel(band)
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Полосы частот",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                
                for (i in 0 until eqInfo.numBands) {
                    val frequency = eqInfo.frequencies.getOrNull(i) ?: 0
                    val currentLevel = bandLevels.getOrNull(i) ?: 0
                    
                    val minVal = eqInfo.minLevel.toFloat()
                    val maxVal = eqInfo.maxLevel.toFloat()

                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (frequency >= 1000) "${frequency / 1000} kHz" else "$frequency Hz",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${currentLevel / 100} dB",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Slider(
                            value = currentLevel.toFloat(),
                            onValueChange = { newValue ->
                                val levelInt = newValue.toInt()
                                settingsRepository.setBandLevel(i, levelInt)
                                if (eqPreset != "Custom") {
                                    settingsRepository.setEqualizerPreset("Custom")
                                }
                                bandLevels = bandLevels.toMutableList().apply {
                                    this[i] = levelInt
                                }
                            },
                            valueRange = minVal..maxVal,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SoundCloudLoginScreen(
    viewModel: MusicViewModel,
    modifier: Modifier = Modifier
) {
    val isLoggingIn by viewModel.isLoggingIn.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    var isWebViewLoading by remember { mutableStateOf(true) }

    // Manual login state
    var selectedMethodIndex by remember { mutableStateOf(1) } // Default to 1 (Manual Token) because WebView doesn't work well on some Linux setups
    var oauthTokenInput by remember { mutableStateOf("") }
    var clientIdInput by remember { mutableStateOf("") }
    var isScrapingClientId by remember { mutableStateOf(false) }
    var localLoginError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedMethodIndex) {
        if (selectedMethodIndex == 1 && clientIdInput.isBlank()) {
            isScrapingClientId = true
            try {
                val resolved = SoundCloudApi.fetchSoundCloudClientId()
                if (resolved != null) {
                    clientIdInput = resolved
                }
            } catch (e: Exception) {
                // ignore
            } finally {
                isScrapingClientId = false
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SoundCloud",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Войдите в свой аккаунт, чтобы настроить приложение",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                SegmentedControl(
                    items = listOf("Через Браузер", "По Токену"),
                    selectedIndex = selectedMethodIndex,
                    onSelectedIndexChanged = {
                        selectedMethodIndex = it
                        localLoginError = null
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )
            }

            // WebView or Manual login container
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(if (selectedMethodIndex == 0) Color.White else MaterialTheme.colorScheme.background)
            ) {
                if (selectedMethodIndex == 0) {
                    DesktopSoundCloudLoginWebView(
                        viewModel = viewModel,
                        onLoadingStateChanged = { isWebViewLoading = it },
                        modifier = Modifier.fillMaxSize()
                    )

                    if (isWebViewLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                } else {
                    // Manual form
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp)
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val activeError = localLoginError ?: loginError
                        if (activeError != null) {
                            Text(
                                text = activeError,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                            )
                        }

                        OutlinedTextField(
                            value = oauthTokenInput,
                            onValueChange = {
                                oauthTokenInput = it
                                localLoginError = null
                            },
                            label = { Text("OAuth-токен") },
                            placeholder = { Text("2-293482-9382...") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                cursorColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { viewModel.setTextFieldFocused(it.isFocused) }
                        )

                        var showAdvanced by remember { mutableStateOf(false) }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            TextButton(
                                onClick = { showAdvanced = !showAdvanced },
                                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text(if (showAdvanced) "Скрыть настройки Client ID" else "Настройки Client ID (Advanced)")
                            }
                        }

                         if (showAdvanced) {
                             OutlinedTextField(
                                 value = clientIdInput,
                                 onValueChange = {
                                     clientIdInput = it
                                     localLoginError = null
                                 },
                                 label = { Text("Client ID") },
                                 placeholder = { Text(if (isScrapingClientId) "Получение..." else "32-значный хэш") },
                                 singleLine = true,
                                 trailingIcon = {
                                     if (isScrapingClientId) {
                                         CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.primary)
                                     }
                                 },
                                 colors = OutlinedTextFieldDefaults.colors(
                                     focusedBorderColor = MaterialTheme.colorScheme.primary,
                                     focusedLabelColor = MaterialTheme.colorScheme.primary,
                                     cursorColor = MaterialTheme.colorScheme.primary
                                 ),
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .padding(bottom = 8.dp)
                                     .onFocusChanged { viewModel.setTextFieldFocused(it.isFocused) }
                             )
                         }

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Как получить OAuth-токен:",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                val steps = listOf(
                                    "1. Войдите в SoundCloud через браузер (Chrome/Firefox/Opera).",
                                    "2. Нажмите F12 (или Ctrl+Shift+I), откройте вкладку «Сеть» (Network).",
                                    "3. Запустите любой трек на сайте.",
                                    "4. Найдите любой запрос к api-v2.soundcloud.com (например, /tracks или /me).",
                                    "5. В заголовках запроса найдите 'Authorization'. Скопируйте значение после слова 'OAuth'."
                                )
                                steps.forEach { step ->
                                    Text(
                                        text = step,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = {
                                if (oauthTokenInput.isBlank()) {
                                    localLoginError = "Пожалуйста, введите OAuth-токен"
                                    return@Button
                                }
                                localLoginError = null
                                scope.launch {
                                    var cId = clientIdInput.trim()
                                    if (cId.isBlank()) {
                                        val fetched = SoundCloudApi.fetchSoundCloudClientId()
                                        if (fetched != null) {
                                            cId = fetched
                                            clientIdInput = fetched
                                        } else {
                                            localLoginError = "Не удалось автоматически получить Client ID. Пожалуйста, укажите его вручную."
                                            return@launch
                                        }
                                    }

                                    var token = oauthTokenInput.trim()
                                    if (token.startsWith("OAuth ", ignoreCase = true)) {
                                        token = token.substring(6).trim()
                                    }

                                    viewModel.onCredentialsCaptured(cId, token)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text(
                                  text = "Войти",
                                  style = MaterialTheme.typography.titleMedium,
                                  fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Authentication Overlay
        // Authentication Overlay
        if (isLoggingIn) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Авторизация в SoundCloud...",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SegmentedControl(
    items: List<String>,
    selectedIndex: Int,
    onSelectedIndexChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(27.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        tonalElevation = 2.dp
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(4.dp)) {
            val width = maxWidth / items.size
            
            val offset by animateDpAsState(
                targetValue = width * selectedIndex,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
                label = "segmentedOffset"
            )

            Box(
                modifier = Modifier
                    .offset(x = offset)
                    .width(width)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(23.dp))
            )

            Row(modifier = Modifier.fillMaxSize()) {
                items.forEachIndexed { index, text ->
                    val isSelected = index == selectedIndex
                    val textColor by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(150),
                        label = "segmentedTextColor"
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    onSelectedIndexChanged(index)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaylistCard(playlist: Playlist, onClick: () -> Unit, onDelete: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "playlistScale"
    )

    Card(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (playlist.artworkUrl != null) {
                FolderArtwork(playlist.artworkUrl, 82.dp)
            } else {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.size(82.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.QueueMusic,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = playlist.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "${playlist.tracks.size} треков",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                )
            }
            IconButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onDelete()
                }
            ) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
private fun PlaylistDetailScreen(
    playlist: Playlist,
    currentTrackId: Long?,
    onBack: () -> Unit,
    onPlayTrack: (FavoriteTrack) -> Unit,
    onRemoveTrack: (FavoriteTrack) -> Unit,
    onChangeArtwork: (String?) -> Unit,
    onMoveDownloadedToDownloads: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TopBar(title = playlist.name, onBack = onBack)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .clickable {
                                showFileDialog(
                                    title = "Выберите обложку плейлиста",
                                    fileExtensions = listOf(".png", ".jpg", ".jpeg", ".webp")
                                ) { files ->
                                    files.firstOrNull()?.let { file ->
                                        onChangeArtwork(file.toURI().toString())
                                    }
                                }
                            }
                    ) {
                        if (playlist.artworkUrl != null) {
                            FolderArtwork(playlist.artworkUrl, 220.dp)
                        } else {
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(32.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.QueueMusic,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.size(72.dp)
                                    )
                                }
                            }
                        }

                        // Premium edit overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.2f))
                                .padding(12.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Surface(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = CircleShape,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.Image,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = playlist.name,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-1).sp
                        )
                        Text(
                            text = "${playlist.tracks.size} треков",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        val hasDownloaded = playlist.tracks.any { it.downloadState == DownloadState.DOWNLOADED }
                        if (hasDownloaded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = onMoveDownloadedToDownloads,
                                shape = RoundedCornerShape(16.dp),
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            ) {
                                Text("Переместить скачанные в Скачанное", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            if (playlist.tracks.isEmpty()) {
                item {
                    EmptyState("Здесь пока нет треков. Зажмите обложку трека в плеере, чтобы добавить его.")
                }
            } else {
                items(playlist.tracks, key = { "playlist-${playlist.id}-${it.id}" }) { track ->
                    DownloadedTrackCard(
                        track = track,
                        isSelected = track.id == currentTrackId,
                        onClick = { onPlayTrack(track) },
                        onDeleteDownload = { onRemoveTrack(track) }
                    )
                }
            }
        }
    }
}

@Composable
fun TrackActionsDialog(
    track: SoundCloudTrack,
    playlists: List<Playlist>,
    onDismiss: () -> Unit,
    onAddToPlaylist: (Playlist) -> Unit,
    onCreatePlaylist: (String) -> Unit,
    onShare: () -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            var showPlaylistSelection by remember { mutableStateOf(false) }
            var showCreatePlaylistDialog by remember { mutableStateOf(false) }
            var playlistNameInput by remember { mutableStateOf("") }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = false, onClick = {}) // prevent click-through
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(40.dp)
                            .height(5.dp)
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                                CircleShape
                            )
                    )

                    // Cover Art & Title Info
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TrackArtwork(artworkUrl = track.artworkUrl, size = 64.dp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = track.title ?: "Unknown Track",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = track.user?.username ?: "SoundCloud Artist",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    if (!showPlaylistSelection) {
                        // Options
                        Surface(
                            onClick = { showPlaylistSelection = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlaylistAdd,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Добавить в плейлист",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Сохраните этот трек в свои подборки",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Surface(
                            onClick = {
                                onShare()
                                onDismiss()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Отправить ссылку на трек",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Поделитесь треком с друзьями",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Text("Отмена", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                        }
                    } else {
                        // Playlist Selection Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = { showPlaylistSelection = false },
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), CircleShape)
                                    .size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "Выберите плейлист",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(
                                onClick = { showCreatePlaylistDialog = true },
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                                    .size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        if (playlists.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "У вас пока нет плейлистов",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 300.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(playlists) { playlist ->
                                    Surface(
                                        onClick = {
                                            onAddToPlaylist(playlist)
                                            onDismiss()
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f))
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                                        ) {
                                            if (!playlist.artworkUrl.isNullOrBlank()) {
                                                FolderArtwork(playlist.artworkUrl, size = 44.dp)
                                            } else {
                                                Box(
                                                    modifier = Modifier
                                                        .size(44.dp)
                                                        .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10.dp)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.QueueMusic,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                                        modifier = Modifier.size(24.dp)
                                                    )
                                                }
                                            }
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = playlist.name,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Text(
                                                    text = "${playlist.tracks.size} треков",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Text("Отмена", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }

            if (showCreatePlaylistDialog) {
                AlertDialog(
                    onDismissRequest = { showCreatePlaylistDialog = false },
                    title = { Text("Создать плейлист") },
                    text = {
                        OutlinedTextField(
                            value = playlistNameInput,
                            onValueChange = { playlistNameInput = it },
                            placeholder = { Text("Название плейлиста") },
                            singleLine = true,
                            modifier = Modifier.onFocusChanged { onFocusChanged(it.isFocused) }
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (playlistNameInput.isNotBlank()) {
                                    onCreatePlaylist(playlistNameInput)
                                    showCreatePlaylistDialog = false
                                    playlistNameInput = ""
                                }
                            }
                        ) {
                            Text("Создать")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showCreatePlaylistDialog = false }) {
                            Text("Отмена")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun VolumeButton(
    volume: Int,
    onVolumeChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    iconSize: androidx.compose.ui.unit.Dp = 24.dp,
    tint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    var showPopup by remember { mutableStateOf(false) }
    
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        IconButton(onClick = { showPopup = !showPopup }, modifier = Modifier.size(iconSize * 2)) {
            Icon(
                imageVector = if (volume == 0) Icons.AutoMirrored.Filled.VolumeMute else if (volume < 50) Icons.AutoMirrored.Filled.VolumeDown else Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = "Volume",
                tint = tint,
                modifier = Modifier.size(iconSize)
            )
        }
        
        if (showPopup) {
            androidx.compose.ui.window.Popup(
                alignment = Alignment.TopCenter,
                onDismissRequest = { showPopup = false },
                properties = androidx.compose.ui.window.PopupProperties(focusable = true)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .width(200.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (volume == 0) Icons.AutoMirrored.Filled.VolumeMute else if (volume < 50) Icons.AutoMirrored.Filled.VolumeDown else Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                        Slider(
                            value = volume.toFloat(),
                            onValueChange = { onVolumeChange(it.toInt()) },
                            valueRange = 0f..100f,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "$volume%",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.width(36.dp),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}


