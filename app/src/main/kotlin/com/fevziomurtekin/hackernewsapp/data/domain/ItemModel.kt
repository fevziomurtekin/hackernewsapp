package com.fevziomurtekin.hackernewsapp.data.domain

import android.os.Parcelable
import com.fevziomurtekin.hackernewsapp.data.room.AskEntity
import com.fevziomurtekin.hackernewsapp.data.room.JobEntity
import com.fevziomurtekin.hackernewsapp.data.room.NewEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemModel(
    var id:Int?,
    var by:String?,
    var descendants:Int?,
    var deleted:Boolean?,
    var dead:Boolean?,
    var kids:MutableList<Int>?=null,
    var score:Int?,
    var time:Long?,
    var text:String?,
    var title:String?,
    var type:String?,
    var part:MutableList<Int>?=null,
    var parent:MutableList<Int>?=null,
    var poll:Int?,
    var url:String?
):Parcelable{
    companion object {
        fun fromNews(item: NewEntity)= ItemModel(
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

        fun fromJobs(item: JobEntity)= ItemModel(
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

        fun fromAsk(item: AskEntity)= ItemModel(
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

        fun fromDefault(result: ItemModel): ItemModel {
            if (result.id == null) {
                result.id=0
            }

            if (result.by == null) {
                result.by=""
            }

            if (result.descendants == null) {
                result.descendants=0
            }

            if (result.dead == null) {
                result.dead=false
            }

            if (result.deleted == null) {
                result.deleted = false
            }

            if (result.kids == null) {
                result.kids = mutableListOf()
            }

            if (result.score == null) {
                result.score = 0
            }

            if (result.time == null) {
                result.time = 0L
            }

            if (result.text == null) {
                result.text = ""
            }

            if (result.title == null) {
                result.title = ""
            }

            if (result.type == null) {
                result.type = ""
            }

            if (result.part == null) {
                result.part = mutableListOf()
            }

            if (result.parent == null) {
                result.parent= mutableListOf()
            }

            if (result.poll == null) {
                result.poll=0
            }

            if (result.url == null) {
                result.url=""
            }

            return  result
        }
    }
}