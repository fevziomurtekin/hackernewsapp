package com.fevziomurtekin.hackernewsapp.data.network.fcm

import android.app.job.JobParameters
import android.app.job.JobService
import timber.log.Timber

class MyJobService : JobService() {

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Timber.d( "Performing long running task in scheduled job")
        // TODO(developer): add long running task here.
        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }

    companion object {
        private val TAG = "MyJobService"
    }
}