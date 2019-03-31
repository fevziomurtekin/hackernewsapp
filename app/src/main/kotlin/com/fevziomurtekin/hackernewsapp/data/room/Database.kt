package com.fevziomurtekin.hackernewsapp.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NewEntity::class,JobEntity::class,AskEntity::class,UserEntity::class], version = 4)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase(){

    abstract fun itemDao() : ItemDao

    abstract fun userDao() : UserDao

}