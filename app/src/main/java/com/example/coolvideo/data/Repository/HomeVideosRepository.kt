package com.example.coolvideo.data.Repository

import com.example.coolvideo.data.DAO.VideoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeVideosRepository private constructor(private val videoDao: VideoDao){
    suspend fun getVideos() = withContext(Dispatchers.IO) {
        var list = videoDao.getVideoList()

        list
    }

    companion object {

        private var instance: HomeVideosRepository? = null

        fun getInstance(placeDao: VideoDao): HomeVideosRepository {
            if (instance == null) {
                synchronized(HomeVideosRepository::class.java) {
                    if (instance == null) {
                        instance = HomeVideosRepository(placeDao)
                    }
                }
            }
            return instance!!
        }

    }
}