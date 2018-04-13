package com.example.ben.rainy_night.listener;

/**
 * @author Ben
 * @date 2018/4/7
 */

public interface MusicActionListener {
    /**
     * 音乐播放
     *
     * @param data      音乐数据
     * @param position  当前播放位置
     * @param cycleMode 循环模式
     * @param time      定时时间，单位分钟
     */
    void start(Object data, int position, String cycleMode, int time);

    /**
     * 继续播放
     */
    void resume();

    /**
     * 音乐暂停
     */
    void pause();

    /**
     * 音乐停止
     */
    void stop();

    /**
     * 播放上一个
     */
    void startPrevious();

    /**
     * 播放下一个
     */
    void startNext();

    /**
     * 设置循环模式
     *
     * @param cycleMode 单曲循环、列表循环
     */
    void setCycleMode(String cycleMode);

    /**
     * 设置定时时间
     *
     * @param time 定时时间，单位分钟
     */
    void setRemainTime(int time);
}
