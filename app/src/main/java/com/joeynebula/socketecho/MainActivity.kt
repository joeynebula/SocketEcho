package com.joeynebula.socketecho

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity() {
    private lateinit var startTb: ToggleButton
    private lateinit var output: TextView
    private lateinit var sendBt: Button
    private lateinit var inputTextTv: AutoCompleteTextView
    //to inject
    private lateinit var socketService: ISocketService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //injected from di
        socketService = get()
        startTb = findViewById(R.id.startTb)
        output = findViewById(R.id.output)
        inputTextTv = findViewById(R.id.inputTextTv)
        sendBt = findViewById(R.id.sendBt)

        sendBt.setOnClickListener {
            socketService.send(inputTextTv.text.toString())
            //empty it out on send
            inputTextTv.setText("")
        }

        startTb.setOnCheckedChangeListener{_, isChecked ->
            sendBt.isEnabled = isChecked
            when(isChecked){
                true -> socketService.start(::output)
                false -> socketService.close()
            }
        }

    }



    private fun output(txt: String) {
       runOnUiThread { output.text = "${output.text}\n\n$txt" }
    }
}
