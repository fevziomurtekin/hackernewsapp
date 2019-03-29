package com.fevziomurtekin.hackernewsapp.data.network

import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface RetroInterface{

    @GET("newstories.json?print=pretty")
    @Headers("Content-type: application/json")
    fun newStories():Deferred<MutableList<Int>>

    @GET("askstories.json?print=pretty")
    @Headers("Content-type: application/json")
    fun askStories():Deferred<MutableList<Int>>

    @GET("topstories.json?print=pretty")
    @Headers("Content-type: application/json")
    fun topStories():Deferred<MutableList<Int>>

    @GET("jobstories.json?print=pretty")
    @Headers("Content-type: application/json")
    fun jobStories():Deferred<MutableList<Int>>

    @GET
    @Headers("Content-type: application/json")
    fun itemDetails(@Url url:String):Deferred<ItemModel>


}