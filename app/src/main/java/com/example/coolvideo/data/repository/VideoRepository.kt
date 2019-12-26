package com.example.coolvideo.data.repository

import com.example.coolvideo.data.DAO.HistoryDao
import com.example.coolvideo.data.model.History
import com.example.coolvideo.data.network.CoolVideoNetwork
import java.sql.Timestamp

class VideoRepository private constructor(private val historyDao: HistoryDao, private val network: CoolVideoNetwork) {
    suspend fun addHistoryToServer(userId : String,videoId : String,videoName : String,
                           videoImgUrl : String, videoUrl : String,userLastSeen : Timestamp
    ){
        network.addHistory(userId,videoId,videoName, videoUrl,videoImgUrl,userLastSeen)
    }

    fun addHistoryLocal(userId : String,videoId : String,videoName : String,
                                videoImgUrl : String, videoUrl : String,userLastSeen : Timestamp){
        val history=History(userId,videoId,videoName, videoUrl,videoImgUrl,userLastSeen)
        var result=historyDao.findHistory(history)
        if (result==null || result.size==0){
            historyDao.insertHistory(history)
        }else{
            historyDao.updateHistoryTime(history)
        }
    }

    companion object {

        private var instance: VideoRepository? = null

        fun getInstance(historyDao: HistoryDao, network: CoolVideoNetwork): VideoRepository {
            if (instance == null) {
                synchronized(HistoryRepository::class.java) {
                    if (instance == null) {
                        instance = VideoRepository(historyDao,network)
                    }
                }
            }
            return instance!!
        }

    }
}