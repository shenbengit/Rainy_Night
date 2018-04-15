package com.example.ben.rainy_night.manager;

import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.event.OnMusicPlayerEvent;
import com.example.ben.rainy_night.impl.MusicActionListenerImpl;
import com.example.ben.rainy_night.listener.MusicActionListener;
import com.example.ben.rainy_night.util.Constant;

import org.greenrobot.eventbus.EventBus;

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
     * 开始播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     */
    public void start(String musicType, int position) {
        if (mListener != null) {
            mListener.start(musicType, position);
        }
    }

    /**
     * 暂停
     *
     * @param musicType 当前播放的音乐的种类
     */
    public void pause(String musicType) {
        if (mListener != null) {
            mListener.pause(musicType);
        }
    }

    /**
     * 继续播放
     *
     * @param musicType 当前播放的音乐的种类
     */
    public void resume(String musicType) {
        if (mListener != null) {
            mListener.resume(musicType);
        }
    }

    /**
     * 停止
     *
     * @param musicType 当前播放的音乐的种类
     */
    public void stop(String musicType) {
        if (mListener != null) {
            mListener.stop(musicType);
        }
    }

    /**
     * 播放上一个
     *
     * @param musicType 当前播放的音乐的种类
     */
    public void startPrevious(String musicType) {
        if (mListener != null) {
            mListener.startPrevious(musicType);
        }
    }

    /**
     * 播放下一个
     *
     * @param musicType 当前播放的音乐的种类
     */
    public void startNext(String musicType) {
        if (mListener != null) {
            mListener.startNext(musicType);
        }
    }

    /**
     * 设置循环模式
     *
     * @param musicType 当前播放的音乐的种类
     * @param playMode  循环模式
     */
    public void setPlayMode(String musicType, String playMode) {
        if (mListener != null) {
            mListener.setPlayMode(musicType, playMode);
        }
    }

    /**
     * 设置倒计时
     *
     * @param musicType  当前播放的音乐的种类
     * @param remainTime 剩余时间
     */
    public void setRemainTime(String musicType, long remainTime) {
        if (mListener != null) {
            mListener.setRemainTime(musicType, remainTime);
        }
    }
}
