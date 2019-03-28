package com.fevziomurtekin.di

import androidx.room.Room
import com.fevziomurtekin.com.data.network.RetroInterface
import com.fevziomurtekin.com.data.room.*
import com.fevziomurtekin.ui.main.MainViewModel
import com.fevziomurtekin.util.ApplicationSchedulerProvider
import com.fevziomurtekin.util.SchedulerProvider
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

/**
 * App Compenents
 */

val appModule = applicationContext {


    viewModel { MainViewModel(get(),get()) }

    // ItemRepository providers.
    bean { ItemRepositoryImpl(get(),get()) as ItemRepository }

    bean { UserRepositoryImpl(get(),get()) as UserRepository }

    // provider to Room database.
    bean {
        Room.databaseBuilder(androidApplication(),Database::class.java,"hackernews-db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    bean { ApplicationSchedulerProvider() as SchedulerProvider }

    // Expose ItemDao directly
    bean { get<Database>().itemDao() }

    // Expose UserDao directly
    bean { get<Database>().userDao() }

}

// Gather all app modules.
val onlineNewsApp = listOf(appModule, remoteModule)