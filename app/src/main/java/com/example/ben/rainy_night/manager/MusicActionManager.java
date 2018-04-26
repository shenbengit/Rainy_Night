package com.example.ben.rainy_night.manager;

import android.content.Context;
import android.text.TextUtils;

import com.example.ben.rainy_night.http.okgo.entity.SleepFmEntity;
import com.example.ben.rainy_night.impl.MusicActionListenerImpl;
import com.example.ben.rainy_night.listener.MusicActionListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;

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
     * 获取Context
     *
     * @param context
     */
    public void initContext(Context context) {
        if (mListener != null) {
            mListener.initContext(context);
        }
    }

    /**
     * 获取播放音乐的数据
     *
     * @param cacheName 缓存key
     */
    public void setData(String cacheName) {
        if (mListener != null) {
            mListener.setData(cacheName);
        }
    }

    /**
     * 获取播放音乐的数据
     *
     * @param key    key
     * @param entity 音乐数据
     */
    public void setData(String key, SleepFmEntity entity) {
        if (mListener != null) {
            mListener.setData(key, entity);
        }
    }

    /**
     * 追加音乐数据
     *
     * @param key    key
     * @param entity 追加的音乐数据
     */
    public <T> void addData(String key, T entity) {
        if (mListener != null) {
            mListener.addData(key, entity);
        }
    }

    /**
     * 获取当前播放音乐的类型
     *
     * @return
     */
    public String getCurrentMusicType() {
        if (mListener != null) {
            return mListener.getCurrentMusicType();
        }
        return null;
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
     * 开始播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     * @param playMode  是否列表循环
     */
    public void start(String musicType, int position, int playMode) {
        if (mListener != null) {
            mListener.start(musicType, position, playMode);
        }
    }

    /**
     * 开始播放
     *
     * @param musicType  当前播放的音乐的种类
     * @param position   播放的位置
     * @param playMode   是否列表循环
     * @param remainTime 剩余时间
     */
    public void start(String musicType, int position, int playMode, int remainTime) {
        if (mListener != null) {
            mListener.start(musicType, position, playMode, remainTime);
        }
    }

    /**
     * 根据索引播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  跳转到要播放的位置
     */
    public void startWithIndex(String musicType, int position) {
        if (mListener != null) {
            mListener.startWithIndex(musicType, position);
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
     * @param playMode 循环模式
     */
    public void setPlayMode(int playMode) {
        if (mListener != null) {
            mListener.setPlayMode(playMode);
        }
    }

    /**
     * 设置倒计时
     *
     * @param remainTime 剩余时间 ：单位分钟
     */
    public void setRemainTime(long remainTime) {
        if (mListener != null) {
            mListener.setRemainTime(remainTime);
        }
    }

    /**
     * 寻求指定的时间位置
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     */

    public void seekTo(String musicType, int position) {
        if (mListener != null) {
            mListener.seekTo(musicType, position);
        }
    }

    /**
     * 获取当前的播放状态
     */

    public int getState() {
        if (mListener != null) {
            return mListener.getState();
        }
        return -1;
    }

    /**
     * 获取当前循环模式
     *
     * @return
     */
    public int getPlayMode() {
        if (mListener != null) {
            return mListener.getPlayMode();
        }
        return -1;
    }

    /**
     * 是否正在播放
     *
     * @return
     */

    public boolean isPlaying() {
        if (mListener != null) {
            return mListener.isPlaying();
        }
        return false;
    }

    /**
     * 获取当前播放流的进度位置
     *
     * @param musicType 当前播放的音乐的种类
     * @return
     */

    public long getCurrentStreamPosition(String musicType) {
        return 0;
    }

    /**
     * 获取当前播放音频的id
     *
     * @param musicType 当前播放的音乐的种类
     * @return
     */

    public int getmCurrentMediaId(String musicType) {
        if (mListener != null) {
            mListener.getmCurrentMediaId(musicType);
        }

        return -1;
    }

    /**
     * 获取当前播放音乐的信息
     *
     * @param musicType
     * @return
     */

    public SongInfo getCurrentMediaInfo(String musicType) {
        if (mListener != null) {
            mListener.getCurrentMediaInfo(musicType);
        }
        return null;
    }

    /**
     * 获取时长
     *
     * @param musicType 当前播放的音乐的种类
     * @return 时长 单位：秒
     */

    public int getDuration(String musicType) {
        if (mListener != null) {
            mListener.getDuration(musicType);
        }
        return 0;
    }
}
