package com.fevziomurtekin.hackernewsapp.ui.newdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.fevziomurtekin.hackernewsapp.R
import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.util.IntentArguments
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.newsdetails.*
import org.koin.android.architecture.ext.viewModel

class NewDetailsFragment: Fragment(){

    private val viewModel by viewModel<NewsDetailsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.newsdetails,container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loadWebView(webview,
            Gson().fromJson(arguments!!.getString(IntentArguments.ARG_INTENT_ID),ItemModel::class.java),progressbarw)
    }



    companion object {
        fun newInstance(item:ItemModel?): NewDetailsFragment{
            val args = Bundle()
            args.putString(IntentArguments.ARG_INTENT_ID,GsonBuilder().create().toJson(item))
            val fragment = NewDetailsFragment()
            fragment.arguments = args
            return fragment

        }
    }
}