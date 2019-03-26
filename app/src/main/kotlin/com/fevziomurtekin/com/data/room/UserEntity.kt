package com.fevziomurtekin.com.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fevziomurtekin.com.data.model.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id:String,
    val about:String,
    val created:Int,
    val delay:Int,
    val submitted:List<Int>
){
    companion object {
        fun from(user: User)=UserEntity(
            user.id,
            user.about,
            user.created,
            user.delay,
            user.submitted
        )
    }
}