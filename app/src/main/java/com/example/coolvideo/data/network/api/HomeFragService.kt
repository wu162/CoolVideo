package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.HomeFragVideos
import retrofit2.Call
import retrofit2.http.GET

interface HomeFragService {
    @GET("getVideos")
    fun getHomeFragVideos():Call<HomeFragVideos>
}