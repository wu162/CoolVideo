package com.example.coolvideo.data.Repository

import android.util.Log
import com.example.coolvideo.data.DAO.HistoryDao
import com.example.coolvideo.data.DAO.VideoDao
import com.example.coolvideo.data.network.CoolVideoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Timestamp

class HistoryRepository private constructor(private val historyDao: HistoryDao, private val network: CoolVideoNetwork) {
    suspend fun getHistorys(id:String) = withContext(Dispatchers.IO) {
        var list = historyDao.getHistoryList()
        if(list.isEmpty()){
            list.addAll(network.fetchHistory(id).historys)
            historyDao.saveHistoryList(list)
        }
        list
    }

    suspend fun addHistory(userId : String,videoId : String,videoName : String,
                           videoImgUrl : String, videoUrl : String,userLastSeen : Timestamp
    ){
        network.addHistory(userId,videoId,videoName, videoUrl,videoImgUrl,userLastSeen)
    }

    suspend fun deleteAllHistory(){
        historyDao.deteleAllHistory()
    }

    companion object {

        private var instance: HistoryRepository? = null

        fun getInstance(historyDao: HistoryDao, network: CoolVideoNetwork): HistoryRepository {
            if (instance == null) {
                synchronized(HistoryRepository::class.java) {
                    if (instance == null) {
                        instance = HistoryRepository(historyDao,network)
                    }
                }
            }
            return instance!!
        }

    }
}