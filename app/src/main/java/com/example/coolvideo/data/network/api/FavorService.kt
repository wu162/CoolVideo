package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.FavorList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FavorService {
    @GET("getFavors")
    fun getFavor(@Query("userId") userId : String):Call<FavorList>
}