package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.VideoList
import retrofit2.Call
import retrofit2.http.GET

interface HomeFragService {
    @GET("getVideos")
    fun getHomeFragVideos():Call<VideoList>
}