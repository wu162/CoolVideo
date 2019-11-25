package com.example.coolvideo.data.model

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

class Video(
    @SerializedName("video_name")
    val videoName: String,

    @SerializedName("video_imgUrl")
    val videoImgUrl:Int
) : LitePalSupport(){
    @Transient val id = 0
    var videoId = 0
}