package com.example.ben.rainy_night.fragment.event;

/**
 * @author Ben
 * @date 2018/4/11
 */

public class OnMusicPlayerEvent {

    /**
     * 音乐种类
     */

    private String musicType;

    /**
     * 音乐动作
     */
    private String musicAction;

    /**
     * 当前播放位置
     */
    private int position;

    /**
     * 是否是列表循环
     * true: 列表循环
     * false: 单循环
     * 默认 列表循环
     */
    private boolean isListLooping;

    /**
     * 定时时间 ，仅针对伴我睡有效
     * 对于其他不需要定时器的地方传: -1
     * 单位分钟
     */
    private long time;

    public OnMusicPlayerEvent() {

    }

    /**
     * 播放音乐
     *
     * @param musicType 音乐种类
     * @param position  从第几个开始播放
     */
    public OnMusicPlayerEvent(String musicType, String musicAction, int position) {
        this.musicType = musicType;
        this.musicAction = musicAction;
        this.position = position;
    }

    /**
     * 设置定时时间
     *
     * @param time
     */
    public OnMusicPlayerEvent(String musicType, long time) {
        this.musicType = musicType;
        this.time = time;
    }

    /**
     * 播放音乐动作
     *
     * @param musicType
     * @param musicAction
     */
    public OnMusicPlayerEvent(String musicType, String musicAction) {
        this.musicType = musicType;
        this.musicAction = musicAction;
    }

    /**
     * 设置循环模式
     *
     * @param musicType     音乐种类
     * @param isListLooping 是否是列表循环
     */

    public OnMusicPlayerEvent(String musicType, boolean isListLooping) {
        this.musicType = musicType;
        this.isListLooping = isListLooping;
    }

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String musicType) {
        this.musicType = musicType;
    }

    public String getMusicAction() {
        return musicAction;
    }

    public void setMusicAction(String musicAction) {
        this.musicAction = musicAction;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isListLooping() {
        return isListLooping;
    }

    public void setListLooping(boolean listLooping) {
        isListLooping = listLooping;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
