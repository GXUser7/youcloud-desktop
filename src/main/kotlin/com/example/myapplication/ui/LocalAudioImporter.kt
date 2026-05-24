package com.example.myapplication.ui

import com.example.myapplication.data.DownloadState
import com.example.myapplication.data.FavoriteTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

suspend fun importLocalAudio(files: List<File>): List<FavoriteTrack> = withContext(Dispatchers.IO) {
    val importedTracks = mutableListOf<FavoriteTrack>()
    val configDir = File(System.getProperty("user.home"), ".config/youcloud").apply { mkdirs() }
    val localMusicDir = File(configDir, "local_music").apply { mkdirs() }
    val localArtDir = File(configDir, "local_music_artworks").apply { mkdirs() }

    for (file in files) {
        if (!file.exists()) continue
        try {
            val uniqueId = System.currentTimeMillis() + UUID.randomUUID().hashCode()
            val fileExtension = file.extension.takeIf { it.isNotBlank() } ?: "mp3"
            val destFile = File(localMusicDir, "local_track_$uniqueId.$fileExtension")
            file.copyTo(destFile, overwrite = true)

            var title = ""
            var artist = ""
            var duration = 0L
            var artworkPath: String? = null

            // Simple ID3 parser for title/artist/artwork
            try {
                val bytes = destFile.readBytes()
                if (bytes.size > 10 && String(bytes, 0, 3, Charsets.US_ASCII) == "ID3") {
                    var offset = 10
                    val size = ((bytes[6].toInt() and 0x7F) shl 21) or
                               ((bytes[7].toInt() and 0x7F) shl 14) or
                               ((bytes[8].toInt() and 0x7F) shl 7) or
                               (bytes[9].toInt() and 0x7F)
                    
                    val tagEnd = offset + size
                    while (offset < tagEnd && offset + 10 < bytes.size) {
                        val frameId = String(bytes, offset, 4, Charsets.US_ASCII)
                        if (frameId.isBlank() || frameId[0] == '\u0000') break
                        
                        val frameSize = ((bytes[offset + 4].toInt() and 0xFF) shl 24) or
                                        ((bytes[offset + 5].toInt() and 0xFF) shl 16) or
                                        ((bytes[offset + 6].toInt() and 0xFF) shl 8) or
                                        (bytes[offset + 7].toInt() and 0xFF)
                        
                        offset += 10
                        if (offset + frameSize > bytes.size) break
                        
                        if (frameSize > 0) {
                            val frameBytes = bytes.sliceArray(offset until (offset + frameSize))
                            val encoding = frameBytes.firstOrNull()?.toInt() ?: 0
                            val charset = when (encoding) {
                                1 -> Charsets.UTF_16
                                2 -> Charsets.UTF_16BE
                                3 -> Charsets.UTF_8
                                else -> Charsets.ISO_8859_1
                            }
                            
                            val textBytes = if (encoding == 1 || encoding == 2) {
                                val start = if (frameBytes.size > 3 && frameBytes[1] == 0xFF.toByte() && frameBytes[2] == 0xFE.toByte()) 3 else 1
                                frameBytes.sliceArray(start until frameBytes.size)
                            } else {
                                frameBytes.sliceArray(1 until frameBytes.size)
                            }
                            
                            val text = String(textBytes, charset).trim().replace("\u0000", "")

                            when (frameId) {
                                "TIT2" -> title = text
                                "TPE1" -> artist = text
                                "APIC" -> {
                                    try {
                                        var mimeTypeEnd = 1
                                        while (mimeTypeEnd < frameBytes.size && frameBytes[mimeTypeEnd] != 0.toByte()) {
                                            mimeTypeEnd++
                                        }
                                        val picTypeIdx = mimeTypeEnd + 1
                                        var descEnd = picTypeIdx + 1
                                        while (descEnd < frameBytes.size && frameBytes[descEnd] != 0.toByte()) {
                                            descEnd++
                                        }
                                        val picDataStart = descEnd + 1
                                        if (picDataStart < frameBytes.size) {
                                            val picData = frameBytes.sliceArray(picDataStart until frameBytes.size)
                                            val artFile = File(localArtDir, "local_art_$uniqueId.jpg")
                                            artFile.writeBytes(picData)
                                            artworkPath = artFile.absolutePath
                                        }
                                    } catch (e: Exception) {
                                        // Ignore
                                    }
                                }
                            }
                        }
                        offset += frameSize
                    }
                }
            } catch (e: Exception) {
                // Ignore
            }

            if (title.isBlank()) {
                title = file.nameWithoutExtension
            }
            if (artist.isBlank()) {
                artist = "Локальный файл"
            }

            val favoriteTrack = FavoriteTrack(
                id = uniqueId,
                urn = "local:track:$uniqueId",
                title = title,
                artworkUrl = artworkPath,
                permalinkUrl = null,
                artist = artist,
                duration = duration,
                streamUrl = destFile.toURI().toString(),
                downloadState = DownloadState.DOWNLOADED
            )
            importedTracks.add(favoriteTrack)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    importedTracks
}

private object Charsets {
    val US_ASCII = java.nio.charset.Charset.forName("US-ASCII")
    val UTF_16 = java.nio.charset.Charset.forName("UTF-16")
    val UTF_16BE = java.nio.charset.Charset.forName("UTF-16BE")
    val UTF_8 = java.nio.charset.Charset.forName("UTF-8")
    val ISO_8859_1 = java.nio.charset.Charset.forName("ISO-8859-1")
}
