package com.joeynebula.socketecho

import android.widget.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.experimental.builder.singleBy
import org.koin.standalone.StandAloneContext.startKoin
import org.robolectric.Robolectric

class MainActivityTests : AndroidTest() {
    companion object {
        init {
            val mockModule = module {
                singleBy<ISocketService, MockSocketService>()
            }

            startKoin(listOf(mockModule))
        }
    }

    /*
    These are a little more integration tests than true unit tests but with the lack
    of much in terms of business logic and with the simplicity of the app I thought these would be more interesting
     */

    @Test
    fun shouldShowHandshakeMessageOnOpen(){
        //robolectric has changed since I last used it but I am going to use the deprecated classes I know in the interest of time
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val startButton = activity.findViewById<ToggleButton>(R.id.startTb)

        val outputEt = activity.findViewById<TextView>(R.id.output)

        startButton.performClick()

        assert(outputEt.text.contains("Attempting to open socket connection"))
    }

    @Test
    fun shouldDisableSendButtonWhenConnectionClosed(){
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val startButton = activity.findViewById<ToggleButton>(R.id.startTb)
        val sendButton = activity.findViewById<Button>(R.id.sendBt)

        assert(!startButton.isChecked && !sendButton.isEnabled)

    }

    @Test
    fun inputShouldClearTextAfterSend(){
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val startButton = activity.findViewById<ToggleButton>(R.id.startTb)
        val sendButton = activity.findViewById<Button>(R.id.sendBt)
        val inputEt = activity.findViewById<AutoCompleteTextView>(R.id.inputTextTv)

        startButton.performClick()
        inputEt.setText("Hello")
        sendButton.performClick()

        assert(inputEt.text.isBlank())
    }
}