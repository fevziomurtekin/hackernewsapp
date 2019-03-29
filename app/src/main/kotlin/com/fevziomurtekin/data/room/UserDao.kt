package com.fevziomurtekin.com.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fevziomurtekin.com.data.model.UserModel

@Dao
interface UserDao{

    /**
     * Save all User data.
     **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(entities:UserEntity)

    /**
     * Find UserEntity by id
     * @return UserEntity
     * */

    @Query("SELECT * FROM user WHERE id = :id")
    fun findUserById(id:String): UserEntity

}