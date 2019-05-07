package com.joeynebula.socketecho

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MainActivity : AppCompatActivity() {
    private var start: Button? = null
    private var output: TextView? = null
    private var client: OkHttpClient? = null

    private inner class EchoWebSocketListener : WebSocketListener() {

        private val NORMAL_CLOSURE_STATUS = 1000

        override fun onOpen(webSocket: WebSocket, response: Response?) {
            webSocket.send("Hello, it's SSaurel !")
            webSocket.send("What's up ?")
            webSocket.send(ByteString.decodeHex("deadbeef"))
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            output("Receiving : " + text!!)
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
            output("Receiving bytes : " + bytes.hex())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String?) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
            output("Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
            output("Error : " + t.message)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start = findViewById(R.id.start) as Button
        output = findViewById(R.id.output) as TextView
        client = OkHttpClient()
        start!!.setOnClickListener { start() }
    }

    private fun start() {
        val request = Request.Builder().url("ws://demos.kaazing.com/echo").build()
        val listener = EchoWebSocketListener()
        val ws = client!!.newWebSocket(request, listener)
        client!!.dispatcher().executorService().shutdown()
    }

    private fun output(txt: String) {
       runOnUiThread { output!!.text = output!!.text.toString() + "\n\n" + txt }
    }
}
