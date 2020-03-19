package com.example.coolvideo.ui.comment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.repository.CommentRepository
import com.example.coolvideo.data.model.Comment
import com.example.coolvideo.utils.DateUtils
import kotlinx.coroutines.launch

class CommentViewModel (private val context : Context, private val repository: CommentRepository) : ViewModel() {
    var dataChanged = MutableLiveData<Int>()
    var videoId=""
    var videoName=""
    var videoUrl=""
    var videoImgUrl=""
    var commentSubmit=MutableLiveData<Int>()

    var comments=ArrayList<Comment>()
    var favor=MutableLiveData<Boolean>(false)

    fun submitComment(s: String) {
        if (s=="")
            return
        launch {
            var pref=context.getSharedPreferences("userInfo",MODE_PRIVATE)
            val userId=pref.getString("id","").toString()
            val userName=pref.getString("name","").toString()
            val datetime= DateUtils.nowDateTime
            var commentId=repository.addCommentToServer(userId,videoId,userName,datetime,s)
            val comment=Comment(commentId,videoId,userId,userName,datetime,s)
            comments.add(comment)
            dataChanged.value = dataChanged.value?.plus(1)
        }

    }

    fun changeFavor(){
        if (favor.value!!){
            favor.value=false
            launch {
                var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
                val userId=pref.getString("id","").toString()
                repository.deleteFavorOnServer(userId,videoId)
                repository.deleteFavorLocal(userId,videoId)
            }
        }else{
            favor.value=true
            launch {
                var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
                val userId=pref.getString("id","").toString()
                repository.addFavorToServer(userId,videoId,videoName, videoUrl,videoImgUrl)
                repository.addFavorLocal(userId,videoId,videoName, videoUrl,videoImgUrl)
            }
        }
    }

    fun getComment(){
        launch{
            val comment=repository.getComments(videoId)
            comments.addAll(comment)
            dataChanged.value = dataChanged.value?.plus(1)
        }
    }

    fun initFavor() {
        launch {
            var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
            val userId=pref.getString("id","").toString()
            val result=repository.getOneFavor(userId,videoId)
            favor.value = result=="success"
        }
    }

    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}
