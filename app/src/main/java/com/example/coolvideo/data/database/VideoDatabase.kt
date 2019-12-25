package com.example.coolvideo.data.database

import com.example.coolvideo.data.DAO.CommentDao
import com.example.coolvideo.data.DAO.FavorDao
import com.example.coolvideo.data.DAO.HistoryDao
import com.example.coolvideo.data.DAO.VideoDao

object CoolVideoDatabase{
    private var videoDao : VideoDao?=null
    private var historyDao : HistoryDao?=null
    private var favorDao : FavorDao?=null
    private var commentDao : CommentDao?=null

    fun getVideoDao(): VideoDao {
        if (videoDao == null) {
            videoDao = VideoDao()
        }
        return videoDao!!
    }

    fun getHistoryDao(): HistoryDao {
        if (historyDao == null) {
            historyDao = HistoryDao()
        }
        return historyDao!!
    }

    fun getFavorDao(): FavorDao {
        if (favorDao == null) {
            favorDao = FavorDao()
        }
        return favorDao!!
    }

    fun getCommentDao(): CommentDao {
        if (commentDao == null) {
            commentDao = CommentDao()
        }
        return commentDao!!
    }
}