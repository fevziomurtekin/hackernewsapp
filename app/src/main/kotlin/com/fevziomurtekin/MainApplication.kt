package com.fevziomurtekin

import android.app.Application
import com.fevziomurtekin.di.onlineNewsApp
import org.koin.android.ext.android.startKoin

class MainApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin(this, onlineNewsApp)

    }
}