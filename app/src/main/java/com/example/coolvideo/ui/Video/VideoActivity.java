package com.example.coolvideo.ui.video;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.coolvideo.R;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;

import master.flame.danmaku.ui.widget.DanmakuView;

public class VideoActivity extends AppCompatActivity {

    public String videoUrls;
    public String videoTitle;
    public String videoId;

    private PlayerView videoView;
    private DanmakuView danmuView;
    private View controlView;
    private DefaultTimeBar timerBar;
    private LinearLayout progressNormal;
    private LinearLayout progressFullscreen;
    private Switch danmuSwitch;
    private EditText danmuEdit;
    private Dialog fullScreenDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

//        String videoUrls="https://v-cdn.zjol.com.cn/276984.mp4";
        getData();
        Log.i("Video",videoUrls);
        String videoTitle="测试视频";
        VideoManager videoManager =new VideoManager(this,videoUrls);
        DanmuManager danmuManager=new DanmuManager(this);

        initViews();
        initListeners(danmuManager);
        initFullscreenDialog();
        initFullscreenButton();
        avoidExitFullScreen();
        initToolbar(videoManager);
        hideStatusBar();

        VideoViewModelFactory videoViewModelFactory=new VideoViewModelFactory(videoManager.getPlayer(), danmuManager.getDanmakuView(),this,videoManager.getPlayerManager());
        VideoViewModel videoViewModel= new ViewModelProvider(this,videoViewModelFactory).get(VideoViewModel.class);
    }

    private void getData() {
        Intent intent=getIntent();
        videoUrls=intent.getStringExtra("videoUrl");
        videoTitle=intent.getStringExtra("videoName");
        videoId=intent.getStringExtra("videoId");
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    private void hideStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initListeners(DanmuManager danmuManager) {
        danmuEdit.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                String content = danmuEdit.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    danmuManager.userAddDanmaku(content);
                    danmuEdit.setText("");
                    //隐藏键盘
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(danmuEdit.getWindowToken(), 0);
                }
            }
            return true;
        });

        danmuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    danmuManager.showDanmu();
                    danmuEdit.setVisibility(View.VISIBLE);
                }else{
                    danmuManager.hideDanmu();
                    danmuEdit.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void avoidExitFullScreen() {
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener (visibility -> {
            if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                onWindowFocusChanged(true);
            }
        });
    }

    private void initViews(){
        this.videoView=findViewById(R.id.exoplayer);
        this.danmuView=findViewById(R.id.danmaku_view);
        this.controlView = videoView.findViewById(R.id.control_view);
        this.timerBar=controlView.findViewById(R.id.exo_progress);
        this.progressNormal=controlView.findViewById(R.id.progress_normal);
        this.progressFullscreen=controlView.findViewById(R.id.progress_fullscreen);
        this.danmuEdit=controlView.findViewById(R.id.danmu_edit);
        this.danmuSwitch=controlView.findViewById(R.id.danmu_switch);
    }

    private void initFullscreenDialog() {
        fullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                super.onBackPressed();
            }
        };
    }

    private void initFullscreenButton() {
        FrameLayout fullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        fullScreenButton.setOnClickListener(v -> {
            if(getResources().getConfiguration().orientation==getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
                setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });
    }

    private void openFullscreenDialog() {
        setupFullscreenView();
        ((ViewGroup) videoView.getParent()).removeView(videoView);
        ((ViewGroup) danmuView.getParent()).removeView(danmuView);

        fullScreenDialog.addContentView(videoView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreenDialog.addContentView(danmuView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreenDialog.show();
    }

    private void setupFullscreenView() {
        progressNormal.removeView(timerBar);
        progressFullscreen.addView(timerBar);

        progressNormal.setVisibility(View.GONE);
        danmuEdit.setVisibility(View.VISIBLE);
        danmuSwitch.setVisibility(View.VISIBLE);
    }

    private void closeFullscreenDialog() {
        setupNormalView();
        ((ViewGroup) videoView.getParent()).removeView(videoView);
        ((ViewGroup) danmuView.getParent()).removeView(danmuView);
        FrameLayout videoFrame=findViewById(R.id.video_frame);
        videoFrame.addView(videoView);
        videoFrame.addView(danmuView);
        fullScreenDialog.dismiss();
    }

    private void setupNormalView() {
        progressFullscreen.removeView(timerBar);
        progressNormal.addView(timerBar);

        progressNormal.setVisibility(View.VISIBLE);
        danmuEdit.setVisibility(View.GONE);
        danmuSwitch.setVisibility(View.GONE);
    }

    private void initToolbar(VideoManager videoManager) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            openFullscreenDialog();
        }else{
            closeFullscreenDialog();
        }
    }
}
