package com.fevziomurtekin.hackernewsapp.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import androidx.room.RoomDatabase
import com.fevziomurtekin.hackernewsapp.R
import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.ui.adapter.NewsAdapter
import com.fevziomurtekin.hackernewsapp.ui.news.NewsFragment
import com.fevziomurtekin.hackernewsapp.util.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crash.FirebaseCrash
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.bundleOf
import org.koin.android.architecture.ext.viewModel
import timber.log.Timber
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity()
    , View.OnClickListener {

    private var onExitCount:Int=0
    private var floatingCount:Int=0
    private var mood=0

    //Declare MainViewModel with Koin and allow constructor di.
    val viewModel by viewModel<MainViewModel> ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Add to Firebase Analytics.
         */
        FirebaseAnalytics.getInstance(this)


        bottomappbar.replaceMenu(R.menu.bottomappbar_menu)
        btn_search.setOnClickListener(this)


        //bottom app bar support.
        setSupportActionBar(bottomappbar)

        //default selected item news.
        viewModel.unCheckedViews(bottomappbar)
        bottomappbar.navigationIcon!!.setTint(Color.WHITE)

        /**
         * post search value with Eventbus in NewsFragment.
         * @return MutableList<ItemModel>
         **/
        edt_search.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                searchNews()
            }
            false
        }


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottomappbar_menu, menu)
        return true
    }

    fun searchNews(){
        Ext.hideKeyboard(this)
        floatingCount++
        viewModel.hideEditText(edt_search)
        viewModel.searchNews(mood,edt_search.text.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        viewModel.unCheckedViews(bottomappbar)


        when(item!!.itemId){
            android.R.id.home->{
                bottomappbar.navigationIcon!!.setTint(Color.WHITE)
                mood=0
                viewModel.getItems(0,false)
                Navigation.findNavController(default_fragment.view!!).navigate(R.id.action_news, bundleOf(IntentArguments.ARG_INTENT_ID to mood))
            }
            R.id.appbar_user->{
                item!!.icon.setTint(Color.WHITE)
                //viewModel.getUser
                mood=1
            }
            R.id.appbar_ask->{
                item!!.icon.setTint(Color.WHITE)
                mood=2
                viewModel.getItems(2,false)
                Navigation.findNavController(default_fragment.view!!).navigate(R.id.action_news, bundleOf(IntentArguments.ARG_INTENT_ID to mood))

            }
            R.id.appbar_jobs->{
                item!!.icon.setTint(Color.WHITE)
                mood=3
                viewModel.getItems(3,false)
                Navigation.findNavController(default_fragment.view!!).navigate(R.id.action_news, bundleOf(IntentArguments.ARG_INTENT_ID to mood))
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_search->{
                searchAnimation()
            }
        }
    }

    private fun searchAnimation() {
        Ext.hideKeyboard(this)
        edt_search.apply { setText("") }

        if(floatingCount%2==0){
            viewModel.showEditText(edt_search)
        }else{
            viewModel.hideEditText(edt_search)
        }

        floatingCount++
    }


    fun showError(){
        Snackbar.make(
            main_layout,
            getString(R.string.loading_error),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.retry) {
                viewModel.getNews()
            }
            .show()
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearAllTables()
    }

    override fun onBackPressed() {

        /**
         *  if Fragment backstackentrycount 1 => NewsFragment.
         */

        if(supportFragmentManager.backStackEntryCount==1 && onExitCount == 0){
            Toast.makeText(this@MainActivity,getString(R.string.press_back_again),Toast.LENGTH_SHORT).show()
            onExitCount++
        }else if(supportFragmentManager.backStackEntryCount==1 && onExitCount==1){
            moveTaskToBack(true)
            exitProcess(-1)
            onExitCount=0
        }else{
            supportFragmentManager.popBackStack()
        }

    }



}
