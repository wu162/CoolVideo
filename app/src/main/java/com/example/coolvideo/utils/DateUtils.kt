package com.example.coolvideo.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    val nowDateTime: Timestamp
        get(){
            return Timestamp(Date().time)
        }

    fun formatHistoryTime(date:Date):String{
        val sdf=SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(date)
    }

    fun formatCommentTime(date:Date):String{
        val sdf=SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(date)
    }
}