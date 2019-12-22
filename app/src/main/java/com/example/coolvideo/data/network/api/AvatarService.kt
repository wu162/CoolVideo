package com.example.coolvideo.data.network.api

import com.example.coolvideo.data.model.UploadAvatarResponce
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AvatarService {
    @Multipart
    @POST("/uploadAvatar")
    fun updateUserPhoto(@Part img : MultipartBody.Part) : Call<String>
}