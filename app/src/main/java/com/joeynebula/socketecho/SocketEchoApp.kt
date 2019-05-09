package com.joeynebula.socketecho

import android.app.Application
import org.koin.android.ext.android.startKoin

class SocketEchoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        val moduleList = listOf(appModule)
        //starts up the DI container
        startKoin(this, moduleList)

    }
}