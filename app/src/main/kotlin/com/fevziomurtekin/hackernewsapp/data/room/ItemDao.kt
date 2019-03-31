package com.fevziomurtekin.hackernewsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.Job

@Dao
interface ItemDao{

    /**
     * Save all new items.
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllNews(entities:MutableList<NewEntity>?)

    /**
     * Save all job items.
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllJobs(entities:MutableList<JobEntity>?)

    /**
     * Save all ask items.
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllAsk(entities:MutableList<AskEntity>?)

    /**
     * Find NewEntity for text
     * @return MutableList<NewEntity>
     * */
    @Query("SELECT * FROM new WHERE text LIKE :text OR title LIKE :text")
    fun findAllByNews(text:String):MutableList<NewEntity>

    /**
     * Find JobEntity for text
     * @return MutableList<JobEntity>
     * */
    @Query("SELECT * FROM job WHERE text LIKE :text OR title LIKE :text")
    fun findAllByJobs(text:String):MutableList<JobEntity>

    /**
     * Find AskEntity for text
     * @return MutableList<AskEntity>
     * */
    @Query("SELECT * FROM ask WHERE text LIKE :text OR title LIKE :text")
    fun findAllByAsks(text:String):MutableList<AskEntity>

    /**
     * Find NewEntity for id
     * @return NewEntity
     * */
    @Query("SELECT * FROM new WHERE id= :id")
    fun findAllByNewId(id: String):NewEntity

    /**
     * Find JobEntity for id
     * @return JobEntity
     * */
    @Query("SELECT * FROM job WHERE id= :id")
    fun findAllByJobId(id: String):JobEntity

    /**
     * Find AskEntity for id
     * @return AskEntity
     * */
    @Query("SELECT * FROM ask WHERE id= :id")
    fun findAllByAskId(id: String):AskEntity

}