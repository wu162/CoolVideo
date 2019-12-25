package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.FavorList
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FavorService {
    @GET("getFavors")
    fun getFavor(@Query("userId") userId : String):Call<FavorList>

    @GET("getFavor")
    fun getOneFavor(@Query("userId") userId : String,
                    @Query("videoId") videoId : String):Call<String>

    @POST("addFavor")
    fun addFavor(@Query("userId") userId : String,
                 @Query("videoId") videoId : String,
                 @Query("videoName") videoName : String,
                 @Query("videoUrl") videoUrl : String,
                 @Query("videoImgUrl") videoImgUrl : String):Call<String>

    @DELETE("deleteFavor")
    fun deleteFavor(@Query("userId") userId : String,
                    @Query("videoId") videoId : String):Call<String>
}