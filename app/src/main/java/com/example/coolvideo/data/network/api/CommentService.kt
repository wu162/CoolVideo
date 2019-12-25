package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.CommentList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.sql.Timestamp

interface CommentService {
    @GET("/getComments")
    fun getComment(@Query("videoId") videoId : String): Call<CommentList>

    @POST("/addComment")
    fun addComment(@Query("userId") userId : String,
                @Query("videoId") videoId : String,
                @Query("userName") userName : String,
                @Query("commentTime") commentTime : Timestamp,
                @Query("commentContent") commentContent : String) : Call<String>
}