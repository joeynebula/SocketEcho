package com.joeynebula.socketecho

import okhttp3.*
import okio.ByteString

class SocketService(private val client: OkHttpClient) : ISocketService {
    override var outputCallback: (txt: String) -> Unit = {}
    private lateinit var request: Request
    private lateinit var socket: WebSocket
    private val NORMAL_CLOSURE_STATUS = 1000

    private inner class EchoWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response?) {
            outputCallback.invoke("Attempting to open socket connection")
            outputCallback.invoke(response?.message() ?: "No message on open")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            outputCallback.invoke("Receiving : $text")
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
            outputCallback.invoke("Receiving bytes : " + bytes.hex())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String?) {
            outputCallback.invoke("Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
            outputCallback.invoke("Error : " + t.message)
        }

    }

    override fun start(outputCallback: (txt: String) -> Unit) {

        //check it the socket is already init, if it is use the return value from send to see if it is closed.
        if (::socket.isInitialized && socket.send("Socket is not closed")) {
            return
        }

        this.outputCallback = outputCallback

        request = Request.Builder().url("ws://demos.kaazing.com/echo").build()

        socket = client.newWebSocket(request, EchoWebSocketListener())
    }

    override fun send(message: String): Boolean {
        return socket.send(message)
    }

    override fun close() {
        socket.close(NORMAL_CLOSURE_STATUS, "Close button pressed")
        //client.dispatcher().executorService().shutdownNow();
    }
}
