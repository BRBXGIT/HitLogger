package com.example.hitlogger.app

import android.app.Application
import com.example.hitlogger.di.AppComponent
import com.example.hitlogger.di.DaggerAppComponent

class DaggerApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}