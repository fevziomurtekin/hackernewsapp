package com.fevziomurtekin.com.data.network

import com.fevziomurtekin.com.data.model.Item
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetroInterface{

    @GET("/v0/newstories.json?print=pretty")
    @Headers("Content-type: application/json")
    fun newStories():Deferred<MutableList<Int>>

    @GET("/vo/askstories.json?print=pretty")
    @Headers("Content-type: application/json")
    fun askStories():Deferred<MutableList<Int>>

    @GET("/vo/topstories.json?print=pretty")
    @Headers("Content-type: application/json")
    fun topStories():Deferred<MutableList<Int>>

    @GET("/vo/jobstories.json?print=pretty")
    @Headers("Content-type: application/json")
    fun jobStories():Deferred<MutableList<Int>>

    @GET("/vo/item/")
    @Headers("Content-type: application/json")
    fun itemDetails():Deferred<Item>


}