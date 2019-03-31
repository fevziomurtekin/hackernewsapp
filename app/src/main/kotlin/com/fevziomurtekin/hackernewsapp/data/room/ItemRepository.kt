package com.fevziomurtekin.hackernewsapp.data.room

import com.fevziomurtekin.hackernewsapp.data.domain.ItemModel
import com.fevziomurtekin.hackernewsapp.data.network.RetroInterface
import kotlinx.coroutines.*
import timber.log.Timber

interface ItemRepository{

    /**
     * @param mood  -> return news type.
     * @param idList
     * @param isReload -> refresh news.
     * Get items from given id list
     * @return MutableList<Item>>
     **/
    fun getItems(mood:Int,idList:MutableList<Int>,isReload:Boolean):Deferred<MutableList<ItemModel>>


    /**
     * Get new for given id
     * @return Item
     **/
    fun getItemByNewId(id:String):Deferred<ItemModel>?

    /**
     * Get job for given id
     * @return Item
     **/
    fun getItemByJobId(id:String):Deferred<ItemModel>?

    /**
     * Get ask for given id
     * @return Item
     **/
    fun getItemByAskId(id:String):Deferred<ItemModel>?

}

/**
 * Item Repository
 * Make use of RetroInterface & add some cache.*/
class ItemRepositoryImpl(
    private val retroInterface:RetroInterface,
    private val itemDao: ItemDao
):ItemRepository
{
    override fun getItems(mood: Int,idList: MutableList<Int>,isReload: Boolean): Deferred<MutableList<ItemModel>> = GlobalScope.async {
        when(mood){
            0 -> return@async getNews(idList,isReload).await()
            1 -> return@async getTops(idList,isReload).await()
            2 -> return@async getAsks(idList,isReload).await()
            else -> return@async getJobs(idList,isReload).await()
        }
    }

    @Synchronized
    suspend fun getNews(idList: MutableList<Int>,isReload: Boolean) : Deferred<MutableList<ItemModel>> = GlobalScope.async {

        /**
         * if news saved from room getAllNews else fetch network
         */

        if(!itemDao.getAllNews().isNullOrEmpty() && !isReload){
            val news = mutableListOf<ItemModel>()
            itemDao.getAllNews().forEach {
                val item = ItemModel.fromNews(it)
                news.add(item)
            }
            return@async news
        }else {

            /**
             * idList if equal null fetch data in newStories api, not equal idList
             * @return MutableList<Int>*/
            val list = if (idList.isEmpty()) {
                retroInterface.newStories().await()
            } else {
                idList
            }

            val itemList: MutableList<ItemModel> = mutableListOf()
            val entityList: MutableList<NewEntity> = mutableListOf()

            list.take(30).map {
                /**
                 * fetching all data then this get details this ids
                 * @return MutableList<Item>*/
                val url = "https://hacker-news.firebaseio.com/v0/item/$it.json?print=pretty"
                val result = retroInterface.itemDetails(url).await()
                val item = ItemModel.fromDefault(result)
                val entity = NewEntity
                entityList.add(NewEntity.from(item))

                itemList.add(item)
            }
            /**
             * itemList to itemEntity saveAll dao
             *
             */

            async { itemDao.saveAllNews(entityList) }

            //Timber.d(itemList.toString())
            return@async itemList
        }
    }

    @Synchronized
    suspend fun getJobs(idList: MutableList<Int>,isReload: Boolean) : Deferred<MutableList<ItemModel>> = GlobalScope.async {

        /**
         * if jobs saved from room getAllNews else fetch network
         */

        if(!itemDao.getAllJobs().isNullOrEmpty() && !isReload){
            val jobs = mutableListOf<ItemModel>()
            itemDao.getAllJobs().forEach {
                val item = ItemModel.fromJobs(it)
                jobs.add(item)
            }
            return@async jobs
        }else {
            /**
             * idList if equal null fetch data in jobStories api, not equal idList
             * @return MutableList<Int>*/

            val list = if (idList.isEmpty()) {
                retroInterface.jobStories().await()
            } else {
                idList
            }

            val itemList: MutableList<ItemModel> = mutableListOf()
            val entityList: MutableList<JobEntity> = mutableListOf()

            list.take(30).map {
                /**
                 * fetching all data then this get details this ids
                 * @return MutableList<Item>*/
                val url = "https://hacker-news.firebaseio.com/v0/item/$it.json?print=pretty"
                val result = retroInterface.itemDetails(url).await()
                val item = ItemModel.fromDefault(result)
                val entity = JobEntity
                entityList.add(JobEntity.from(item))

                itemList.add(item)
            }
            /**
             * itemList to itemEntity saveAll dao
             *
             */

            async { itemDao.saveAllJobs(entityList) }

            Timber.d(itemList.toString())
            return@async itemList
        }
    }

    @Synchronized
    suspend fun getTops(idList: MutableList<Int>,isReload: Boolean) : Deferred<MutableList<ItemModel>> = GlobalScope.async {

        /**
         * idList if equal null fetch data in topStories api, not equal idList
         * @return MutableList<Int>*/

        val list = if(idList.isEmpty()) {
            retroInterface.topStories().await()
        }else{
            idList
        }

        val itemList:MutableList<ItemModel> = mutableListOf()
        val entityList:MutableList<NewEntity> = mutableListOf()

        list.take(30).map {
            /**
             * fetching all data then this get details this ids
             * @return MutableList<Item>*/
            val url = "https://hacker-news.firebaseio.com/v0/item/$it.json?print=pretty"
            val result = retroInterface.itemDetails(url).await()
            val item = ItemModel.fromDefault(result)
            val entity = NewEntity
            entityList.add(NewEntity.from(item))

            itemList.add(item)
        }
        /**
         * itemList to itemEntity saveAll dao
         *
         */

        async { itemDao.saveAllNews(entityList) }

        Timber.d(itemList.toString())
        return@async itemList
    }

    @Synchronized
    suspend fun getAsks(idList: MutableList<Int>, isReload: Boolean) : Deferred<MutableList<ItemModel>> = GlobalScope.async {

        /**
         * if jobs saved from room getAllNews else fetch network
         */

        if(!itemDao.getAllAsks().isNullOrEmpty() && !isReload){
            val asks = mutableListOf<ItemModel>()
            itemDao.getAllAsks().forEach {
                val item = ItemModel.fromAsk(it)
                asks.add(item)
            }
            return@async asks
        }else {

            /**
             * idList if equal null fetch data in askStories api, not equal idList
             * @return MutableList<Int>*/

            val list = if (idList.isEmpty()) {
                retroInterface.askStories().await()
            } else {
                idList
            }

            val itemList: MutableList<ItemModel> = mutableListOf()
            val entityList: MutableList<AskEntity> = mutableListOf()

            list.take(30).map {
                /**
                 * fetching all data then this get details this ids
                 * @return MutableList<Item>*/
                val url = "https://hacker-news.firebaseio.com/v0/item/$it.json?print=pretty"
                val result = retroInterface.itemDetails(url).await()
                val item = ItemModel.fromDefault(result)
                val entity = AskEntity
                entityList.add(AskEntity.from(item))

                itemList.add(item)
            }
            /**
             * itemList to itemEntity saveAll dao
             *
             */

            async { itemDao.saveAllAsk(entityList) }

            Timber.d(itemList.toString())
            return@async itemList
        }
    }


    /**
     * @param id
     * return ItemModel for news.
     */
    override fun getItemByNewId(id: String): Deferred<ItemModel>? = GlobalScope.async {
        ItemModel.fromNews(itemDao.findAllByNewId(id))
    }

    /**
     * @param id
     * return ItemModel for jobs.
     */
    override fun getItemByJobId(id: String): Deferred<ItemModel>? = GlobalScope.async {
        ItemModel.fromJobs(itemDao.findAllByJobId(id))
    }

    /**
     * @param id
     * return ItemModel for ask.
     */
    override fun getItemByAskId(id: String): Deferred<ItemModel>? = GlobalScope.async {
        ItemModel.fromAsk(itemDao.findAllByAskId(id))
    }


}