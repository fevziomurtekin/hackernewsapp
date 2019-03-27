package com.fevziomurtekin.com.data.model

import com.fevziomurtekin.com.data.room.UserEntity

data class UserModel(
    val about: String,
    val created: Int,
    val delay: Int,
    val id: String,
    val karma: Int,
    val submitted: List<Int>
){
    companion object {
        fun from(userEntity: UserEntity) = UserModel(
            userEntity.about,
            userEntity.created,
            userEntity.delay,
            userEntity.id,
            userEntity.karma,
            userEntity.submitted
        )

    }
}