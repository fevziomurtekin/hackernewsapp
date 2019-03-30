package com.fevziomurtekin.hackernewsapp.util

import android.content.Context
import androidx.fragment.app.Fragment

interface FragmentExt{

    fun replaceFragment(retain:Boolean,holderId:Int,fragment: Fragment)

}