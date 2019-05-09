package com.joeynebula.socketecho

class MockSocketService : ISocketService {
    override var outputCallback:(txt: String) -> Unit = {}
    override fun start(outputCallback: (txt: String) -> Unit) {
        this.outputCallback = outputCallback
        outputCallback.invoke("Attempting to open socket connection")
    }

    override fun send(message: String): Boolean {
        outputCallback.invoke(message)
        return true
    }

    override fun close() {
        outputCallback.invoke("Closing : 10000 / Close button pressed")
    }
}