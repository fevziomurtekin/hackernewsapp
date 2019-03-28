package com.fevziomurtekin.com.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fevziomurtekin.com.data.model.ItemModel
import org.jetbrains.annotations.NotNull

@Entity(tableName = "item")
data class ItemEntity(
    @PrimaryKey
    val id:Int?,
    val by:String?,
    val descendants:Int??,
    val deleted:Boolean?,
    val dead:Boolean?,
    val kids:MutableList<Int>?=null,
    val score:Int?,
    val time:Long?,
    val text:String?,
    val title:String?,
    val type:String?,
    val part:MutableList<Int>?=null,
    val parent:MutableList<Int>?=null,
    val poll:Int?,
    val url:String?
){
    companion object {
        fun from(item:ItemModel)=ItemEntity(
            item.id,
            item.by,
            item.descendants,
            item.deleted,
            item.dead,
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