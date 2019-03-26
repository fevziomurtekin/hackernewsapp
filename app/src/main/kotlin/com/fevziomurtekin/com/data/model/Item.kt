package com.fevziomurtekin.com.data.model

data class Item(
    val `by`: String,
    val descendants: Int,
    val deleted:Boolean,
    val dead:Boolean,
    val id: Int,
    val kids: MutableList<Int>,
    val score: Int,
    val time: Long,
    val text:String,
    val title: String,
    val type: String,
    val part:MutableList<Int>,
    val parent:MutableList<Int>,
    val poll:Int,
    val url: String
)