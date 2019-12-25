package com.example.coolvideo.ui.video;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.coolvideo.R;

import java.util.Arrays;
import java.util.List;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class DanmuManager implements LifecycleObserver {

    private DanmakuView danmakuView;

    private DanmakuContext danmakuContext;

    private Context context;

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    private List<Danmu> danmuList= Arrays.asList(
            new Danmu(0L,"测试弹幕1"),
            new Danmu(4000L,"测试弹幕2"),
            new Danmu(5000L,"测试弹幕3"),
            new Danmu(6000L,"测试弹幕4"),
            new Danmu(7000L,"测试弹幕5"),
            new Danmu(8000L,"测试弹幕6"),
            new Danmu(9000L,"测试弹幕7")
    );

    public DanmuManager(Context context){
        this.context=context;
        ((AppCompatActivity)context).getLifecycle().addObserver(this);
        danmakuView = ((Activity)context).findViewById(R.id.danmaku_view);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                loadDanmuList();
            }
            @Override
            public void updateTimer(DanmakuTimer timer) { }
            @Override
            public void danmakuShown(BaseDanmaku danmaku) { }
            @Override
            public void drawingFinished() {}
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);
    }

    public void userAddDanmaku(String content) {
        addDanmaku(content,true,danmakuView.getCurrentTime());
    }

    public void addDanmaku(String content, boolean withBorder,long time) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(time);
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void loadDanmuList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(Danmu danmu : danmuList)
                {
                    BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
                    danmaku.text = danmu.getContent();
                    danmaku.padding = 5;
                    danmaku.textSize = sp2px(20);
                    danmaku.textColor = Color.WHITE;
                    danmaku.setTime(danmu.getTime());
                    danmakuView.addDanmaku(danmaku);
                }
            }
        }).start();
    }

    public void showDanmu(){
        danmakuView.show();
    }

    public void hideDanmu(){
        danmakuView.hide();
    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }

    public DanmakuView getDanmakuView() {
        return danmakuView;
    }
}
