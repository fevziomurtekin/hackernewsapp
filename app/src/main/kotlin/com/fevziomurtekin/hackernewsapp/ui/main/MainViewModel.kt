package com.fevziomurtekin.hackernewsapp.ui.main

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.data.room.ItemRepository
import com.fevziomurtekin.hackernewsapp.util.*
import com.fevziomurtekin.util.*
import org.greenrobot.eventbus.EventBus
import timber.log.Timber


class MainViewModel(
    private val itemRepository: ItemRepository,
    schedulerProvider: SchedulerProvider
) : RxViewModel(schedulerProvider){


    private val mEvents = SingleLiveEvent<Event>()
    private val idList:MutableList<Int>?= mutableListOf()
    private var items : MutableList<ItemModel>? = mutableListOf()
    val events : LiveData<Event>
        get() = mEvents

    fun getNews(){
        /**
         * @Default value LoadingEvent
         */
        mEvents.value = LoadingEvent
        launch {
            try {
                items = itemRepository.getItems(0, idList!!).await()
                Timber.d(items.toString())
                EventBus.getDefault().post(items)
                mEvents.value = SuccessEvent
            } catch (e: Throwable) {
                Timber.d(e)
                mEvents.value = FailedEvent(Throwable())
            }
        }
    }

    fun showSplash(view: ConstraintLayout){ view.visibility = View.VISIBLE }

    fun hideSplash(view: ConstraintLayout){
        val animator = ObjectAnimator.ofFloat(view,"alpha",1f,0f)
        animator.interpolator = AccelerateInterpolator()

        animator.duration = 200
        animator.start()
    }

}