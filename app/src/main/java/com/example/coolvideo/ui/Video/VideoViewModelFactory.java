package com.example.coolvideo.ui.video;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.SimpleExoPlayer;

import master.flame.danmaku.ui.widget.DanmakuView;

public class VideoViewModelFactory implements ViewModelProvider.Factory {

    private SimpleExoPlayer player;
    private Context context;
    private VideoManager videoManager;
    private DanmakuView danmakuView;

    public VideoViewModelFactory(SimpleExoPlayer player,DanmakuView danmakuView,Context context,VideoManager videoManager) {
        this.player = player;
        this.context=context;
        this.videoManager=videoManager;
        this.danmakuView=danmakuView;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(VideoViewModel.class)) {
            return (T) new VideoViewModel(player,danmakuView,context,videoManager);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}