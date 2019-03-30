package com.fevziomurtekin.hackernewsapp.ui.newdetails

import android.webkit.WebHistoryItem
import android.webkit.WebView
import androidx.core.widget.ContentLoadingProgressBar
import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.util.SchedulerProvider
import com.fevziomurtekin.util.RxViewModel
import kotlinx.android.synthetic.main.newsdetails.*

class NewsDetailsViewModel(
    schedulerProvider: SchedulerProvider)
    : RxViewModel(schedulerProvider){


    fun loadWebView(webView: WebView,item: ItemModel,progressBar: ContentLoadingProgressBar){
        val settings = webView.settings
        settings.builtInZoomControls =false
        settings.javaScriptEnabled = true
        settings.domStorageEnabled =true
        settings.defaultFontSize=14
        settings.setAppCacheEnabled(true)

        if(item!=null){
            webView.loadUrl(item.url)
            progressBar.hide()
        }else progressBar.hide()




    }

}