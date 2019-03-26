package com.fevziomurtekin.com.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fevziomurtekin.com.data.model.User

@Dao
interface UserDao{

    /**
     * Save all User datas.
     **/
    @Insert
    fun saveAll(entities:MutableList<UserEntity>)


    /**
     * Find UserEntity by username
     * @return MutableList<UserEntitiy>
     * */
    @Query("SELECT * FROM user WHERE about LIKE :text")
    fun findUsers(text:String):MutableList<UserEntity>


    /**
     * Find UserEntity by id
     * @return UserEntity
     * */

    @Query("SELECT * FROM user WHERE id = :id")
    fun findUserById(id:String): UserEntity

}