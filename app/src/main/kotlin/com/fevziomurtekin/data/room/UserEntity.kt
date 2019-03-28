package com.fevziomurtekin.com.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fevziomurtekin.com.data.model.UserModel

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id:String,
    val about:String,
    val created:Int,
    val delay:Int,
    val karma:Int,
    val submitted:List<Int>?=null
){
    companion object {
        fun from(user: UserModel)=UserEntity(
            user.id,
            user.about,
            user.created,
            user.delay,
            user.karma,
            user.submitted
        )
    }
}