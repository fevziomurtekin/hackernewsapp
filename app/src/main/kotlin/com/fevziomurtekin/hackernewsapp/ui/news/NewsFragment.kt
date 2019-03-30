package com.fevziomurtekin.hackernewsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fevziomurtekin.hackernewsapp.R
import com.google.gson.GsonBuilder

class NewsFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.naju_fragments,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {

        fun newInstance() = NewsFragment().let {
            val args = Bundle()

        }

    }
}