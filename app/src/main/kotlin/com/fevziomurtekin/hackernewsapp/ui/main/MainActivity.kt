package com.fevziomurtekin.hackernewsapp.ui.main

import android.os.Bundle
import android.text.Editable
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
import com.fevziomurtekin.hackernewsapp.util.Ext
import com.fevziomurtekin.hackernewsapp.util.FragmentExt
import com.fevziomurtekin.hackernewsapp.util.LoadingEvent
import com.fevziomurtekin.hackernewsapp.util.SuccessEvent
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crash.FirebaseCrash
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.architecture.ext.viewModel
import timber.log.Timber
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(),FragmentExt
    , View.OnClickListener {

    private var onExitCount:Int=0
    private var floatingCount:Int=0
    private var mood=0

    //Declare MainViewModel with Koin and allow constructor di.
    val viewModel by viewModel<MainViewModel> ()


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


        /**
         * post search value with Eventbus in NewsFragment.
         * @return MutableList<ItemModel>
         **/
        edt_search.setOnEditorActionListener(object: TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchNews()
                }
                return false
            }
        })

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
        when(item!!.itemId){
            android.R.id.home->{
                mood=0
                viewModel.getItems(0,false)
                replaceFragment(true,R.id.framelayout,NewsFragment.newInstance(mood))
            }
            R.id.appbar_user->{
                //viewModel.getUser
                mood=1
                //replaceFragment(true,R.id.framelayout,NewsFragment.newInstance(mood))
            }
            R.id.appbar_ask->{
                mood=2
                viewModel.getItems(2,false)
                replaceFragment(true,R.id.framelayout,NewsFragment.newInstance(mood))
            }
            R.id.appbar_jobs->{
                mood=3
                viewModel.getItems(3,false)
                replaceFragment(true,R.id.framelayout,NewsFragment.newInstance(mood))
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
