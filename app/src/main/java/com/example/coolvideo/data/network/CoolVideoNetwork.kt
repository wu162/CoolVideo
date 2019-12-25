package com.example.coolvideo.data.network

import android.util.Log
import com.example.coolvideo.data.network.api.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.sql.Timestamp
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CoolVideoNetwork {
    private val homeFragService=ServiceCreator.create(HomeFragService::class.java)
    private val historyService=ServiceCreator.create(HistoryService::class.java)
    private val favorService=ServiceCreator.create(FavorService::class.java)
    private val avatarService=ServiceCreator.create(AvatarService::class.java)
    private val userService=ServiceCreator.create(UserService::class.java)
    private val commentService=ServiceCreator.create(CommentService::class.java)
    private val danmuService=ServiceCreator.create(DanmuService::class.java)

    suspend fun fetchHomeFragVideos()=homeFragService.getHomeFragVideos().await()
    suspend fun fetchHistory(id:String)=historyService.getHistory(id).await()
    suspend fun fetchFavor(id:String)=favorService.getFavor(id).await()
    suspend fun getOneFavor(userId:String,videoId:String)=favorService.getOneFavor(userId,videoId).await()
    suspend fun addFavor(userId : String, videoId : String,
                         videoName : String, videoUrl:String, videoImgUrl : String){
        favorService.addFavor(userId,videoId,videoName,videoUrl,videoImgUrl).await()
    }
    suspend fun deleteFavor(userId:String,videoId:String){
        favorService.deleteFavor(userId,videoId).await()
    }
    suspend fun uploadAvatar(imgPath:String){
        var file= File(imgPath)
        var requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        var fileToUpload = MultipartBody.Part.createFormData("img", file.name, requestBody)
        avatarService.updateUserPhoto(fileToUpload).await()
    }
    suspend fun addUser(userName : String,password : String): String {
        return userService.addUser(userName,password).await()
    }
    suspend fun updateName(id:Int, userName:String){
        userService.updateName(id,userName).await()
    }
    suspend fun addHistory(userId : String,videoId : String,videoName : String,
                           videoImgUrl : String, videoUrl : String,userLastSeen : Timestamp
    ){
        historyService.updateHistory(userId,videoId,videoName, videoUrl,videoImgUrl,userLastSeen).await()
    }

    suspend fun getComment(videoId : String)=commentService.getComment(videoId).await()

    suspend fun addComment(userId : String, videoId : String, userName : String,
                           commentTime : Timestamp, commentContent : String): String {
        return commentService.addComment(userId,videoId,userName,commentTime,commentContent).await()
    }

    suspend fun fetchDanmu(videoId:String)=danmuService.getDanmu(videoId).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    companion object{
        private var network: CoolVideoNetwork?=null
        fun getInstance(): CoolVideoNetwork{
            if(network==null){
                synchronized(CoolVideoNetwork::class.java) {
                    if (network == null) {
                        network = CoolVideoNetwork()
                    }
                }
            }
            return network!!
        }
    }
}