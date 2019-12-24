package com.example.coolvideo.data.network.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/addUser")
    fun addUser(@Query("userName") userName : String, @Query("passWord") password : String) : Call<String>

    @POST("/updateUserName")
    fun updateName(@Query("id") id : Int, @Query("userName") userName : String) : Call<String>
}