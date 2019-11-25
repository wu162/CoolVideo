package com.example.coolvideo.data.database

import com.example.coolvideo.data.DAO.VideoDao

object CoolVideoDatabase{
    private var videoDao : VideoDao?=null

    fun getVideoDao(): VideoDao {
        if (videoDao == null) {
            videoDao = VideoDao()
        }
        return videoDao!!
    }
}