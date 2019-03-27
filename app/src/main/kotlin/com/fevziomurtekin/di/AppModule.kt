package com.fevziomurtekin.di

import androidx.room.Room
import com.fevziomurtekin.com.data.network.RetroInterface
import com.fevziomurtekin.com.data.room.Database
import com.fevziomurtekin.com.data.room.ItemRepositoryImpl
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

/**
 * App Compenents
 */

val appModule = applicationContext {

    //TODO add to viewmodels.
    //viewModel {  }

    // Retrofit Interface providers.
    bean { ItemRepositoryImpl(get(),get()) as RetroInterface }

    // provider to Room database.
    bean {
        Room.databaseBuilder(androidApplication(),Database::class.java,"hackernews-db")
            .build()
    }

    // Expose ItemDao directly
    bean { get<Database>().itemDao() }

    // Expose UserDao directly
    bean { get<Database>().userDao() }

}

// Gather all app modules.
val onlineNewsApp = listOf(appModule, remoteModule)