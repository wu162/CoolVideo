package com.example.coolvideo.ui.Video;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.coolvideo.R;
import com.example.coolvideo.utils.PlayerStateMessage;
import com.example.coolvideo.utils.listener.OnScreenTouchListener;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Observable;

public class VideoManager extends Observable implements LifecycleObserver {

    private Context context;
    private String url;

    private PlayerView videoView;
    private SimpleExoPlayer player;

    private boolean isPlayerPlaying;

    private PlayerStateMessage pLayerStateMessage;

    public boolean isPlayerPlaying() {
        return isPlayerPlaying;
    }

    public VideoManager(Context context, String url){
        this.context=context;
        ((AppCompatActivity)context).getLifecycle().addObserver(this);
        this.url=url;
        this.isPlayerPlaying=true;
        this.videoView=((Activity)context).findViewById(R.id.exoplayer);
        this.pLayerStateMessage=new PlayerStateMessage(PlayerStateMessage.Resume);
        prepareExoPlayer();
        videoView.setOnTouchListener(new OnScreenTouchListener(context, this));
    }

    private void prepareExoPlayer() {
        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(
                            context,
                            Util.getUserAgent(
                                    context,
                                    context.getString(R.string.app_name)
                            )
                    );
            MediaSource videoSource = new ExtractorMediaSource
                    .Factory(dataSourceFactory)
                    .setExtractorsFactory(extractorsFactory)
                    .createMediaSource(Uri.parse(url));
            player.prepare(videoSource);
        }
        player.seekTo(player.getCurrentPosition() + 1);
        videoView.setPlayer(player);
    }

    private void releaseVideoPlayer() {
        if (player != null) {
            player.release();
        }
        player = null;
    }

    private void goToBackground() {
        if (player != null) {
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    private void goToForeground() {
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }

    public void playImmediately(){
        player.setPlayWhenReady(true);
        isPlayerPlaying=true;
    }

    public void stopImmediately(){
        player.setPlayWhenReady(false);
        isPlayerPlaying=false;
    }

    public void adjustPlayerTime(long offset){
        long nextPos=player.getCurrentPosition()+offset;
        if(nextPos>player.getDuration()){
            player.seekTo(player.getDuration());
        }else if (nextPos<0){
            player.seekTo(0);
        }else{
            player.seekTo(nextPos);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onVideoResume(){
        prepareExoPlayer();
        goToForeground();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onVideoPause(){
        goToBackground();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onVideoDestroy(){
        releaseVideoPlayer();
    }

    public VideoManager getPlayerManager() {
        return this;
    }

    public SimpleExoPlayer getPlayer(){
        return player;
    }
}
