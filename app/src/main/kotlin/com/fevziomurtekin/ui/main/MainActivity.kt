package com.fevziomurtekin.com

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.fevziomurtekin.ui.main.MainViewModel
import com.fevziomurtekin.util.FailedEvent
import com.fevziomurtekin.util.LoadingEvent
import com.fevziomurtekin.util.SuccessEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel

class MainActivity : AppCompatActivity() {

    //Declare MainViewModel with Koin and allow constructor di.
    private val viewModel by viewModel<MainViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomappbar.replaceMenu(R.menu.bottomappbar_menu)

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

}
