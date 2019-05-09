package com.joeynebula.socketecho

interface ISocketService {
    var outputCallback:(txt: String) -> Unit
    fun start(outputCallback: (txt: String) -> Unit)
    fun send(message: String): Boolean
    fun close()
}
