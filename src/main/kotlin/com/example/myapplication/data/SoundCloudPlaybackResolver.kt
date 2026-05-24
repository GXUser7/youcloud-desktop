package com.example.myapplication.data

class SoundCloudPlaybackResolver(
    private val service: SoundCloudService
) {
    suspend fun resolve(track: SoundCloudTrack, clientId: String): String? {
        if (track.streamable != true || track.policy == "BLOCK" || track.policy == "SNIP") {
            return null
        }

        val authorization = track.trackAuthorization ?: return null
        val transcoding = track.media
            ?.transcodings
            ?.filter(::isPlayable)
            ?.minByOrNull(::priority)
            ?: return null

        return service.resolveTranscoding(
            transcodingUrl = transcoding.url,
            clientId = clientId,
            trackAuthorization = authorization
        ).url
    }

    private fun isPlayable(transcoding: SoundCloudTranscoding): Boolean {
        val protocol = transcoding.format?.protocol.orEmpty()
        return !transcoding.snipped && !protocol.contains("encrypted", ignoreCase = true)
    }

    private fun priority(transcoding: SoundCloudTranscoding): Int {
        val preset = transcoding.preset.orEmpty()
        val protocol = transcoding.format?.protocol.orEmpty()

        return when {
            protocol == "progressive" -> 0
            preset.startsWith("aac_160") -> 1
            preset.startsWith("aac_96") -> 2
            preset.startsWith("abr") -> 3
            protocol == "hls" -> 4
            else -> 5
        }
    }
}
