package com.joeynebula.socketecho

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class SocketService : ISocketService {
    private val client: OkHttpClient = OkHttpClient()
    private lateinit var listener: WebSocketListener
    private lateinit var request: Request
    private lateinit var socket: WebSocket
    private val NORMAL_CLOSURE_STATUS = 1000

    override fun start(listener: WebSocketListener) {

        //check it the socket is already init, if it is use the return value from send to see if it is closed.
        if (::socket.isInitialized && socket.send("Socket is not closed")) {
            return
        }

        this.listener = listener

        request = Request.Builder().url("ws://demos.kaazing.com/echo").build()

        socket = client.newWebSocket(request, listener)
    }

    override fun send(message: String): Boolean {
        return socket.send(message)
    }

    override fun close() {
        socket.close(NORMAL_CLOSURE_STATUS, "Close button pressed")
        //client.dispatcher().executorService().shutdownNow();
    }
}
