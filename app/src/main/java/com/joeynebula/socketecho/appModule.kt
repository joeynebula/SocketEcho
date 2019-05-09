package com.joeynebula.socketecho

import okhttp3.OkHttpClient
import org.koin.dsl.module.module

val appModule = module {
    single<ISocketService>{
        val httpClient = OkHttpClient()

        return@single SocketService(httpClient)

    }
}