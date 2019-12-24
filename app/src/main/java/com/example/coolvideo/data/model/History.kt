package com.example.coolvideo.data.model

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport
import java.sql.Timestamp

class History(
    @SerializedName("userId")
    var userId: String,

    @SerializedName("videoId")
    var videoId: String,

    @SerializedName("videoName")
    var videoName: String,

    @SerializedName("videoUrl")
    var videoUrl:String,

    @SerializedName("videoImgUrl")
    var videoImgUrl:String,

    @SerializedName("userLastSeen")
    var userLastSeen: Timestamp

) : LitePalSupport(){
}