package com.example.ben.rainy_night.listener;

/**
 * @author Ben
 * @date 2018/4/7
 */

public interface MusicActionListener {
    /**
     * 开始播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     */
    void start(String musicType, int position);

    /**
     * 暂停
     *
     * @param musicType 当前播放的音乐的种类
     */
    void pause(String musicType);

    /**
     * 继续播放
     *
     * @param musicType 当前播放的音乐的种类
     */
    void resume(String musicType);

    /**
     * 停止
     *
     * @param musicType 当前播放的音乐的种类
     */
    void stop(String musicType);

    /**
     * 播放上一个
     *
     * @param musicType 当前播放的音乐的种类
     */
    void startPrevious(String musicType);

    /**
     * 播放下一个
     *
     * @param musicType 当前播放的音乐的种类
     */
    void startNext(String musicType);

    /**
     * 设置循环模式
     *
     * @param musicType 当前播放的音乐的种类
     * @param playMode  循环模式
     */
    void setPlayMode(String musicType, String playMode);

    /**
     * 设置倒计时
     *
     * @param musicType  当前播放的音乐的种类
     * @param remainTime 剩余时间
     */
    void setRemainTime(String musicType, long remainTime);
}
