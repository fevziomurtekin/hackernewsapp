package com.fevziomurtekin.hackernewsapp.ui.main

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.view.menu.MenuBuilder
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import com.fevziomurtekin.hackernewsapp.R
import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.data.room.ItemDao
import com.fevziomurtekin.hackernewsapp.data.room.ItemRepository
import com.fevziomurtekin.hackernewsapp.util.*
import com.fevziomurtekin.util.*
import com.google.android.material.bottomappbar.BottomAppBar
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

    fun searchNews(mood: Int,text:String){
        EventBus.getDefault().post(itemRepository.getNewsBySearch(mood,text))
    }

    fun clearAllTables(){
        itemRepository.clearAllTables()
    }

    @SuppressLint("RestrictedApi")
    fun unCheckedViews(bottomAppBar: BottomAppBar){
        bottomAppBar.navigationIcon!!.setTint(Color.parseColor("#FFC768"))
        (bottomAppBar.menu as MenuBuilder).actionItems.forEach {
            it.icon.setTint(Color.parseColor("#FFC768"))
        }
    }

    fun showEditText(view: EditText){
        val animator = ObjectAnimator.ofFloat(view,"alpha",0f,1f)
        animator.interpolator = AccelerateInterpolator()

        animator.duration = 200
        animator.start()
    }

    fun hideEditText(view: EditText){
        val animator = ObjectAnimator.ofFloat(view,"alpha",1f,0f)
        animator.interpolator = AccelerateInterpolator()

        animator.duration = 200
        animator.start()
    }

    fun showSplash(view: ConstraintLayout){ view.visibility = View.VISIBLE }

    fun hideSplash(view: ConstraintLayout){
        val animator = ObjectAnimator.ofFloat(view,"alpha",1f,0f)
        animator.interpolator = AccelerateInterpolator()

        animator.duration = 200
        animator.start()
    }

}