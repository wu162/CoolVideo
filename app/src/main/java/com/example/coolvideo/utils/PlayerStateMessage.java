package com.example.coolvideo.utils;

public class PlayerStateMessage {
    public enum PlayerState{
        Resume,Pause,SeekBarChanged,Buffering
    }
    public static final PlayerState Resume=PlayerState.Resume;
    public static final PlayerState Pause=PlayerState.Pause;
    public static final PlayerState SeekBarChanged=PlayerState.SeekBarChanged;
    public static final PlayerState Buffering=PlayerState.Buffering;

    private long seekBarPosition;

    public PlayerState playerState;

    public PlayerStateMessage(PlayerState playerState){
        this.playerState=playerState;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public long getSeekBarPosition() {
        return seekBarPosition;
    }

    public void setSeekBarPosition(long seekBarPosition) {
        this.seekBarPosition = seekBarPosition;
    }
}
