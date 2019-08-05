package com.fevziomurtekin.hackernewsapp.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fevziomurtekin.hackernewsapp.data.domain.UserModel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id:String,
    val about:String,
    val created:Int,
    val delay:Int,
    val karma:Int,
    val submitted:List<Int>?=null
):Parcelable{
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