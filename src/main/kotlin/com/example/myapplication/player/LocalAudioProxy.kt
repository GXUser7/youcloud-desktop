package com.example.myapplication.player

import com.sun.net.httpserver.HttpServer
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.concurrent.Executors

object LocalAudioProxy {
    private var server: HttpServer? = null
    var port: Int = 0
        private set

    fun start() {
        if (server != null) return
        try {
            server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0).apply {
                createContext("/stream") { exchange ->
                    val query = exchange.requestURI.query ?: ""
                    val targetUrl = query.removePrefix("url=").let { URLDecoder.decode(it, "UTF-8") }

                    if (targetUrl.isBlank()) {
                        exchange.sendResponseHeaders(400, -1)
                        return@createContext
                    }

                    var connection: HttpURLConnection? = null
                    try {
                        val url = URL(targetUrl)
                        connection = url.openConnection() as HttpURLConnection
                        val isHead = exchange.requestMethod.equals("HEAD", ignoreCase = true)
                        connection.requestMethod = if (isHead) "HEAD" else "GET"
                        connection.connectTimeout = 10000
                        connection.readTimeout = 15000
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")

                        // Forward the Range header if requested by JavaFX MediaPlayer
                        val rangeHeader = exchange.requestHeaders.getFirst("Range")
                        if (rangeHeader != null) {
                            connection.setRequestProperty("Range", rangeHeader)
                        }

                        connection.connect()

                        val responseCode = connection.responseCode
                        println("[AudioProxy] ${exchange.requestMethod} $targetUrl -> $responseCode (Content-Length: ${connection.contentLengthLong}, Content-Type: ${connection.contentType})")
                        val responseHeaders = exchange.responseHeaders

                        // Copy all headers back
                        connection.headerFields.forEach { (key, values) ->
                            if (key != null && !key.equals("Transfer-Encoding", ignoreCase = true) && !key.equals("Content-Encoding", ignoreCase = true)) {
                                responseHeaders.put(key, values)
                            }
                        }

                        // Get Content Length
                        val contentLength = connection.contentLengthLong
                        
                        // Send response headers
                        val responseLength = if (isHead) {
                            if (contentLength > 0) contentLength else -1
                        } else {
                            if (contentLength > 0) contentLength else 0
                        }

                        if (responseCode == HttpURLConnection.HTTP_PARTIAL) {
                            exchange.sendResponseHeaders(206, responseLength)
                        } else {
                            exchange.sendResponseHeaders(200, responseLength)
                        }

                        if (!isHead) {
                            connection.inputStream.use { input ->
                                exchange.responseBody.use { output ->
                                    val buffer = ByteArray(8192)
                                    var bytesRead: Int
                                    while (input.read(buffer).also { bytesRead = it } != -1) {
                                        output.write(buffer, 0, bytesRead)
                                    }
                                }
                            }
                        } else {
                            exchange.responseBody.close()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        try {
                            exchange.sendResponseHeaders(500, -1)
                        } catch (ex: Exception) {}
                    } finally {
                        connection?.disconnect()
                    }
                }
                executor = Executors.newCachedThreadPool()
                start()
            }
            port = server!!.address.port
            println("Local Audio Proxy successfully started on port $port")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getProxyUrl(originalUrl: String): String {
        if (!originalUrl.startsWith("http")) return originalUrl
        return "http://127.0.0.1:$port/stream?url=${URLEncoder.encode(originalUrl, "UTF-8")}"
    }

    fun stop() {
        server?.stop(0)
        server = null
    }
}
