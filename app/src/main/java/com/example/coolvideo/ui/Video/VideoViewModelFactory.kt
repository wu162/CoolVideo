package com.example.coolvideo.ui.video

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coolvideo.data.repository.VideoRepository
import com.google.android.exoplayer2.SimpleExoPlayer
import master.flame.danmaku.ui.widget.DanmakuView

class VideoViewModelFactory(
    private val player: SimpleExoPlayer,
    private val danmakuView: DanmakuView,
    private val context: Context,
    private val videoManager: VideoManager,
    private val danmuManager: DanmuManager,
    private val repository: VideoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            return VideoViewModel(player, danmakuView, context, videoManager,danmuManager,repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}