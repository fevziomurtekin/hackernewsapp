package com.fevziomurtekin.hackernewsapp.util

import kotlinx.coroutines.Dispatchers

class ApplicationSchedulerProvider : SchedulerProvider {
    override fun ui() = Dispatchers.Main
}