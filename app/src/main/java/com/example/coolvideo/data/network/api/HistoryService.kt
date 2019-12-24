package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.HistoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryService {
    @GET("/getHistorys")
    fun getHistory(@Query("userId") userId : String):Call<HistoryList>
}