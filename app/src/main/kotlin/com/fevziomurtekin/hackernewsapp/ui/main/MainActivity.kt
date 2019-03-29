package com.fevziomurtekin.hackernewsapp.ui.main

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fevziomurtekin.hackernewsapp.R
import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.util.LoadingEvent
import com.fevziomurtekin.hackernewsapp.util.SuccessEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.architecture.ext.viewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    //Declare MainViewModel with Koin and allow constructor di.
    private val viewModel by viewModel<MainViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomappbar.replaceMenu(R.menu.bottomappbar_menu)

        recyclerview.layoutManager = LinearLayoutManager(applicationContext)

        viewModel.events.observe(this, Observer {
            it?.let {
                when(it){
                    LoadingEvent -> viewModel.showSplash(splashscreen as ImageView)
                    SuccessEvent -> viewModel.hideSplash(splashscreen as ImageView)
                    else         -> showError()
                }
            }
        })

        viewModel.getNews()
    }

    fun showError(){
        Snackbar.make(
            main_layout,
            getString(R.string.loading_error),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.retry, {
                viewModel.getNews()
            })
            .show()
    }

    /**
     * Defined and created EventBus.
     * Fetch Items and Update Recyclerview from usage EventBus.
     * @return MutableList<ItemModel>
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventItems(items : MutableList<ItemModel>){
        Timber.d("${items.toString()}")

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}
