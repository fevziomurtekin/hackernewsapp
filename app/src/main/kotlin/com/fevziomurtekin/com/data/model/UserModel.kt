package com.fevziomurtekin.com.data.model

data class UserModel(
    val about: String,
    val created: Int,
    val delay: Int,
    val id: String,
    val karma: Int,
    val submitted: List<Int>
)