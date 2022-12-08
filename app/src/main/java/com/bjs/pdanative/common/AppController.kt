package com.bjs.pdanative.common

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AppController : Application(), Configuration.Provider {


    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        //For using app context
        var instance: AppController? = null
        fun globalContext(): Context {
            return instance!!.applicationContext
        }
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

}