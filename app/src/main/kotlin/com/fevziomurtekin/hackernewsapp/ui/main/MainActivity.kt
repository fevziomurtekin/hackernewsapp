package com.fevziomurtekin.hackernewsapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import androidx.room.RoomDatabase
import com.fevziomurtekin.hackernewsapp.R
import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.ui.adapter.NewsAdapter
import com.fevziomurtekin.hackernewsapp.ui.news.NewsFragment
import com.fevziomurtekin.hackernewsapp.util.FragmentExt
import com.fevziomurtekin.hackernewsapp.util.LoadingEvent
import com.fevziomurtekin.hackernewsapp.util.SuccessEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.architecture.ext.viewModel
import timber.log.Timber


class MainActivity : AppCompatActivity(),FragmentExt, Toolbar.OnMenuItemClickListener {


    /***
     * @param holderId -> FrameLayout that the fragment will be placed in
     * @param fragment -> Fragment instance
     * @param retain -> Whether or not previous fragment will be destroyed of will retain its state
     */

    override fun replaceFragment(retain: Boolean, holderId: Int,fragment:Fragment) {
        if (retain) {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_rigth,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right)
                .add(holderId,fragment,fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit();
        } else {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_rigth,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right)
                .replace(holderId,fragment,fragment.javaClass.simpleName)
                .addToBackStack(fragment.javaClass.simpleName)
                .commit()
        }

    }

    //Declare MainViewModel with Koin and allow constructor di.
    val viewModel by viewModel<MainViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomappbar.replaceMenu(R.menu.bottomappbar_menu)
        bottomappbar.setOnMenuItemClickListener(this)
        //recyclerview.layoutManager = LinearLayoutManager(applicationContext)


        replaceFragment(false,R.id.framelayout,NewsFragment.newInstance(0))

        viewModel.events.observe(this, Observer {
            it?.let {
                when(it){
                    LoadingEvent -> viewModel.showSplash(splashscreen as ConstraintLayout)
                    SuccessEvent -> viewModel.hideSplash(splashscreen as ConstraintLayout)
                    else         -> showError()
                }
            }
        })

        viewModel.getNews()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.appbar_user->{
                //viewModel.getUser
                replaceFragment(true,R.id.framelayout,NewsFragment.newInstance(1))
            }
            R.id.appbar_ask->{
                viewModel.getItems(2,false)
                replaceFragment(true,R.id.framelayout,NewsFragment.newInstance(2))
            }
            R.id.appbar_jobs->{
                viewModel.getItems(3,false)
                replaceFragment(true,R.id.framelayout,NewsFragment.newInstance(3))
            }
        }

        return true
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


    override fun onDestroy() {
        super.onDestroy()
        //TODO clear all tables.
    }

}
