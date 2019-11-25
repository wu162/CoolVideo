package com.example.coolvideo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.Repository.HomeVideosRepository
import com.example.coolvideo.data.model.Video
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: HomeVideosRepository) : ViewModel() {
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

    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
            dataChanged.value = dataChanged.value?.plus(1)
        } catch (t: Throwable) {
            t.printStackTrace()
            dataChanged.value = dataChanged.value?.plus(1)
        }
    }
}