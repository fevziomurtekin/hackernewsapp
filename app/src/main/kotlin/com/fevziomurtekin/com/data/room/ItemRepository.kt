package com.fevziomurtekin.com.data.room

import com.fevziomurtekin.com.data.model.ItemModel
import com.fevziomurtekin.com.data.network.RetroInterface
import kotlinx.coroutines.*

interface ItemRepository{

    /**
     * Get items from given id list
     * @return MutableList<Item>>
     **/
    fun getItems(mood:Int,idList:MutableList<Int>):Deferred<MutableList<ItemModel>>?


    /**
     * Get item for given id
     * @return Item
     **/
    fun getItemById(id:String):Deferred<ItemModel>?

}

/**
 * Item Repository
 * Make use of RetroInterface & add some cache.*/
class ItemRepositoryImp(
    private val retroInterface:RetroInterface,
    private val itemDao: ItemDao
):ItemRepository
{
    override fun getItems(mood: Int,idList: MutableList<Int>): Deferred<MutableList<ItemModel>>? = GlobalScope.async {
        when(mood){
            0 -> return@async getNews(idList).await()
            1 -> return@async getJobs(idList).await()
            2 -> return@async getTops(idList).await()
            else -> return@async getAsks(idList).await()
        }
    }

    private fun getNews(idList: MutableList<Int>) : Deferred<MutableList<ItemModel>> = GlobalScope.async {

        /**
         * idList if equal null fetch data in newStories api, not equal idList
         * @return MutableList<Int>*/

        val list = if(idList.isEmpty()) {
            retroInterface.newStories().await()
        }else{
            idList
        }

        val itemList:MutableList<ItemModel> = mutableListOf()

        GlobalScope.async {
            list.map {
                /**
                 * fetching all data then this get details this ids
                 * @return MutableList<Item>*/
                val url = "https://hacker-news.firebaseio.com/v0/item/$it.json?print=pretty"
                val item = retroInterface.itemDetails(url).await()
                itemList.add(item)
            }
        }

        return@async itemList
    }

    private fun getJobs(idList: MutableList<Int>) : Deferred<MutableList<ItemModel>> = GlobalScope.async {

        /**
         * idList if equal null fetch data in jobStories api, not equal idList
         * @return MutableList<Int>*/

        val list = if(idList.isEmpty()) {
            retroInterface.jobStories().await()
        }else{
            idList
        }

        val itemList:MutableList<ItemModel> = mutableListOf()

        GlobalScope.async {
            list.map {
                /**
                 * fetching all data then this get details this ids
                 * @return MutableList<Item>*/
                val url = "https://hacker-news.firebaseio.com/v0/item/$it.json?print=pretty"
                val item = retroInterface.itemDetails(url).await()
                itemList.add(item)
            }
        }

        return@async itemList
    }

    private fun getTops(idList: MutableList<Int>) : Deferred<MutableList<ItemModel>> = GlobalScope.async {

        /**
         * idList if equal null fetch data in topStories api, not equal idList
         * @return MutableList<Int>*/

        val list = if(idList.isEmpty()) {
            retroInterface.topStories().await()
        }else{
            idList
        }

        val itemList:MutableList<ItemModel> = mutableListOf()

        GlobalScope.async {
            list.map {
                /**
                 * fetching all data then this get details this ids
                 * @return MutableList<Item>*/
                val url = "https://hacker-news.firebaseio.com/v0/item/$it.json?print=pretty"
                val item = retroInterface.itemDetails(url).await()
                itemList.add(item)
            }
        }

        return@async itemList
    }

    private fun getAsks(idList: MutableList<Int>) : Deferred<MutableList<ItemModel>> = GlobalScope.async {

        /**
         * idList if equal null fetch data in askStories api, not equal idList
         * @return MutableList<Int>*/

        val list = if(idList.isEmpty()) {
            retroInterface.askStories().await()
        }else{
            idList
        }

        val itemList:MutableList<ItemModel> = mutableListOf()

        GlobalScope.async {
            list.map {
                /**
                 * fetching all data then this get details this ids
                 * @return MutableList<Item>*/
                val url = "https://hacker-news.firebaseio.com/v0/item/$it.json?print=pretty"
                val item = retroInterface.itemDetails(url).await()
                itemList.add(item)
            }
        }

        return@async itemList
    }



    override fun getItemById(id: String): Deferred<ItemModel>? = GlobalScope.async {
        ItemModel.from(itemDao.findAllById(id))
    }

}