package com.example.coolvideo.data.Repository

import com.example.coolvideo.data.DAO.FavorDao
import com.example.coolvideo.data.network.CoolVideoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavorRepository private constructor(private val favorDao: FavorDao, private val network: CoolVideoNetwork) {
    suspend fun getFavors() = withContext(Dispatchers.IO) {
        var list = favorDao.getFavorList()
        if(list.isEmpty()){
            list.addAll(network.fetchFavor().favors)
            favorDao.saveFavorList(list)
        }
        list
    }

    suspend fun deleteAllFavor(){
        favorDao.deteleAllFavor()
    }

    companion object {

        private var instance: FavorRepository? = null

        fun getInstance(favorDao: FavorDao, network: CoolVideoNetwork): FavorRepository {
            if (instance == null) {
                synchronized(FavorRepository::class.java) {
                    if (instance == null) {
                        instance = FavorRepository(favorDao,network)
                    }
                }
            }
            return instance!!
        }

    }
}