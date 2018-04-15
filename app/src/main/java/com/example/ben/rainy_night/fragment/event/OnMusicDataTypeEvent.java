package com.example.ben.rainy_night.fragment.event;

/**
 * @author Ben
 * @date 2018/4/15
 */

public class OnMusicDataTypeEvent {
    /**
     * 音乐种类
     */
    private String musicType;

    public OnMusicDataTypeEvent(String musicType) {
        this.musicType = musicType;
    }

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }
}
