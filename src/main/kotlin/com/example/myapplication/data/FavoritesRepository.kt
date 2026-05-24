package com.example.myapplication.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class FavoritesRepository {
    private val configDir = File(System.getProperty("user.home"), ".config/youcloud").apply { mkdirs() }
    private val favoritesFile = File(configDir, "favorites.json")
    private val metadataFile = File(configDir, "favorites_metadata.json")
    private val gson = Gson()
    private val listType = object : TypeToken<List<FavoriteTrack>>() {}.type

    private val _favorites = MutableStateFlow(load())
    val favorites = _favorites.asStateFlow()

    private val _downloadedFolderArtworkUri = MutableStateFlow(loadArtworkUri())
    val downloadedFolderArtworkUri = _downloadedFolderArtworkUri.asStateFlow()

    fun isFavorite(trackId: Long): Boolean = _favorites.value.any { it.id == trackId }

    fun get(trackId: Long): FavoriteTrack? = _favorites.value.firstOrNull { it.id == trackId }

    fun add(track: SoundCloudTrack, streamUrl: String?) {
        if (isFavorite(track.id)) return

        update(
            _favorites.value + FavoriteTrack(
                id = track.id,
                urn = track.urn ?: "",
                title = track.title ?: "Unknown Track",
                artworkUrl = track.artworkUrl,
                permalinkUrl = track.permalinkUrl,
                artist = track.user?.username ?: "Unknown Artist",
                duration = track.duration,
                streamUrl = streamUrl,
                downloadState = DownloadState.NONE
            )
        )
    }

    fun addFavoriteTrack(favoriteTrack: FavoriteTrack) {
        if (isFavorite(favoriteTrack.id)) return
        update(_favorites.value + favoriteTrack)
    }

    fun remove(trackId: Long) {
        update(_favorites.value.filterNot { it.id == trackId })
    }

    fun updateStreamUrl(trackId: Long, streamUrl: String?) {
        update(_favorites.value.map {
            if (it.id == trackId) it.copy(streamUrl = streamUrl) else it
        })
    }

    fun updateDownloadState(trackId: Long, state: DownloadState) {
        update(_favorites.value.map {
            if (it.id == trackId) it.copy(downloadState = state) else it
        })
    }

    fun updateDownloadedFolderArtworkUri(uri: String?) {
        _downloadedFolderArtworkUri.value = uri
        saveArtworkUri(uri)
    }

    private fun load(): List<FavoriteTrack> {
        if (!favoritesFile.exists()) return emptyList()
        return runCatching {
            val json = favoritesFile.readText()
            gson.fromJson<List<FavoriteTrack>>(json, listType) ?: emptyList()
        }.getOrDefault(emptyList())
    }

    private fun update(value: List<FavoriteTrack>) {
        _favorites.value = value
        runCatching {
            favoritesFile.writeText(gson.toJson(value))
        }
    }

    private fun loadArtworkUri(): String? {
        if (!metadataFile.exists()) return null
        return runCatching {
            metadataFile.readText().takeIf { it.isNotBlank() }
        }.getOrNull()
    }

    private fun saveArtworkUri(uri: String?) {
        runCatching {
            metadataFile.writeText(uri ?: "")
        }
    }
}
