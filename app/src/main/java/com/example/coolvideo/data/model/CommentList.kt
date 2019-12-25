package com.example.coolvideo.data.model

import com.google.gson.annotations.SerializedName

data class CommentList(
    @SerializedName("comments")
    var comments: List<Comment>
)