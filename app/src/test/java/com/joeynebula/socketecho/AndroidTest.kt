package com.joeynebula.socketecho

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidTest.ApplicationStub::class,
    sdk = [23]
)
abstract class AndroidTest : KoinTest {

    fun context(): Context {
        return ApplicationProvider.getApplicationContext()
    }

    fun cacheDir(): File {
        return context().cacheDir
    }

    internal class ApplicationStub : Application()
}