package com.example.coolvideo.data.model

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport

class History(
    @SerializedName("userId")
    var userId: String,

    @SerializedName("videoName")
    var videoName: String,

    @SerializedName("videoImgUrl")
    var videoImgUrl:String,

    @SerializedName("userLastSeen")
    var userLastSeen:String

) : LitePalSupport(){
}