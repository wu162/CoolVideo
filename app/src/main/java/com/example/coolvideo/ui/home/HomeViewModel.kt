package com.example.coolvideo.ui.home

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.repository.HomeVideosRepository
import com.example.coolvideo.data.model.Video
import com.example.coolvideo.ui.video.VideoActivity
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class HomeViewModel(private val context : Context, private val repository: HomeVideosRepository) : ViewModel() ,VideoItemListener {
    var dataChanged = MutableLiveData<Int>()

    var videos=ArrayList<Video>()

    init {
        launch{
            repository.deleteAllVideo()
        }
    }

    fun getVideos(){
        launch{
            val video=repository.getVideos()
            videos.addAll(video)
        }
    }

    override fun onItemClick(video: Video) {
        startVideoActivity(video)
    }

    private fun startVideoActivity(video: Video) {
        var intent= Intent(context, VideoActivity::class.java)
        intent.putExtra("videoUrl", baseUrl+ videoPath+video.videoUrl)
        intent.putExtra("videoName", video.videoName)
        intent.putExtra("videoId", video.videoId)
        intent.putExtra("videoUrl", video.videoUrl)
        intent.putExtra("videoImgUrl", video.videoImgUrl)
        context.startActivity(intent)
    }

    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
            dataChanged.value = dataChanged.value?.plus(1)
        } catch (t: Throwable) {
            t.printStackTrace()
            dataChanged.value = dataChanged.value?.plus(1)
        }
    }

    companion object{
        private const val baseUrl="http://47.100.37.242:8080"
        private const val videoPath="/videos/"
    }
}