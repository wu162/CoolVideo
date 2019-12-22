package com.example.coolvideo.ui.Video;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.coolvideo.R;
import com.example.coolvideo.utils.MyObservable;
import com.example.coolvideo.utils.MyObserver;
import com.example.coolvideo.utils.PlayerStateMessage;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.TimeBar;

import java.util.ArrayList;

import master.flame.danmaku.ui.widget.DanmakuView;

public class VideoViewModel extends ViewModel implements MyObservable {
    private ArrayList<MyObserver> observers;
    private Context context;
    private SimpleExoPlayer player;
    private DanmakuView danmakuView;
    private VideoManager videoManager;
    private PlayerStateMessage pLayerStateMessage;
    public VideoViewModel(SimpleExoPlayer player, DanmakuView danmakuView, Context context,VideoManager videoManager){
        observers=new ArrayList<>();
        this.context=context;
        this.player=player;
        this.danmakuView=danmakuView;
        this.videoManager=videoManager;
        this.pLayerStateMessage=new PlayerStateMessage(PlayerStateMessage.Resume);
        setupPlayerListener();
        setupTimerBar();
        initObservers(danmakuView);
    }

    private void initObservers(DanmakuView danmakuView) {
        CollapsingToolbarManager collapsingToolbarManager=new CollapsingToolbarManager(context, videoManager.getPlayerManager());
        addObserver(collapsingToolbarManager);
        DanmuObserver danmuObserver=new DanmuObserver(danmakuView);
        addObserver(danmuObserver);
    }

    private void setupTimerBar() {
        DefaultTimeBar timerBar=((Activity)context).findViewById(R.id.control_view).findViewById(R.id.exo_progress);
        timerBar.addListener(new TimeBar.OnScrubListener() {
            @Override
            public void onScrubStart(TimeBar timeBar, long position) {}
            @Override
            public void onScrubMove(TimeBar timeBar, long position) { }
            @Override
            public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
                customNotify(PlayerStateMessage.SeekBarChanged,position);
            }
        });
    }

    private void setupPlayerListener() {
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playWhenReady && playbackState == Player.STATE_READY){
                    customNotify(PlayerStateMessage.Resume);
                } else if (playWhenReady) {
                    customNotify(PlayerStateMessage.Buffering);
                } else {
                    customNotify(PlayerStateMessage.Pause);
                }
            }
        });
    }

    private void customNotify(PlayerStateMessage.PlayerState playerState){
        pLayerStateMessage.setPlayerState(playerState);
        notifyObservers(pLayerStateMessage);
    }

    private void customNotify(PlayerStateMessage.PlayerState playerState,long seekBarposition){
        pLayerStateMessage.setPlayerState(playerState);
        pLayerStateMessage.setSeekBarPosition(seekBarposition);
        notifyObservers(pLayerStateMessage);
    }

    @Override
    public void addObserver(MyObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Object o) {
        for(MyObserver observer: observers){
            observer.update(o);
        }
    }
}
