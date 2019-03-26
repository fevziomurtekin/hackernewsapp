package com.fevziomurtekin.com.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ItemEntity::class,UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase(){

    abstract fun itemDao() : ItemDao

    abstract fun userDao() : UserDao

}