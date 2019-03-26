package com.fevziomurtekin.com.data.room

import com.fevziomurtekin.com.data.model.Item
import kotlinx.coroutines.Deferred

interface ItemRepository{

    /**
     * Get items from given id list
     * @return MutableList<Item>>
     **/
    fun getItems(idList:MutableList<Int>):Deferred<MutableList<Item>>


    /**
     * Get item for given id
     * @return Item
     **/
    fun getItemById(id:String):Deferred<Item>

}