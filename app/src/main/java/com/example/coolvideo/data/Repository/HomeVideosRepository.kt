package com.example.coolvideo.data.Repository

import com.example.coolvideo.data.DAO.VideoDao
import com.example.coolvideo.data.model.HomeFragVideos
import com.example.coolvideo.data.network.CoolVideoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeVideosRepository private constructor(private val videoDao: VideoDao, private val network: CoolVideoNetwork){
    suspend fun getVideos() = withContext(Dispatchers.IO) {
        var list = videoDao.getVideoList()
        if(list.isEmpty()){
            list.addAll(network.fetchHomeFragVideos().videos)
            videoDao.saveVideoList(list)
        }
        list
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