package com.joeynebula.socketecho

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.koin.android.ext.android.get
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //to inject
    private lateinit var socketService: ISocketService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //injected from di
        socketService = get()

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

    override fun onPause() {
        super.onPause()
        socketService.close()
    }




    private fun output(txt: String) {
       runOnUiThread { output.text = "${output.text}\n\n$txt" }
    }
}
