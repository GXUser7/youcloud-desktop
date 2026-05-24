package com.example.myapplication.player

import com.example.myapplication.ui.Log
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.PrintWriter
import java.util.concurrent.atomic.AtomicBoolean

class SystemMediaControls(
    private val musicPlayer: MusicPlayer,
    private val scope: CoroutineScope
) {
    private var process: Process? = null
    private var writer: PrintWriter? = null
    private val running = AtomicBoolean(false)
    private val gson = Gson()

    fun start() {
        // Only run on Linux (MPRIS is a Linux standard)
        val os = System.getProperty("os.name").lowercase()
        if (!os.contains("linux")) {
            Log.w("SystemMediaControls", "System Media Controls (MPRIS) is only supported on Linux.")
            return
        }

        if (!running.compareAndSet(false, true)) return
        Log.d("SystemMediaControls", "Starting System Media Controls (MPRIS)...")

        scope.launch(Dispatchers.IO) {
            try {
                // Write Python script to ~/.config/youcloud/mpris_daemon.py
                val configDir = File(System.getProperty("user.home"), ".config/youcloud")
                if (!configDir.exists()) {
                    configDir.mkdirs()
                }
                val scriptFile = File(configDir, "mpris_daemon.py")
                scriptFile.writeText(MPRIS_DAEMON_PYTHON_SCRIPT)
                scriptFile.setExecutable(true)

                // Start the process using python3
                val pb = ProcessBuilder("python3", scriptFile.absolutePath)
                pb.redirectErrorStream(true)
                val proc = pb.start()
                process = proc

                writer = PrintWriter(proc.outputStream, true)

                // Coroutine to read stdout from Python daemon
                scope.launch(Dispatchers.IO) {
                    try {
                        val reader = BufferedReader(InputStreamReader(proc.inputStream))
                        while (running.get()) {
                            val line = reader.readLine() ?: break
                            Log.d("SystemMediaControls", "MPRIS daemon output: $line")
                            handleDaemonCommand(line)
                        }
                    } catch (e: Exception) {
                        if (running.get()) {
                            Log.e("SystemMediaControls", "Error reading daemon output", e)
                        }
                    }
                }

                // Coroutine to sync State with Python daemon
                scope.launch(Dispatchers.Default) {
                    combine(
                        musicPlayer.isPlaying,
                        musicPlayer.currentQueueTrack,
                        musicPlayer.positionMs,
                        musicPlayer.durationMs,
                        musicPlayer.volume
                    ) { isPlaying, track, positionMs, durationMs, volume ->
                        StateUpdate(
                            title = track?.title ?: "",
                            artist = track?.artist ?: "",
                            playbackStatus = if (isPlaying) "Playing" else "Paused",
                            positionMs = positionMs,
                            durationMs = durationMs,
                            volume = volume
                        )
                    }.collect { state ->
                        sendStateToDaemon(state)
                    }
                }

            } catch (e: Exception) {
                Log.e("SystemMediaControls", "Failed to start MPRIS daemon", e)
            }
        }
    }

    private fun handleDaemonCommand(line: String) {
        val cmd = line.trim()
        scope.launch(Dispatchers.Main) {
            when {
                cmd == "PLAY_PAUSE" -> musicPlayer.togglePlayPause()
                cmd == "PLAY" -> {
                    if (!musicPlayer.isPlaying.value) {
                        musicPlayer.togglePlayPause()
                    }
                }
                cmd == "PAUSE" -> {
                    if (musicPlayer.isPlaying.value) {
                        musicPlayer.togglePlayPause()
                    }
                }
                cmd == "NEXT" -> musicPlayer.skipNext()
                cmd == "PREVIOUS" -> musicPlayer.skipPrevious()
                cmd == "STOP" -> {
                    if (musicPlayer.isPlaying.value) {
                        musicPlayer.togglePlayPause()
                    }
                }
                cmd.startsWith("VOLUME ") -> {
                    val vol = cmd.substringAfter("VOLUME ").toIntOrNull()
                    if (vol != null) {
                        musicPlayer.setVolume(vol)
                    }
                }
            }
        }
    }

    private fun sendStateToDaemon(state: StateUpdate) {
        val w = writer ?: return
        try {
            val json = gson.toJson(state)
            w.println(json)
        } catch (e: Exception) {
            Log.e("SystemMediaControls", "Failed to send state update", e)
        }
    }

    fun stop() {
        if (!running.compareAndSet(true, false)) return
        Log.d("SystemMediaControls", "Stopping System Media Controls...")
        try {
            process?.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        process = null
        writer = null
    }

    private data class StateUpdate(
        val title: String,
        val artist: String,
        val playbackStatus: String,
        val positionMs: Long,
        val durationMs: Long,
        val volume: Int
    )

    companion object {
        private val MPRIS_DAEMON_PYTHON_SCRIPT = """
            #!/usr/bin/env python3
            import sys
            import json
            import threading
            import time

            try:
                import gi
                gi.require_version('Gio', '2.0')
                gi.require_version('GLib', '2.0')
                from gi.repository import Gio, GLib
            except ImportError:
                print("WARNING: gi (PyGObject) not installed. MPRIS integration will be disabled.", file=sys.stderr)
                sys.exit(1)

            MPRIS_INTROSPECTION_XML = ${'"'}${'"'}${'"'}
            <node>
              <interface name="org.mpris.MediaPlayer2">
                <method name="Raise"/>
                <method name="Quit"/>
                <property name="CanQuit" type="b" access="read"/>
                <property name="CanRaise" type="b" access="read"/>
                <property name="HasTrackList" type="b" access="read"/>
                <property name="Identity" type="s" access="read"/>
                <property name="SupportedUriSchemes" type="as" access="read"/>
                <property name="SupportedMimeTypes" type="as" access="read"/>
              </interface>
              <interface name="org.mpris.MediaPlayer2.Player">
                <method name="Next"/>
                <method name="Previous"/>
                <method name="Pause"/>
                <method name="PlayPause"/>
                <method name="Stop"/>
                <method name="Play"/>
                <property name="PlaybackStatus" type="s" access="read"/>
                <property name="Metadata" type="a{sv}" access="read"/>
                <property name="Volume" type="d" access="readwrite"/>
                <property name="Position" type="x" access="read"/>
                <property name="CanGoNext" type="b" access="read"/>
                <property name="CanGoPrevious" type="b" access="read"/>
                <property name="CanPlay" type="b" access="read"/>
                <property name="CanPause" type="b" access="read"/>
                <property name="CanSeek" type="b" access="read"/>
                <property name="CanControl" type="b" access="read"/>
              </interface>
            </node>
            ${'"'}${'"'}${'"'}

            class MprisPlayer:
                def __init__(self):
                    self.playback_status = "Stopped"
                    self.metadata = {}
                    self.volume = 1.0
                    self.position = 0
                    self.title = ""
                    self.artist = ""
                    self.duration_us = 0
                    self.dbus_connection = None
                    self.registration_id = 0

            player = MprisPlayer()

            def handle_method_call(connection, sender, object_path, interface_name, method_name, parameters, invocation):
                if interface_name == "org.mpris.MediaPlayer2":
                    if method_name == "Raise":
                        print("RAISE", flush=True)
                        invocation.return_value(None)
                    elif method_name == "Quit":
                        print("QUIT", flush=True)
                        invocation.return_value(None)
                elif interface_name == "org.mpris.MediaPlayer2.Player":
                    if method_name == "Play":
                        print("PLAY", flush=True)
                        invocation.return_value(None)
                    elif method_name == "Pause":
                        print("PAUSE", flush=True)
                        invocation.return_value(None)
                    elif method_name == "PlayPause":
                        print("PLAY_PAUSE", flush=True)
                        invocation.return_value(None)
                    elif method_name == "Next":
                        print("NEXT", flush=True)
                        invocation.return_value(None)
                    elif method_name == "Previous":
                        print("PREVIOUS", flush=True)
                        invocation.return_value(None)
                    elif method_name == "Stop":
                        print("STOP", flush=True)
                        invocation.return_value(None)

            def handle_get_property(connection, sender, object_path, interface_name, property_name):
                if interface_name == "org.mpris.MediaPlayer2":
                    if property_name == "CanQuit":
                        return GLib.Variant("b", True)
                    elif property_name == "CanRaise":
                        return GLib.Variant("b", True)
                    elif property_name == "HasTrackList":
                        return GLib.Variant("b", False)
                    elif property_name == "Identity":
                        return GLib.Variant("s", "YouCloud")
                    elif property_name == "SupportedUriSchemes":
                        return GLib.Variant("as", [])
                    elif property_name == "SupportedMimeTypes":
                        return GLib.Variant("as", [])
                elif interface_name == "org.mpris.MediaPlayer2.Player":
                    if property_name == "PlaybackStatus":
                        return GLib.Variant("s", player.playback_status)
                    elif property_name == "Metadata":
                        return GLib.Variant("a{sv}", player.metadata)
                    elif property_name == "Volume":
                        return GLib.Variant("d", player.volume)
                    elif property_name == "Position":
                        return GLib.Variant("x", player.position)
                    elif property_name == "CanGoNext":
                        return GLib.Variant("b", True)
                    elif property_name == "CanGoPrevious":
                        return GLib.Variant("b", True)
                    elif property_name == "CanPlay":
                        return GLib.Variant("b", True)
                    elif property_name == "CanPause":
                        return GLib.Variant("b", True)
                    elif property_name == "CanSeek":
                        return GLib.Variant("b", False)
                    elif property_name == "CanControl":
                        return GLib.Variant("b", True)
                return None

            def handle_set_property(connection, sender, object_path, interface_name, property_name, value):
                if interface_name == "org.mpris.MediaPlayer2.Player" and property_name == "Volume":
                    vol = value.get_double()
                    print(f"VOLUME {int(vol * 100)}", flush=True)
                    return True
                return False

            def emit_properties_changed(changed_properties):
                if not player.dbus_connection:
                    return
                
                variant_dict = {}
                for k, v in changed_properties.items():
                    if k == "PlaybackStatus":
                        variant_dict[k] = GLib.Variant("s", v)
                    elif k == "Metadata":
                        variant_dict[k] = GLib.Variant("a{sv}", v)
                    elif k == "Volume":
                        variant_dict[k] = GLib.Variant("d", v)
                    elif k == "Position":
                        variant_dict[k] = GLib.Variant("x", v)

                if not variant_dict:
                    return

                try:
                    player.dbus_connection.emit_signal(
                        None,
                        "/org/mpris/MediaPlayer2",
                        "org.freedesktop.DBus.Properties",
                        "PropertiesChanged",
                        GLib.Variant("(sa{sv}as)", ("org.mpris.MediaPlayer2.Player", variant_dict, []))
                    )
                except Exception as e:
                    print(f"Failed to emit signal: {e}", file=sys.stderr)

            def stdin_thread_func():
                while True:
                    try:
                        line = sys.stdin.readline()
                        if not line:
                            break
                        line = line.strip()
                        if not line:
                            continue
                        
                        data = json.loads(line)
                        changed = {}
                        
                        new_title = data.get("title", "")
                        new_artist = data.get("artist", "")
                        new_duration_ms = data.get("durationMs", 0)
                        new_position_ms = data.get("positionMs", 0)
                        new_status = data.get("playbackStatus", "Stopped")
                        new_volume = data.get("volume", 100) / 100.0

                        player.playback_status = new_status
                        player.volume = new_volume
                        player.position = int(new_position_ms * 1000)
                        player.duration_us = int(new_duration_ms * 1000)

                        meta = {
                            "mpris:trackid": GLib.Variant("o", "/org/mpris/MediaPlayer2/Track/0")
                        }
                        if new_title:
                            meta["xesam:title"] = GLib.Variant("s", new_title)
                        if new_artist:
                            meta["xesam:artist"] = GLib.Variant("as", [new_artist])
                        if player.duration_us > 0:
                            meta["mpris:length"] = GLib.Variant("x", player.duration_us)
                        
                        player.metadata = meta

                        changed["PlaybackStatus"] = player.playback_status
                        changed["Volume"] = player.volume
                        changed["Metadata"] = player.metadata
                        changed["Position"] = player.position

                        GLib.idle_add(emit_properties_changed, changed)
                        
                    except Exception as e:
                        print(f"Error in stdin parser: {e}", file=sys.stderr)

            def on_bus_acquired(connection, name):
                player.dbus_connection = connection
                node_info = Gio.DBusNodeInfo.new_for_xml(MPRIS_INTROSPECTION_XML)
                
                connection.register_object(
                    "/org/mpris/MediaPlayer2",
                    node_info.interfaces[0],
                    handle_method_call,
                    handle_get_property,
                    handle_set_property
                )
                
                connection.register_object(
                    "/org/mpris/MediaPlayer2",
                    node_info.interfaces[1],
                    handle_method_call,
                    handle_get_property,
                    handle_set_property
                )

            def on_name_acquired(connection, name):
                pass

            def on_name_lost(connection, name):
                sys.exit(1)

            def main():
                t = threading.Thread(target=stdin_thread_func, daemon=True)
                t.start()

                Gio.bus_own_name(
                    Gio.BusType.SESSION,
                    "org.mpris.MediaPlayer2.youcloud",
                    Gio.BusNameOwnerFlags.NONE,
                    on_bus_acquired,
                    on_name_acquired,
                    on_name_lost
                )

                loop = GLib.MainLoop()
                try:
                    loop.run()
                except KeyboardInterrupt:
                    pass

            if __name__ == "__main__":
                main()
        """.trimIndent()
    }
}
