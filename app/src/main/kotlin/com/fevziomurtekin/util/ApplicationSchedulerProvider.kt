package com.fevziomurtekin.util

import android.provider.Contacts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class ApplicationSchedulerProvider : SchedulerProvider {
    override fun ui() = Dispatchers.IO
}