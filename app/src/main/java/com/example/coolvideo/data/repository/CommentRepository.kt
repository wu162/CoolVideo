package com.example.coolvideo.data.repository

import com.example.coolvideo.data.model.Favor
import com.example.coolvideo.data.network.CoolVideoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litepal.LitePal
import java.sql.Timestamp

class CommentRepository private constructor(private val network: CoolVideoNetwork) {
    suspend fun getComments(videoId:String) = withContext(Dispatchers.IO) {
        var list=network.getComment(videoId).comments
        list
    }

    fun addFavorLocal(userId : String, videoId : String,
                              videoName : String, videoUrl:String, videoImgUrl : String){
        val favor= Favor(userId,videoId,videoName, videoUrl,videoImgUrl)
        favor.save()
    }

    fun deleteFavorLocal(userId : String, videoId : String){
        LitePal.deleteAll(Favor::class.java,"userId = ? and videoId = ?" , userId,videoId)
    }

    suspend fun addFavorToServer(userId : String, videoId : String,
                         videoName : String, videoUrl:String, videoImgUrl : String){
        network.addFavor(userId,videoId,videoName, videoUrl,videoImgUrl)
    }

    suspend fun getOneFavor(userId: String, videoId: String):String{
        return network.getOneFavor(userId, videoId)
    }

    suspend fun deleteFavorOnServer(userId:String, videoId:String){
        network.deleteFavor(userId,videoId)
    }

    suspend fun addCommentToServer(userId : String, videoId : String, userName : String,
                           commentTime : Timestamp, commentContent : String):String{
        return network.addComment(userId,videoId,userName,commentTime,commentContent)
    }

    companion object {

        private var instance: CommentRepository? = null

        fun getInstance(network: CoolVideoNetwork): CommentRepository {
            if (instance == null) {
                synchronized(CommentRepository::class.java) {
                    if (instance == null) {
                        instance = CommentRepository(network)
                    }
                }
            }
            return instance!!
        }

    }
}