package com.example.ben.rainy_night.impl;

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
     * 音乐播放
     *
     * @param data      音乐数据
     * @param position  当前播放位置
     * @param cycleMode 循环模式
     * @param time      定时时间，单位分钟
     */
    @Override
    public void start(Object data, int position, String cycleMode, int time) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(Constant.MUSIC_START, data, position, cycleMode, time));
    }

    /**
     * 继续播放
     */
    @Override
    public void resume() {
        EventBus.getDefault().post(new OnMusicPlayerEvent(Constant.MUSIC_RESUME));
    }

    /**
     * 音乐暂停
     */
    @Override
    public void pause() {
        EventBus.getDefault().post(new OnMusicPlayerEvent(Constant.MUSIC_PAUSE));
    }

    /**
     * 音乐停止
     */
    @Override
    public void stop() {
        EventBus.getDefault().post(new OnMusicPlayerEvent(Constant.MUSIC_STOP));
    }

    /**
     * 播放上一个
     */
    @Override
    public void startPrevious() {
        EventBus.getDefault().post(new OnMusicPlayerEvent(Constant.MUSIC_PREVIOUS));
    }

    /**
     * 播放下一个
     */
    @Override
    public void startNext() {
        EventBus.getDefault().post(new OnMusicPlayerEvent(Constant.MUSIC_NEXT));
    }

    /**
     * 设置循环模式
     *
     * @param cycleMode 单曲循环、列表循环
     */
    @Override
    public void setCycleMode(String cycleMode) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(Constant.MUSIC_SET_CYCLE_MODE, cycleMode));
    }

    /**
     * 设置定时时间
     *
     * @param time 定时时间，单位分钟
     */
    @Override
    public void setRemainTime(int time) {
        EventBus.getDefault().post(new OnMusicPlayerEvent(Constant.MUSIC_SET_TIME, time));
    }
}
