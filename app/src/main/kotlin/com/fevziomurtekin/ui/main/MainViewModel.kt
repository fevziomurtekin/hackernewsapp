package com.fevziomurtekin.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import com.fevziomurtekin.com.data.network.RetroInterface
import com.fevziomurtekin.util.*
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.util.*

class MainViewModel(
    private val retroInterface: RetroInterface,
    schedulerProvider: SchedulerProvider
) : RxViewModel(schedulerProvider){

    private val mEvents = SingleLiveEvent<Event>()
    val events : LiveData<Event>
        get() = mEvents

    fun getNews(){
        /**
         * @Default value LoadingEvent
         */
        mEvents.value = LoadingEvent
        launch {
            try {
                retroInterface.newStories().await()

                mEvents.value = SuccessEvent
            }catch (e:Throwable){
                    mEvents.value = FailedEvent(Throwable())
            }
        }
    }

    fun showSplash(imageView: ImageView){ imageView.visibility = View.VISIBLE }

    fun hideSplash(imageView: ImageView){
        val animator = ObjectAnimator.ofFloat(imageView,"alpha",1f,0f)
        animator.interpolator = AccelerateInterpolator()

        animator.duration = 200
        animator.start()
    }

}