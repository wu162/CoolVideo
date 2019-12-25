package com.example.coolvideo.data.model

import com.google.gson.annotations.SerializedName

data class Danmu (

    @SerializedName("id")
    var danmuId: Int,

    @SerializedName("userId")
    var userId: String,

    @SerializedName("videoId")
    var videoId: String,

    @SerializedName("time")
    var time: Long,

    @SerializedName("content")
    var content:String
)