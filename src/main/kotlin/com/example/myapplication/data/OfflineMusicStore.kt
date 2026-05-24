package com.example.myapplication.data

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
import java.net.URI

class OfflineMusicStore private constructor(
    private val favoritesRepository: FavoritesRepository
) {
    private val configDir = File(System.getProperty("user.home"), ".config/youcloud").apply { mkdirs() }
    private val downloadsDir = File(configDir, "downloads").apply { mkdirs() }
    private val client = OkHttpClient()

    fun downloadHls(streamUrl: String, onProgress: (Float) -> Unit = {}) {
        // Find the track by streamUrl
        val track = favoritesRepository.favorites.value.firstOrNull { it.streamUrl == streamUrl }
            ?: throw IllegalArgumentException("Track with streamUrl not found in favorites: $streamUrl")

        val destinationFile = File(downloadsDir, "${track.id}.mp3")
        val request = Request.Builder().url(streamUrl).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Failed to download track: $response")
            val body = response.body ?: throw IOException("Empty response body")

            destinationFile.outputStream().use { output ->
                body.byteStream().copyTo(output)
            }
        }

        // Update the streamUrl in FavoritesRepository to point to the local file
        val localUrl = destinationFile.toURI().toString()
        favoritesRepository.updateStreamUrl(track.id, localUrl)
    }

    fun removeHls(streamUrl: String) {
        try {
            val uri = URI(streamUrl)
            if (uri.scheme == "file") {
                val file = File(uri)
                if (file.exists()) {
                    file.delete()
                }
            }
        } catch (e: Exception) {
            // If it's a raw path, try loading as File
            val file = File(streamUrl)
            if (file.exists()) {
                file.delete()
            }
        }
    }

    fun release() {
        // No-op on desktop
    }

    companion object {
        @Volatile
        private var instance: OfflineMusicStore? = null

        fun getInstance(favoritesRepository: FavoritesRepository): OfflineMusicStore =
            instance ?: synchronized(this) {
                instance ?: OfflineMusicStore(favoritesRepository).also { instance = it }
            }
    }
}
