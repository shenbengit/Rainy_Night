package com.example.ben.rainy_night.player.manager;

import android.content.Context;

import com.example.ben.rainy_night.player.callback.MusicPlayerCallback;
import com.example.ben.rainy_night.player.callback.MusicPlayerImpl;
import com.lzx.musiclibrary.aidl.model.SongInfo;

import java.util.List;

/**
 * @author Ben
 * @date 2018/4/12
 */

public class MusicPlayerManager {

    private MusicPlayerCallback mCallback;

    private MusicPlayerManager() {
        mCallback = MusicPlayerImpl.getInstance();
    }

    public static MusicPlayerManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MusicPlayerManager INSTANCE = new MusicPlayerManager();
    }

    /**
     * 获取Context
     *
     * @param context
     */
    public void initContext(Context context) {
        if (mCallback != null) {
            mCallback.initContext(context);
        }
    }

    /**
     * 开始播放
     *
     * @param list      播放音乐的列表
     * @param postition 开始播放的位置
     */
    public void start(List<SongInfo> list, int postition) {
        if (mCallback != null) {
            mCallback.start(list, postition);
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mCallback != null) {
            mCallback.pause();
        }
    }

    /**
     * 继续
     */
    public void resume() {
        if (mCallback != null) {
            mCallback.resume();
        }
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (mCallback != null) {
            mCallback.stop();
        }
    }

    /**
     * 播放上一个
     */
    public void startPrevious() {
        if (mCallback != null) {
            mCallback.startPrevious();
        }
    }

    /**
     * 播放下一个
     */
    public void startNext() {
        if (mCallback != null) {
            mCallback.startNext();
        }
    }

    /**
     * 寻求指定的时间位置
     *
     * @param position
     */
    public void seekTo(int position) {
        if (mCallback != null) {
            mCallback.seekTo(position);
        }
    }

    /**
     * 设置播放模式
     *
     * @param isListLooping
     */
    public void setPlayMode(boolean isListLooping) {
        if (mCallback != null) {
            mCallback.setPlayMode(isListLooping);
        }
    }

    /**
     * 获取当前的播放状态
     */
    public int getState() {
        if (mCallback != null) {
            return mCallback.getState();
        } else {
            return -1;
        }
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        if (mCallback != null) {
            return mCallback.isPlaying();
        }
        return false;
    }

    /**
     * @return 获取当前播放流的进度位置
     */
    public long getCurrentStreamPosition() {
        if (mCallback != null) {
            mCallback.getCurrentStreamPosition();
        }
        return 0;
    }

    /**
     * 获取当前播放音频的id
     *
     * @return
     */
    public int getmCurrentMediaId() {
        if (mCallback != null) {
            return mCallback.getmCurrentMediaId();
        } else {
            return -1;
        }
    }

    /**
     * 获取时长
     *
     * @return
     */
    public int getDuration() {
        if (mCallback != null) {
            return mCallback.getDuration();
        } else {
            return -1;
        }
    }

    /**
     * 设置定时时间
     *
     * @param time 定时时间，单位分钟
     */
    public void setRemainTime(int time) {
        if (mCallback != null) {
            mCallback.setRemainTime(time);
        }
    }

}
