package com.example.coolvideo.ui.video

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.R
import com.example.coolvideo.data.network.CoolVideoNetwork
import com.example.coolvideo.data.repository.VideoRepository
import com.example.coolvideo.utils.DateUtils
import com.example.coolvideo.utils.MyObservable
import com.example.coolvideo.utils.MyObserver
import com.example.coolvideo.utils.PlayerStateMessage
import com.example.coolvideo.utils.PlayerStateMessage.PlayerState
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.TimeBar
import com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
import kotlinx.coroutines.launch
import master.flame.danmaku.ui.widget.DanmakuView
import java.util.*

class VideoViewModel(
    player: SimpleExoPlayer,
    danmakuView: DanmakuView,
    context: Context,
    videoManager: VideoManager,
    danmuManager: DanmuManager,
    repository: VideoRepository
) : ViewModel(), MyObservable {
    private val observers: ArrayList<MyObserver>
    private val context: Context
    private val player: SimpleExoPlayer
    private val danmakuView: DanmakuView
    private val videoManager: VideoManager
    private val pLayerStateMessage: PlayerStateMessage
    private lateinit var userId: String
    private lateinit var videoId: String
    private fun initObservers(danmakuView: DanmakuView) {
        val collapsingToolbarManager =
            CollapsingToolbarManager(context, videoManager.playerManager)
        addObserver(collapsingToolbarManager)
        val danmuObserver = DanmuObserver(danmakuView)
        addObserver(danmuObserver)
    }

    private fun setupTimerBar() {
        val timerBar: DefaultTimeBar =
            (context as Activity).findViewById<View>(R.id.control_view)
                .findViewById(R.id.exo_progress)
        timerBar.addListener(object : OnScrubListener {
            override fun onScrubStart(
                timeBar: TimeBar,
                position: Long
            ) {
            }

            override fun onScrubMove(timeBar: TimeBar, position: Long) {}
            override fun onScrubStop(
                timeBar: TimeBar,
                position: Long,
                canceled: Boolean
            ) {
                customNotify(PlayerStateMessage.SeekBarChanged, position)
            }
        })
    }

    private fun setupPlayerListener() {
        player.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(
                playWhenReady: Boolean,
                playbackState: Int
            ) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    customNotify(PlayerStateMessage.Resume)
                } else if (playWhenReady) {
                    customNotify(PlayerStateMessage.Buffering)
                } else {
                    customNotify(PlayerStateMessage.Pause)
                }
            }
        })
    }

    private fun customNotify(playerState: PlayerState) {
        pLayerStateMessage.setPlayerState(playerState)
        notifyObservers(pLayerStateMessage)
    }

    private fun customNotify(playerState: PlayerState, seekBarposition: Long) {
        pLayerStateMessage.setPlayerState(playerState)
        pLayerStateMessage.seekBarPosition = seekBarposition
        notifyObservers(pLayerStateMessage)
    }

    override fun addObserver(observer: MyObserver) {
        observers.add(observer)
    }

    override fun notifyObservers(o: Any) {
        for (observer in observers) {
            observer.update(o)
        }
    }

    init {
        observers = ArrayList()
        this.context = context
        this.player = player
        this.danmakuView = danmakuView
        this.videoManager = videoManager
        pLayerStateMessage = PlayerStateMessage(PlayerStateMessage.Resume)
        setupPlayerListener()
        setupTimerBar()
        initObservers(danmakuView)

        val pref=context.getSharedPreferences("userInfo",MODE_PRIVATE)
        userId=pref.getString("id","")!!
        videoId=(context as VideoActivity).getVideoId()

        launch {
            val danmuList=CoolVideoNetwork.getInstance().fetchDanmu((context as VideoActivity).getVideoId()).danmus
            for (danmu in danmuList){
                danmuManager.addDanmaku(danmu.content,userId==danmu.userId,danmu.time)
            }
            videoManager.playImmediately()

            var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
            val id=pref.getString("id","").toString()
            val videoActivity=context as VideoActivity
            val datetime=DateUtils.nowDateTime
            repository.addHistoryToServer(id,videoId.toString(),videoActivity.videoTitle,
                videoActivity.videoUrl,videoActivity.videoImgUrl, datetime)
            repository.addHistoryLocal(id,videoId.toString(),videoActivity.videoTitle,
                videoActivity.videoUrl,videoActivity.videoImgUrl, datetime)
        }
    }

    fun onUserSendDanmu(content: String) {
        launch {
            CoolVideoNetwork.getInstance().addDanmu(userId,videoId,videoManager.playerTime,content)
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