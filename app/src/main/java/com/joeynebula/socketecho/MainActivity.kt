package com.joeynebula.socketecho

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var start: Button
    private lateinit var output: TextView
    private lateinit var sendBt: Button
    private lateinit var inputTextTv: AutoCompleteTextView
    //to inject
    private val socketService: ISocketService = SocketService()



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
            socketService.start(::output)
            sendBt.isEnabled = true
        }
    }



    private fun output(txt: String) {
       runOnUiThread { output?.text = "${output!!.text}\n\n$txt" }
    }
}
