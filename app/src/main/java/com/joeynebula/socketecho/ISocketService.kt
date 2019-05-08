package com.joeynebula.socketecho

interface ISocketService {
    fun start(outputCallback: (txt: String) -> Unit)
    fun send(message: String): Boolean
    fun close()
}
