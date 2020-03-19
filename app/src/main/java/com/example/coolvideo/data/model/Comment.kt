package com.example.coolvideo.data.model

import com.google.gson.annotations.SerializedName
import org.litepal.crud.LitePalSupport
import java.sql.Timestamp

data class Comment(

    @SerializedName("id")
    var commentId: String,

    @SerializedName("videoId")
    var videoId: String,

    @SerializedName("userId")
    var userId: String,

    @SerializedName("userName")
    var userName: String,

    @SerializedName("commentTime")
    var commentTime: Timestamp,

    @SerializedName("commentContent")
    var commentContent:String

)