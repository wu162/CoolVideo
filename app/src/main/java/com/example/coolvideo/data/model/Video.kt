package com.example.coolvideo.data.model

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

class Video(
    @SerializedName("video_name")
    var videoName: String,

    @SerializedName("video_imgUrl")
    var videoImgUrl:Int
) : LitePalSupport(){
    @Transient val id:Long = 0
}