package com.example.coolvideo.ui.Video;

public class Danmu {
    private Long time;
    private String content;

    public Danmu(Long time, String content) {
        this.time = time;
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
