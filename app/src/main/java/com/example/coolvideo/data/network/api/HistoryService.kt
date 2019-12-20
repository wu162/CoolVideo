package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.HistoryList
import retrofit2.Call
import retrofit2.http.GET

interface HistoryService {
    @GET("getHistorys")
    fun getHistory():Call<HistoryList>
}