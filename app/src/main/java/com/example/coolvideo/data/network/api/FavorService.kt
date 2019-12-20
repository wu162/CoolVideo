package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.FavorList
import retrofit2.Call
import retrofit2.http.GET

interface FavorService {
    @GET("getFavors")
    fun getFavor():Call<FavorList>
}