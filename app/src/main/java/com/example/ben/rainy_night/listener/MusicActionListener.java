package com.example.ben.rainy_night.listener;

import android.content.Context;

import com.example.ben.rainy_night.http.okgo.entity.SleepFmEntity;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;

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
     * 获取播放音乐的数据
     *
     * @param key    key
     * @param entity 音乐数据
     */
    void setData(String key, SleepFmEntity entity);

    /**
     * 追加音乐数据
     *
     * @param key    key
     * @param object 追加的音乐数据
     */
    <T> void addData(String key, T object);

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
     * @param playMode 循环模式
     */
    void setPlayMode(int playMode);

    /**
     * 设置倒计时
     *
     * @param remainTime 剩余时间
     */
    void setRemainTime(long remainTime);

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
     * 获取当前循环模式
     *
     * @return
     */
    int getPlayMode();

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
     * @return 时长 单位：秒
     */
    int getDuration(String musicType);
}
