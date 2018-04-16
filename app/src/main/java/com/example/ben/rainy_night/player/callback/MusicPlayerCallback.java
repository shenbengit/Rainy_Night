package com.example.ben.rainy_night.player.callback;

import android.content.Context;

import com.lzx.musiclibrary.aidl.model.SongInfo;

import java.util.List;

/**
 * @author Ben
 * @date 2018/4/12
 */

public interface MusicPlayerCallback {
    /**
     * 获取Context
     *
     * @param context
     */
    void initContext(Context context);

    /**
     * 开始播放
     *
     * @param list      播放音乐的列表
     * @param postition 开始播放的位置
     */
    void start(List<SongInfo> list, int postition);

    /**
     * 暂停
     */
    void pause();

    /**
     * 继续
     */
    void resume();

    /**
     * 停止播放
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
     * 寻求指定的时间位置
     *
     * @param position
     */
    void seekTo(int position);

    /**
     * 设置播放模式
     *
     * @param isListLooping
     */
    void setPlayMode(boolean isListLooping);

    /**
     * 获取当前的播放状态
     */
    int getState();

    /**
     * 是否正在播放
     *
     * @return
     */
    boolean isPlaying();

    /**
     * @return 获取当前播放流的进度位置
     */
    long getCurrentStreamPosition();


    /**
     * 获取当前播放音频的id
     *
     * @return
     */
    int getmCurrentMediaId();

    /**
     * 获取时长
     *
     * @return
     */
    int getDuration();

    /**
     * 设置定时时间
     *
     * @param time 定时时间，单位分钟
     */
    void setRemainTime(int time);
}
