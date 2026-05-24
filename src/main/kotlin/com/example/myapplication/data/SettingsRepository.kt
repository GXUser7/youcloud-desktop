package com.example.myapplication.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class SettingsRepository(
    val defaultClientId: String,
    val defaultOauthToken: String = "",
    val defaultUserId: String = ""
) {
    private val gson = Gson()
    private val configDir = File(System.getProperty("user.home"), ".config/youcloud").apply { mkdirs() }
    private val settingsFile = File(configDir, "settings.json")

    private val data: MutableMap<String, Any> = loadSettings()

    private val _clientId = MutableStateFlow(
        (data[KEY_CLIENT_ID] as? String)?.takeIf { it.isNotBlank() } ?: defaultClientId
    )
    val clientId = _clientId.asStateFlow()

    private val _oauthToken = MutableStateFlow(readOauthToken())
    val oauthToken = _oauthToken.asStateFlow()

    private val _userId = MutableStateFlow(readUserId())
    val userId = _userId.asStateFlow()

    val equalizerEnabled = MutableStateFlow(data[KEY_EQ_ENABLED] as? Boolean ?: false)
    val equalizerPreset = MutableStateFlow((data[KEY_EQ_PRESET] as? String) ?: "Flat")
    val homeSelectedTab = MutableStateFlow((data[KEY_HOME_SELECTED_TAB] as? Double)?.toInt() ?: 0)
    val themePreset = MutableStateFlow((data[KEY_THEME_PRESET] as? String) ?: "Indigo Lavender")

    fun setHomeSelectedTab(tab: Int) {
        homeSelectedTab.value = tab
        save(KEY_HOME_SELECTED_TAB, tab)
    }

    fun setThemePreset(preset: String) {
        themePreset.value = preset
        save(KEY_THEME_PRESET, preset)
    }

    fun setEqualizerEnabled(enabled: Boolean) {
        equalizerEnabled.value = enabled
        save(KEY_EQ_ENABLED, enabled)
    }

    fun setEqualizerPreset(preset: String) {
        equalizerPreset.value = preset
        save(KEY_EQ_PRESET, preset)
    }

    fun getBandLevel(band: Int): Int {
        return (data["eq_band_$band"] as? Double)?.toInt() ?: 0
    }

    fun setBandLevel(band: Int, level: Int) {
        save("eq_band_$band", level)
    }

    class EqualizerInfo(
        val numBands: Int,
        val minLevel: Int,
        val maxLevel: Int,
        val frequencies: List<Int>
    )

    fun getEqualizerInfo(): EqualizerInfo {
        return EqualizerInfo(
            numBands = 5,
            minLevel = -1500,
            maxLevel = 1500,
            frequencies = listOf(60, 230, 910, 4000, 14000)
        )
    }

    fun oauthTokenValue(): String = _oauthToken.value
    fun userIdValue(): String = _userId.value

    fun saveClientId(value: String) {
        val cleaned = value.trim()
        if (cleaned.isBlank()) return

        _clientId.value = cleaned
        save(KEY_CLIENT_ID, cleaned)
    }

    fun resetClientId() {
        _clientId.value = defaultClientId
        remove(KEY_CLIENT_ID)
    }

    fun saveOauthToken(value: String) {
        val cleaned = normalizeOauthToken(value)
        _oauthToken.value = cleaned
        save(KEY_OAUTH_TOKEN, cleaned)
    }

    fun resetOauthToken() {
        _oauthToken.value = defaultOauthToken
        remove(KEY_OAUTH_TOKEN)
    }

    fun saveUserId(value: String) {
        val cleaned = value.trim()
        _userId.value = cleaned
        save(KEY_USER_ID, cleaned)
    }

    fun resetUserId() {
        _userId.value = defaultUserId
        remove(KEY_USER_ID)
    }

    private fun readOauthToken(): String {
        val stored = data[KEY_OAUTH_TOKEN] as? String
        return stored?.takeIf { it.isNotBlank() } ?: defaultOauthToken
    }

    private fun readUserId(): String {
        val stored = data[KEY_USER_ID] as? String
        return stored?.takeIf { it.isNotBlank() } ?: defaultUserId
    }

    private fun normalizeOauthToken(value: String): String =
        value.trim().removePrefix("OAuth ").trim()

    private fun loadSettings(): MutableMap<String, Any> {
        if (!settingsFile.exists()) return mutableMapOf()
        return try {
            val json = settingsFile.readText()
            val type = object : TypeToken<MutableMap<String, Any>>() {}.type
            gson.fromJson(json, type) ?: mutableMapOf()
        } catch (e: Exception) {
            mutableMapOf()
        }
    }

    private fun save(key: String, value: Any) {
        data[key] = value
        persist()
    }

    private fun remove(key: String) {
        data.remove(key)
        persist()
    }

    private fun persist() {
        try {
            settingsFile.writeText(gson.toJson(data))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private companion object {
        const val KEY_CLIENT_ID = "soundcloud_client_id"
        const val KEY_OAUTH_TOKEN = "soundcloud_oauth_token"
        const val KEY_USER_ID = "soundcloud_user_id"
        const val KEY_EQ_ENABLED = "equalizer_enabled"
        const val KEY_EQ_PRESET = "equalizer_preset"
        const val KEY_HOME_SELECTED_TAB = "home_selected_tab"
        const val KEY_THEME_PRESET = "theme_preset"
    }
}
