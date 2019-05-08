package com.joeynebula.socketecho

import okhttp3.WebSocketListener

interface ISocketService {
    fun start(listener: WebSocketListener)
    fun send(message: String): Boolean
    fun close()
}
