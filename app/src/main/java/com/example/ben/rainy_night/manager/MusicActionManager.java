package com.example.ben.rainy_night.manager;

import com.example.ben.rainy_night.listener.MusicActionListener;
import com.example.ben.rainy_night.impl.MusicActionListenerImpl;

/**
 * 音乐播放工具类
 *
 * @author Ben
 * @date 2018/4/7
 */

public class MusicActionManager {

    private MusicActionListener mListener;

    private MusicActionManager() {
        mListener = MusicActionListenerImpl.getInstance();
    }

    public static MusicActionManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MusicActionManager INSTANCE = new MusicActionManager();
    }

    /**
     * 音乐播放
     *
     * @param data      音乐数据
     * @param position  当前播放位置
     * @param cycleMode 循环模式
     * @param time      定时时间
     */
    public void start(Object data, int position, String cycleMode, int time) {
        if (mListener != null) {
            mListener.start(data, position, cycleMode, time);
        }
    }

    /**
     * 继续播放
     */
    public void resume() {
        if (mListener != null) {
            mListener.resume();
        }
    }

    /**
     * 音乐暂停
     */
    public void pause() {
        if (mListener != null) {
            mListener.pause();
        }
    }

    /**
     * 音乐停止
     */
    public void stop() {
        if (mListener != null) {
            mListener.stop();
        }
    }

    /**
     * 播放上一个
     */
    public void startPrevious() {
        if (mListener != null) {
            mListener.startPrevious();
        }
    }

    /**
     * 播放下一个
     */
    public void startNext() {
        if (mListener != null) {
            mListener.startNext();
        }
    }

    /**
     * 设置循环模式
     *
     * @param cycleMode 单曲循环、列表循环
     */
    public void setCycleMode(String cycleMode) {
        if (mListener != null) {
            mListener.setCycleMode(cycleMode);
        }
    }

    /**
     * 设置定时时间
     *
     * @param time 定时时间，单位分钟
     */
    public void setRemainTime(int time) {
        if (mListener != null) {
            mListener.setRemainTime(time);
        }
    }
}
