package com.example.ben.rainy_night.player.callback;

import android.content.Context;

/**
 * @author Ben
 * @date 2018/4/12
 */

public interface MusicPlayerCallback {
    /**
     * 开始播放
     *
     * @param context   context
     * @param data      音乐数据
     * @param position  当前播放位置
     * @param cycleMode 循环模式
     * @param time      定时时间，单位分钟
     */
    void start(Context context, Object data, int position, String cycleMode, int time);

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
    void seekTo(long position);

    /**
     * 设置播放状态
     *
     * @param state
     */
    void setState(int state);

    /**
     * 获取当前的播放状态
     */
    int getState();

    /**
     * 设置是否单循环
     *
     * @param looping
     */
    void setLooping(boolean looping);

    /**
     * 设置循环列表
     * 仅在setLooping(false)有效
     *
     * @param object 音乐数据
     */
    void setLoopList(Object object);

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
     * 设置当前播放音频的id
     *
     * @param mediaId
     */
    void setmCurrentMediaId(int mediaId);

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

    /**
     * 设置Callback
     *
     * @param callback
     */
    void setCallback(Callback callback);

    interface Callback {
        /**
         * 当前音乐播放完毕
         */
        void onPlayCompletion();

        /**
         * 在播放状态改变时，可以实现这个回调来更新媒体会话的播放状态。
         *
         * @param state
         */
        void onPlaybackStatusChanged(int state);

        /**
         * 错误提示
         *
         * @param error
         */
        void onError(String error);

        /**
         * 设置正在播放音频的id
         *
         * @param mediaId
         */
        void setCurrentMediaId(String mediaId);
    }
}
