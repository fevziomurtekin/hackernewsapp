package com.fevziomurtekin.com.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters{
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun intListToString(list: List<Int>) = Gson().toJson(list)

    @TypeConverter
    fun stringTointList(text:String) = Gson().fromJson(text,Array<Int>::class.java).toMutableList()
}