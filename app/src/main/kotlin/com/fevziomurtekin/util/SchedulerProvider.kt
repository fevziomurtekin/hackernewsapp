package com.fevziomurtekin.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Rx Scheduler Provider.
 */

interface SchedulerProvider {
    fun ui(): CoroutineDispatcher
}