package com.example.myapplication

import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFAcknowledge
import java.io.File

@OptIn(KCEFAcknowledge::class)
fun main() {
    println("Initializing KCEF...")
    try {
        KCEF.initBlocking(
            builder = {
                installDir(File("kcef-bundle"))
                progress {
                    onLocating {
                        println("KCEF: Locating CEF...")
                    }
                    onDownloading { progress ->
                        println("KCEF: Downloading CEF: $progress%")
                    }
                    onExtracting {
                        println("KCEF: Extracting CEF...")
                    }
                    onInstall {
                        println("KCEF: Installing CEF...")
                    }
                    onInitializing {
                        println("KCEF: Initializing CEF...")
                    }
                    onInitialized {
                        println("KCEF: CEF Initialized successfully!")
                    }
                }
            },
            onError = {
                println("KCEF ERROR:")
                it?.printStackTrace()
            },
            onRestartRequired = {
                println("KCEF RESTART REQUIRED!")
            }
        )
        println("KCEF initBlocking completed.")
    } catch (e: Exception) {
        println("KCEF exception caught:")
        e.printStackTrace()
    }
}
