package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.DanmuList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DanmuService {
    @GET("/getVideoDanmu")
    fun getDanmu(@Query("videoId") videoId : String): Call<DanmuList>

    @POST("/addDanmu")
    fun addDanmu(@Query("userId") userId : String,
                 @Query("videoId") videoId : String,
                 @Query("time") time : Long,
                 @Query("content") content : String): Call<String>
}