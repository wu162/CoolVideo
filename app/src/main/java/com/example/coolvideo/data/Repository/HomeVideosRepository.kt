package com.example.coolvideo.data.Repository

import android.util.Log
import com.example.coolvideo.data.DAO.VideoDao
import com.example.coolvideo.data.network.CoolVideoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.util.*

class HomeVideosRepository private constructor(private val videoDao: VideoDao, private val network: CoolVideoNetwork){
    suspend fun getVideos() = withContext(Dispatchers.IO) {
        var list = videoDao.getVideoList()
        if(list.isEmpty()){
            list.addAll(network.fetchHomeFragVideos().videos)
            videoDao.saveVideoList(list)
        }
        list
    }

    suspend fun addHistory(userId : String,videoId : String,videoName : String,
                           videoImgUrl : String, videoUrl : String,userLastSeen : Timestamp
    ){
        network.addHistory(userId,videoId,videoName, videoUrl,videoImgUrl,userLastSeen)
    }

    suspend fun deleteAllVideo(){
        videoDao.deteleAlVideo()
    }

    companion object {

        private var instance: HomeVideosRepository? = null

        fun getInstance(videoDao: VideoDao, network: CoolVideoNetwork): HomeVideosRepository {
            if (instance == null) {
                synchronized(HomeVideosRepository::class.java) {
                    if (instance == null) {
                        instance = HomeVideosRepository(videoDao,network)
                    }
                }
            }
            return instance!!
        }

    }
}