package com.fevziomurtekin.hackernewsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao{

    /**
     * Save all items
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(entities:MutableList<ItemEntity>?)

    /**
     * Find ItemEntity for text
     * @return MutableList<ItemEntity>
     * */
    @Query("SELECT * FROM item WHERE text LIKE :text OR title LIKE :text")
    fun findAllBy(text:String):MutableList<ItemEntity>

    /**
     * Find ItemEntity for id
     * @return ItemEntity
     * */
    @Query("SELECT * FROM item WHERE id= :id")
    fun findAllById(id: String):ItemEntity

}