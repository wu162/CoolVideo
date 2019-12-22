package com.example.coolvideo.utils.listener;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;


import com.example.coolvideo.R;
import com.example.coolvideo.ui.Video.VideoManager;
import com.google.android.exoplayer2.ui.PlayerView;

public class OnScreenTouchListener implements View.OnTouchListener {

    private static final String TAG = "OnScreenTouchListener";

    private Context context;

    private final GestureDetectorCompat mDetector;
    private PlayerView videoView;
    private VideoManager playerManager;
    private float fastForwardDistance;
    private float screenWidth;
    private float screenHeight;

    //手势最大快进60秒
    private static final int MAX_OFFSET=60000;

    public OnScreenTouchListener(Context context, VideoManager playerManager) {
        mDetector = new GestureDetectorCompat(context, new GestureListener());
        this.context=context;
        this.videoView=((Activity)context).findViewById(R.id.exoplayer);
        this.playerManager=playerManager;
        this.fastForwardDistance=0;

        //获取屏幕大小
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.screenWidth = dm.widthPixels;
        this.screenHeight = dm.heightPixels;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                this.fastForwardDistance = 0;
                break;
            case MotionEvent.ACTION_UP:
                if(fastForwardDistance!=0)
                {
                    playerManager.adjustPlayerTime(calculateOffset(fastForwardDistance));
                }
                break;
        }
        return mDetector.onTouchEvent(event);

    }

    private long calculateOffset(float fastForwardDistance) {
        if(context.getResources().getConfiguration().orientation==context.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
            return (long)(fastForwardDistance/screenHeight * MAX_OFFSET);
        } else {
            return (long)(fastForwardDistance/screenWidth * MAX_OFFSET);
        }
    }

    private void onClick() {
        Log.i(TAG, "onClick: Clicking in the screen");
        if(videoView.isControllerVisible()){
            videoView.hideController();
        } else {
            videoView.showController();
        }
    }

    private void onDoubleClick() {
        Log.i(TAG, "onClick: Clicking TWO TIMES in the screen");
        if(playerManager.isPlayerPlaying())
        {
            playerManager.stopImmediately();
        }
        else
        {
            playerManager.playImmediately();
        }
    }

    private void onFastForward(float distanceX){
        fastForwardDistance+=distanceX;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onClick();
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            onDoubleClick();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            onFastForward(-distanceX);
            return true;
        }
    }
}