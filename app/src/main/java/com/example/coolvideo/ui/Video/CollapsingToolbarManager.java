package com.example.coolvideo.ui.video;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.coolvideo.R;
import com.example.coolvideo.utils.MyObserver;
import com.example.coolvideo.utils.PlayerStateMessage;
import com.google.android.material.appbar.AppBarLayout;

public class CollapsingToolbarManager implements MyObserver {

    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    private LinearLayout playButton;
    private AppBarLayout app_bar;

    private View mAppBarChildAt;
    private AppBarLayout.LayoutParams  mAppBarParams;
    private VideoManager exoPlayerViewManager;

    public CollapsingToolbarManager(Context context, VideoManager exoPlayerViewManager){
        playButton=((Activity)context).findViewById(R.id.playButton);
        app_bar=((Activity)context).findViewById(R.id.app_bar);
        this.exoPlayerViewManager=exoPlayerViewManager;
        iniAppBar();
    }

    private void iniAppBar() {
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarManager.CollapsingToolbarLayoutState.EXPANDED) {
                        playButton.setVisibility(View.GONE);
                        state = CollapsingToolbarManager.CollapsingToolbarLayoutState.EXPANDED;
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarManager.CollapsingToolbarLayoutState.COLLAPSED) {
                        playButton.setVisibility(View.VISIBLE);//显示播放按钮
                        state = CollapsingToolbarManager.CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != CollapsingToolbarManager.CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if(state == CollapsingToolbarManager.CollapsingToolbarLayoutState.COLLAPSED){
                            playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
                        state = CollapsingToolbarManager.CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_bar.setExpanded(true);
                exoPlayerViewManager.playImmediately();
            }
        });

        mAppBarChildAt = app_bar.getChildAt(0);
        mAppBarParams = (AppBarLayout.LayoutParams)mAppBarChildAt.getLayoutParams();
    }

    @Override
    public void update(Object arg) {
        PlayerStateMessage playerStateMessage=(PlayerStateMessage)arg;
        switch (playerStateMessage.getPlayerState()){
            case Buffering:
            case Resume:
                changeAppbar(true);
                break;
            default:
                changeAppbar(false);
                break;
        }
    }

    private void changeAppbar(boolean playing) {
        if(!playing)
        {
            mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                                       | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                                       | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
            mAppBarChildAt.setLayoutParams(mAppBarParams);
        }
        else
        {
            mAppBarParams = (AppBarLayout.LayoutParams)mAppBarChildAt.getLayoutParams();
            mAppBarParams.setScrollFlags(0);
        }
    }
}
