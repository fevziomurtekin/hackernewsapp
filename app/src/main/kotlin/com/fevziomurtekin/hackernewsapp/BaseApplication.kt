package com.fevziomurtekin.hackernewsapp

import android.app.Application
import com.fevziomurtekin.di.onlineNewsApp
import org.koin.android.ext.android.startKoin

class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin(this, onlineNewsApp)

    }
}