package com.example.coolvideo.ui.Video;

import com.example.coolvideo.utils.MyObserver;
import com.example.coolvideo.utils.PlayerStateMessage;

import master.flame.danmaku.ui.widget.DanmakuView;

public class DanmuObserver implements MyObserver {

    private DanmakuView danmakuView;

    public DanmuObserver(DanmakuView danmakuView){
        this.danmakuView=danmakuView;
    }

    @Override
    public void update(Object arg) {
        PlayerStateMessage playerStateMessage=(PlayerStateMessage)arg;
        switch (playerStateMessage.getPlayerState()){
            case Buffering:
            case Pause:
                danmakuView.pause();
                break;
            case Resume:
                danmakuView.resume();
                break;
            case SeekBarChanged:
                danmakuView.seekTo(playerStateMessage.getSeekBarPosition());
                break;
            default:
                break;
        }
    }
}
