package com.fevziomurtekin.com.data.room

import com.fevziomurtekin.com.data.model.UserModel
import com.fevziomurtekin.com.data.network.RetroInterface
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

interface UserRepository{

    /**
     * Get Users by id
     * @return Users */

    fun getUser(id:String): Deferred<UserModel>

}

class UserRepositoryImpl(
    private val retroInterface: RetroInterface,
    private val userDao: UserDao
) : UserRepository{

    override fun getUser(id: String): Deferred<UserModel> = GlobalScope.async {

        /**
         * findUser add data.
         * */

        userDao.saveUser(UserEntity.from(
            UserModel.from(userDao.findUserById(id))
        ))

        UserModel.from(userDao.findUserById(id))
    }

}