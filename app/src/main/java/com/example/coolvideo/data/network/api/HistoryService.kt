package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.HistoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.sql.Timestamp

interface HistoryService {
    @GET("/getHistorys")
    fun getHistory(@Query("userId") userId : String):Call<HistoryList>

    @POST("/updateUserHistory")
    fun updateHistory(@Query("userId") userId : String,
                   @Query("videoId") videoId : String,
                   @Query("videoName") videoName : String,
                   @Query("videoUrl") videoUrl : String,
                   @Query("videoImgUrl") videoImgUrl : String,
                   @Query("userLastSeen") userLastSeen : Timestamp
    ) : Call<String>
}