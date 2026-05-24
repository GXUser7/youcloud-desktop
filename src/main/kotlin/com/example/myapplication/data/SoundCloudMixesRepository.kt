package com.example.myapplication.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.util.concurrent.TimeUnit

class SoundCloudMixesRepository(
    private val service: SoundCloudService
) {
    private val configDir = File(System.getProperty("user.home"), ".config/youcloud").apply { mkdirs() }
    private val cacheFile = File(configDir, "mixes_cache.json")
    private val gson = Gson()

    private val cacheData: MutableMap<String, Any> = loadCache()

    fun fetchHomeSectionsFlow(clientId: String): Flow<Pair<MixSection?, MixSection?>> = flow {
        val cacheKey = "home_sections"
        val cachedJson = cacheData[cacheKey] as? String

        var cachedDataPair: Pair<MixSection?, MixSection?>? = null
        if (cachedJson != null) {
            try {
                val type = object : TypeToken<Pair<MixSection?, MixSection?>>() {}.type
                cachedDataPair = gson.fromJson(cachedJson, type)
                if (cachedDataPair != null) {
                    emit(cachedDataPair)
                }
            } catch (e: Exception) {
                // Ignore parse errors
            }
        }

        try {
            val networkData = fetchHomeSectionsNetwork(clientId)

            // Clean up cached track data for mixes that are no longer present
            if (cachedDataPair != null) {
                val cachedMixIds = mutableSetOf<String>()
                cachedDataPair.first?.mixes?.map { it.id }?.let { cachedMixIds.addAll(it) }
                cachedDataPair.second?.mixes?.map { it.id }?.let { cachedMixIds.addAll(it) }

                val networkMixIds = mutableSetOf<String>()
                networkData.first?.mixes?.map { it.id }?.let { networkMixIds.addAll(it) }
                networkData.second?.mixes?.map { it.id }?.let { networkMixIds.addAll(it) }

                val removedMixIds = cachedMixIds - networkMixIds
                if (removedMixIds.isNotEmpty()) {
                    for (mixId in removedMixIds) {
                        cacheData.remove("mix_tracks_$mixId")
                        cacheData.remove("mix_tracks_${mixId}_time")
                    }
                    persist()
                }
            }

            cacheData[cacheKey] = gson.toJson(networkData)
            cacheData["${cacheKey}_time"] = System.currentTimeMillis()
            persist()

            if (networkData != cachedDataPair) {
                emit(networkData)
            }
        } catch (e: Exception) {
            if (cachedDataPair == null) {
                throw e
            }
        }
    }

    private suspend fun fetchHomeSectionsNetwork(clientId: String): Pair<MixSection?, MixSection?> {
        val response = service.getMixedSelections(
            clientId = clientId,
            limit = 10,
            offset = 0,
            linkedPartitioning = 1,
            appVersion = SoundCloudApi.APP_VERSION,
            appLocale = "en"
        )

        val moodsSelection = response.collection.firstOrNull { it.trackingFeatureName == "your-moods" }
        val stationsSelection = response.collection.firstOrNull { 
            it.title?.contains("Discover with Station", ignoreCase = true) == true || 
            it.trackingFeatureName == "stations" ||
            it.trackingFeatureName == "discover-with-station"
        }

        val moodsSection = moodsSelection?.let { selection ->
            val mixes = selection.items?.collection?.mapNotNull(SoundCloudMixPlaylist::toMix).orEmpty()
            if (mixes.isNotEmpty()) MixSection(selection.title?.takeIf { it.isNotBlank() } ?: "Your Mixes", mixes) else null
        }

        val stationsSection = stationsSelection?.let { selection ->
            val stations = selection.items?.collection?.mapNotNull(SoundCloudMixPlaylist::toMix).orEmpty()
            if (stations.isNotEmpty()) MixSection(selection.title?.takeIf { it.isNotBlank() } ?: "Discover with Station", stations) else null
        }

        return Pair(moodsSection, stationsSection)
    }

    suspend fun loadMixTracks(mix: SoundCloudMix, clientId: String): List<SoundCloudTrack> {
        val cacheKey = "mix_tracks_${mix.id}"
        val cachedJson = cacheData[cacheKey] as? String
        val cacheTime = (cacheData["${cacheKey}_time"] as? Double)?.toLong() ?: 0L
        val isCacheValid = (System.currentTimeMillis() - cacheTime) < TimeUnit.HOURS.toMillis(24)

        if (cachedJson != null && isCacheValid) {
            try {
                val type = object : TypeToken<List<SoundCloudTrack>>() {}.type
                val cachedDataList: List<SoundCloudTrack> = gson.fromJson(cachedJson, type)
                if (cachedDataList.isNotEmpty()) return cachedDataList
            } catch (e: Exception) {}
        }

        val networkData = fetchMixTracksNetwork(mix, clientId)
        if (networkData.isNotEmpty()) {
            cacheData[cacheKey] = gson.toJson(networkData)
            cacheData["${cacheKey}_time"] = System.currentTimeMillis()
            persist()
        }
        return networkData
    }

    private suspend fun fetchMixTracksNetwork(mix: SoundCloudMix, clientId: String): List<SoundCloudTrack> {
        val playlistTracks = runCatching {
            service.getSystemPlaylist(
                permalink = mix.permalink,
                clientId = clientId,
                appVersion = SoundCloudApi.APP_VERSION,
                appLocale = "en"
            ).resolvedTracks()
        }.getOrDefault(emptyList())

        if (playlistTracks.isNotEmpty()) {
            return playlistTracks
        }

        return loadTracksByIds(mix.trackIds, clientId)
    }

    private suspend fun loadTracksByIds(ids: List<Long>, clientId: String): List<SoundCloudTrack> {
        if (ids.isEmpty()) return emptyList()

        return ids.chunked(20).flatMap { chunk ->
            runCatching {
                service.getTracksByIds(
                    ids = chunk.joinToString(","),
                    clientId = clientId
                ).collection
            }.getOrElse {
                chunk.mapNotNull { trackId ->
                    runCatching { service.getTrack(trackId, clientId) }.getOrNull()
                }
            }
        }
    }

    private fun SoundCloudSystemPlaylist.resolvedTracks(): List<SoundCloudTrack> =
        tracks.orEmpty().filter { track ->
            track.kind == "track" && !track.title.isNullOrBlank()
        }

    fun clearCache() {
        cacheData.clear()
        persist()
    }

    private fun loadCache(): MutableMap<String, Any> {
        if (!cacheFile.exists()) return mutableMapOf()
        return try {
            val json = cacheFile.readText()
            val type = object : TypeToken<MutableMap<String, Any>>() {}.type
            gson.fromJson(json, type) ?: mutableMapOf()
        } catch (e: Exception) {
            mutableMapOf()
        }
    }

    private fun persist() {
        runCatching {
            cacheFile.writeText(gson.toJson(cacheData))
        }
    }
}
