package com.fevziomurtekin.hackernewsapp.util

import java.lang.Exception
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

    fun urlToBy(url: String?): String {
        var returnString = ""
        var start=0
        var last=0

        if(!url.isNullOrEmpty()) {
            try{start = url.lastIndexOf("www")-1}catch (exp : Exception){}
            last  = url.indexOf("com")-1

            if(start==-2){
                start = url.lastIndexOf("//")+2
            }

            try{returnString = url.substring(start,last)}catch (exp:Exception){returnString=""}
            returnString = "(${returnString})"

        }else
            returnString =""

        return returnString
    }


}