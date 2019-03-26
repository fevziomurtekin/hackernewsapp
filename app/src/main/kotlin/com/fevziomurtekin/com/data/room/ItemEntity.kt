package com.fevziomurtekin.com.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fevziomurtekin.com.data.model.Item

@Entity(tableName = "item")
data class ItemEntity(
    @PrimaryKey
    val id:Int,
    val by:String,
    val descendants:Int,
    val deleted:Boolean,
    val kids:MutableList<Int>,
    val score:Int,
    val time:Long,
    val text:String,
    val title:String,
    val type:String,
    val part:MutableList<Int>,
    val parent:MutableList<Int>,
    val poll:Int,
    val url:String
){
    companion object {
        fun from(item:Item)=ItemEntity(
            item.id,
            item.by,
            item.descendants,
            item.deleted,
            item.kids,
            item.score,
            item.time,
            item.text,
            item.title,
            item.type,
            item.part,
            item.parent,
            item.poll,
            item.url
        )
    }
}