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
import com.fevziomurtekin.hackernewsapp.data.room.ItemDao
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
         * Usage SplashScreen
         * @Default value LoadingEvent
         */
        mEvents.value = LoadingEvent
        launch {
            try {
                items = itemRepository.getItems(0, idList!!,true).await()
                Timber.d(items.toString())
                EventBus.getDefault().post(items)
                mEvents.value = SuccessEvent
            } catch (e: Throwable) {
                Timber.d(e)
                mEvents.value = FailedEvent(Throwable())
            }
        }
    }

    @Synchronized
    fun getItems(mood:Int,isReload:Boolean){
        /**
         * Usage BottomBar Click
         * @params mood.
         */
        var list = mutableListOf<ItemModel>()

        launch {
            val list=when (mood) {
                0 -> itemRepository.getItems(0, idList!!,isReload).await()
                1 -> itemRepository.getItems(1, idList!!,isReload).await()
                2 -> itemRepository.getItems(2, idList!!,isReload).await()
                else -> itemRepository.getItems(3, idList!!,isReload).await()
            }
            Timber.d(list.toString())
            EventBus.getDefault().post(list)
        }
    }

    fun clearAllTables(){
        itemRepository.clearAllTables()
    }

    fun showSplash(view: ConstraintLayout){ view.visibility = View.VISIBLE }

    fun hideSplash(view: ConstraintLayout){
        val animator = ObjectAnimator.ofFloat(view,"alpha",1f,0f)
        animator.interpolator = AccelerateInterpolator()

        animator.duration = 200
        animator.start()
    }

}