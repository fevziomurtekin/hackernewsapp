package com.fevziomurtekin.hackernewsapp.util

import java.sql.Date
import java.sql.Timestamp
import java.util.*

object Ext{

    fun getHours(time:Long?):String{

        if(time!=null) {
            val now = Calendar.getInstance()
            val hours = now.get(Calendar.HOUR_OF_DAY)

            val timeStamp = Timestamp(time)
            val date = Date(timeStamp.time)
            val newsCal = Calendar.getInstance()
            newsCal.time = date
            val newHours = newsCal.get(Calendar.HOUR_OF_DAY)

            val diff = hours - newHours

            return "${diff} hours"
        }else return ""

    }


}