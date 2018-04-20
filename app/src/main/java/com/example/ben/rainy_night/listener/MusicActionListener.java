package com.example.ben.rainy_night.listener;

import android.content.Context;

import com.lzx.musiclibrary.aidl.model.SongInfo;

/**
 * @author Ben
 * @date 2018/4/7
 */

public interface MusicActionListener {
    /**
     * 获取Context
     *
     * @param context
     */
    void initContext(Context context);

    /**
     * 获取播放音乐的数据
     *
     * @param cacheName 缓存key
     */
    void setData(String cacheName);

    /**
     * 获取当前播放音乐的类型
     *
     * @return
     */
    String getCurrentMusicType();

    /**
     * 开始播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     */
    void start(String musicType, int position);

    /**
     * 开始播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     * @param playMode  是否列表循环
     */
    void start(String musicType, int position, int playMode);

    /**
     * 开始播放
     *
     * @param musicType  当前播放的音乐的种类
     * @param position   播放的位置
     * @param playMode   是否列表循环
     * @param remainTime 剩余时间
     */
    void start(String musicType, int position, int playMode, long remainTime);

    /**
     * 根据索引播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  跳转到要播放的位置
     */
    void startWithIndex(String musicType, int position);

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
    void setPlayMode(String musicType, int playMode);

    /**
     * 设置倒计时
     *
     * @param musicType  当前播放的音乐的种类
     * @param remainTime 剩余时间
     */
    void setRemainTime(String musicType, long remainTime);

    /**
     * 寻求指定的时间位置
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     */
    void seekTo(String musicType, int position);

    /**
     * 获取当前的播放状态
     *
     * @return
     */
    int getState();

    /**
     * 是否正在播放
     *
     * @return
     */
    boolean isPlaying();

    /**
     * 获取当前播放流的进度位置
     *
     * @param musicType 当前播放的音乐的种类
     * @return
     */
    long getCurrentStreamPosition(String musicType);


    /**
     * 获取当前播放音频的id
     *
     * @param musicType 当前播放的音乐的种类
     * @return
     */
    int getmCurrentMediaId(String musicType);

    /**
     * 获取当前播放音乐的信息
     *
     * @param musicType
     * @return
     */
    SongInfo getCurrentMediaInfo(String musicType);

    /**
     * 获取时长
     *
     * @param musicType 当前播放的音乐的种类
     * @param musicType
     * @return
     */
    int getDuration(String musicType);
}
