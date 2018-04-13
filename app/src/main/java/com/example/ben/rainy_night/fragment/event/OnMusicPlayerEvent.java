package com.example.ben.rainy_night.fragment.event;

/**
 * @author Ben
 * @date 2018/4/11
 */

public class OnMusicPlayerEvent {

    private int action;
    /**
     * 音乐数据
     */
    private Object data;

    /**
     * 当前播放位置
     */
    private int position;

    /**
     * 循环模式
     */
    private String cycleMode;

    /**
     * 定时时间 ，仅针对伴我睡有效
     * 对于其他不需要定时器的地方传: -1
     * 单位分钟
     */
    private int time;

    public OnMusicPlayerEvent() {

    }

    public OnMusicPlayerEvent(int action) {
        this.action = action;
    }

    public OnMusicPlayerEvent(int action, String cycleMode) {
        this.action = action;
        this.cycleMode = cycleMode;
    }

    public OnMusicPlayerEvent(int action, int time) {
        this.action = action;
        this.time = time;
    }

    public OnMusicPlayerEvent(int action, Object data, int position, String cycleMode, int time) {
        this.action = action;
        this.data = data;
        this.position = position;
        this.cycleMode = cycleMode;
        this.time = time;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCycleMode() {
        return cycleMode;
    }

    public void setCycleMode(String cycleMode) {
        this.cycleMode = cycleMode;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
