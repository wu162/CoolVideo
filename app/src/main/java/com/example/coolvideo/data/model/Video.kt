package com.example.coolvideo.data.model

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

class Video(
    @SerializedName("id")
    var videoId: String,
    @SerializedName("videoName")
    var videoName: String,

    @SerializedName("videoUrl")
    var videoUrl: String,

    @SerializedName("videoImgUrl")
    var videoImgUrl:String

) : LitePalSupport(){
}