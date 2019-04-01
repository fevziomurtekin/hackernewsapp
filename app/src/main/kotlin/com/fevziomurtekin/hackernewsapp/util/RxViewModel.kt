package com.fevziomurtekin.util

import androidx.lifecycle.ViewModel
import com.fevziomurtekin.hackernewsapp.util.SchedulerProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * ViewModel for Coroutines Jobs
 *
 * launch() - launch a Rx request
 * clear all request on stop
 */

abstract class RxViewModel(private val schedulerProvider: SchedulerProvider): ViewModel(){

    var jobs = listOf<Job>()

    fun launch (code:suspend CoroutineScope.() -> Unit){
        jobs += GlobalScope.launch(schedulerProvider.ui(),block = code)
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }


}