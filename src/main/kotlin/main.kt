import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.example.myapplication.ui.MusicScreen
import com.example.myapplication.ui.MusicViewModel
import com.example.myapplication.data.SettingsRepository
import com.example.myapplication.data.FavoritesRepository
import com.example.myapplication.data.PlaylistsRepository
import com.example.myapplication.data.OfflineMusicStore
import com.example.myapplication.player.MusicPlayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.unit.DpSize
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import javafx.application.Platform
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFAcknowledge
import java.io.File
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.Key

@OptIn(KCEFAcknowledge::class)
fun main() {
    // Start KCEF Chromium initialization in the background
    kotlin.concurrent.thread(start = true) {
        try {
            KCEF.initBlocking(
                builder = {
                    installDir(File("kcef-bundle"))
                },
                onError = {
                    it?.printStackTrace()
                },
                onRestartRequired = {
                    // restart ignored
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    application {
        // Explicitly initialize JavaFX Platform so it's ready when we need WebView
        try {
            Platform.startup {}
            Platform.setImplicitExit(false)
        } catch (e: Exception) {
            // Platform might already be initialized
        }

    // Start local audio proxy for streaming
    remember { com.example.myapplication.player.LocalAudioProxy.start() }

    val settingsRepository = remember { SettingsRepository(defaultClientId = "") }
    val musicPlayer = remember { MusicPlayer(settingsRepository) }
    val favoritesRepository = remember { FavoritesRepository() }
    val playlistsRepository = remember { PlaylistsRepository() }
    val offlineMusicStore = remember { OfflineMusicStore.getInstance(favoritesRepository) }

    val viewModel = remember {
        MusicViewModel(
            musicPlayer = musicPlayer,
            favoritesRepository = favoritesRepository,
            playlistsRepository = playlistsRepository,
            offlineMusicStore = offlineMusicStore,
            settingsRepository = settingsRepository
        )
    }

    val systemMediaControls = remember {
        com.example.myapplication.player.SystemMediaControls(musicPlayer, viewModel.viewModelScope).apply {
            start()
        }
    }

    Window(
        onCloseRequest = {
            com.example.myapplication.player.LocalAudioProxy.stop()
            systemMediaControls.stop()
            viewModel.onCleared()
            exitApplication()
        },
        title = "YouCloud - SoundCloud Player",
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(1200.dp, 800.dp)
        ),
        onKeyEvent = { keyEvent ->
            if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Spacebar) {
                if (!viewModel.isTextFieldFocused.value) {
                    viewModel.togglePlayPause()
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    ) {
        val activeThemePreset by viewModel.themePreset.collectAsState()
        MyApplicationTheme(presetName = activeThemePreset, darkTheme = true) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MusicScreen(viewModel = viewModel)
            }
        }
    }
}
}
