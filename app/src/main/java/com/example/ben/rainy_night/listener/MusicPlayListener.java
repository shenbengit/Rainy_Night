package com.example.ben.rainy_night.listener;

/**
 * @author Ben
 * @date 2018/4/7
 */

public interface MusicPlayListener {
    /**
     * 音乐播放
     *
     * @param musicUrl 播放音乐的地址
     */
    void start(String musicUrl);

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
}
