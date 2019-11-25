package com.example.coolvideo.data.DAO

import com.example.coolvideo.R
import com.example.coolvideo.data.model.Video
import org.litepal.LitePal


class VideoDao {
    fun getVideoList():MutableList<Video>{
        LitePal.deleteAll(Video::class.java)
        var video: Video=Video("apple", R.drawable.apple)
        video.save()
        video=Video("banana", R.drawable.banana)
        video.save()
        video=Video("cherry", R.drawable.cherry)
        video.save()
        video=Video("grape", R.drawable.grape)
        video.save()
        video=Video("apple", R.drawable.apple)
        video.save()
        video=Video("banana", R.drawable.banana)
        video.save()
        video=Video("cherry", R.drawable.cherry)
        video.save()
        video=Video("grape", R.drawable.grape)
        video.save()
        return LitePal.findAll(Video::class.java)
    }

    fun saveVideoList(videoList: List<Video>?) {
        if (videoList != null && videoList.isNotEmpty()) {
            LitePal.saveAll(videoList)
        }
    }
}