package com.joeynebula.socketecho

import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send

interface SocketService {
    @Send
    fun sendText(message: String): Boolean

    @Receive
    fun reciveText(): String    

}