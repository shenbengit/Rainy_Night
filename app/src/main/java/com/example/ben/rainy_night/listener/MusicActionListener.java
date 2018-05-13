package com.example.ben.rainy_night.listener;

import android.content.Context;

import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
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
     * 获取播放音乐的数据
     *
     * @param key    key
     * @param entity 音乐数据
     */
    <T> void setData(String key, T entity);


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
     * 设置播放列表,索引默认为0
     *
     * @param musicType 当前播放的音乐的种类
     */
    void setPlayList(String musicType);

    /**
     * 设置播放列表，并指定索引
     *
     * @param musicType 当前播放的音乐的种类
     * @param index     索引
     */
    void setPlayListWithIndex(String musicType, int index);

    /**
     * 根据索引播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  跳转到要播放的位置
     */
    void startWithIndex(String musicType, int position);

    /**
     * 暂停
     */
    void pause();

    /**
     * 继续播放
     */
    void resume();

    /**
     * 停止
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
     * @param position 播放的位置
     */
    void seekTo(int position);

    /**
     * 设置播放音量
     *
     * @param audioVolume audioVolume 播放音量,范围: 0f ~ 1f
     */
    void setVolume(float audioVolume);

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
     * 获取当前播放音乐的进度
     *
     * @return
     */
    long getProgress();


    /**
     * 获取当前播放音频的在List中的位置
     *
     * @return
     */
    int getCurrPlayingIndex();

    /**
     * 获取当前播放音乐的信息
     *
     * @return
     */
    SongInfo getCurrentMediaInfo();

    /**
     * 判断当前的音乐是不是正在播放的音乐
     *
     * @param musicName 音乐名
     * @return
     */
    boolean isCurrMusicIsPlayingMusic(String musicName);

    /**
     * 获取时长
     *
     * @return 时长 单位：秒
     */
    int getDuration();

    /**
     * 添加播放事件监听
     *
     * @param listener
     */
    void addPlayerEventListener(OnPlayerEventListener listener);

    /**
     * 移除播放事件监听
     *
     * @param listener
     */
    void removePlayerEventListener(OnPlayerEventListener listener);
}
