package com.joeynebula.socketecho

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MainActivity : AppCompatActivity() {
    private lateinit var start: Button
    private lateinit var output: TextView
    private lateinit var sendBt: Button
    private lateinit var inputTextTv: AutoCompleteTextView
    //to inject
    private val socketService: ISocketService = SocketService()

    private inner class EchoWebSocketListener : WebSocketListener() {



        override fun onOpen(webSocket: WebSocket, response: Response?) {
            output("Attempting to open socket connection")
            output(response?.message() ?: "No message on open")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            output("Receiving : $text")
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {
            output("Receiving bytes : " + bytes.hex())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String?) {
            output("Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
            output("Error : " + t.message)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start = findViewById<Button>(R.id.start)
        output = findViewById<TextView>(R.id.output)
        inputTextTv = findViewById<AutoCompleteTextView>(R.id.inputTextTv)
        sendBt = findViewById<Button>(R.id.sendBt)

        sendBt.setOnClickListener {
            socketService.send(inputTextTv.text.toString())
            //empty it out on send
            inputTextTv.setText("")
        }
        start.setOnClickListener {
            socketService.start(EchoWebSocketListener())
            sendBt.isEnabled = true
        }
    }



    private fun output(txt: String) {
       runOnUiThread { output?.text = "${output!!.text}\n\n$txt" }
    }
}
