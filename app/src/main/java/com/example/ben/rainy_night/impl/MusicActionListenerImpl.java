package com.example.ben.rainy_night.impl;

import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.event.OnMusicPlayerEvent;
import com.example.ben.rainy_night.listener.MusicActionListener;
import com.example.ben.rainy_night.util.Constant;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Ben
 * @date 2018/4/7
 */

public class MusicActionListenerImpl implements MusicActionListener {

    private MusicActionListenerImpl() {
    }

    public static MusicActionListenerImpl getInstance() {
        return new MusicActionListenerImpl();
    }

    /**
     * 开始播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     */
    @Override
    public void start(String musicType, int position) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(musicType, Constant.MUSIC_START, position));
    }

    /**
     * 暂停
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void pause(String musicType) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(musicType, Constant.MUSIC_PAUSE));
    }

    /**
     * 继续播放
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void resume(String musicType) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(musicType, Constant.MUSIC_PAUSE));
    }

    /**
     * 停止
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void stop(String musicType) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(musicType, Constant.MUSIC_PAUSE));
    }

    /**
     * 播放上一个
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void startPrevious(String musicType) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(musicType, Constant.MUSIC_PAUSE));
    }

    /**
     * 播放下一个
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void startNext(String musicType) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(musicType, Constant.MUSIC_PAUSE));
    }

    /**
     * 设置循环模式
     *
     * @param musicType 当前播放的音乐的种类
     * @param playMode  循环模式
     */
    @Override
    public void setPlayMode(String musicType, String playMode) {
        if (TextUtils.equals(playMode, Constant.LIST_CYCLE)) {
            EventBus.getDefault().post(new OnMusicPlayerEvent(musicType, true));
        } else if (TextUtils.equals(playMode, Constant.SINGLE_CYCLE)) {
            EventBus.getDefault().post(new OnMusicPlayerEvent(musicType, false));
        }
    }

    /**
     * 设置倒计时
     *
     * @param musicType  当前播放的音乐的种类
     * @param remainTime 剩余时间
     */
    @Override
    public void setRemainTime(String musicType, long remainTime) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(musicType, remainTime));
    }
}
