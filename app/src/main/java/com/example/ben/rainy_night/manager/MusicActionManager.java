package com.example.ben.rainy_night.manager;

import android.content.Context;

import com.example.ben.rainy_night.impl.MusicActionListenerImpl;
import com.example.ben.rainy_night.listener.MusicActionListener;
import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;

import java.io.IOException;

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
     * @param entity 音乐数据: MusicEntity、SleepFmEntity
     */
    public <T> void setData(String key, T entity) {
        if (mListener != null) {
            mListener.setData(key, entity);
        }
    }

    /**
     * 追加音乐数据
     *
     * @param key    key
     * @param object 追加的音乐数据
     */
    public <T> void addData(String key, T object) {
        if (mListener != null) {
            mListener.addData(key, object);
        }
    }

    /**
     * 获取当前播放音乐的类型
     *
     * @return
     */
    public String getCurrentMusicType() {
        return mListener != null ? mListener.getCurrentMusicType() : null;
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
     * 设置播放列表,索引默认为0
     *
     * @param musicType 当前播放的音乐的种类
     */
    public void setPlayList(String musicType) {
        if (mListener != null) {
            mListener.setPlayList(musicType);
        }
    }

    /**
     * 设置播放列表，并指定索引
     *
     * @param musicType 当前播放的音乐的种类
     * @param index     索引
     */
    public void setPlayListWithIndex(String musicType, int index) {
        if (mListener != null) {
            mListener.setPlayListWithIndex(musicType, index);
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
     */
    public void pause() {
        if (mListener != null) {
            mListener.pause();
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
     * 停止
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
     * @param position 播放的位置
     */

    public void seekTo(int position) {
        if (mListener != null) {
            mListener.seekTo(position);
        }
    }

    /**
     * 设置播放音量
     *
     * @param audioVolume audioVolume 播放音量,范围: 0f ~ 1f
     */
    public void setVolume(float audioVolume) {
        if (mListener != null) {
            mListener.setVolume(audioVolume);
        }
    }

    /**
     * 获取当前的播放状态
     */

    public int getState() {
        return mListener != null ? mListener.getState() : 0;
    }

    /**
     * 获取当前循环模式
     *
     * @return
     */
    public int getPlayMode() {
        return mListener != null ? mListener.getPlayMode() : 0;
    }

    /**
     * 是否正在播放
     *
     * @return
     */

    public boolean isPlaying() {
        return mListener != null && mListener.isPlaying();
    }

    /**
     * 获取当前播放音乐的进度
     *
     * @return
     */
    public long getProgress() {
        return mListener != null ? mListener.getProgress() : 0;
    }

    /**
     * 获取当前播放音频的在List中的位置
     *
     * @return
     */

    public int getCurrPlayingIndex() {
        return mListener != null ? mListener.getCurrPlayingIndex() : -1;
    }

    /**
     * 获取当前播放音乐的信息
     *
     * @return
     */

    public SongInfo getCurrentMediaInfo() {
        return mListener != null ? mListener.getCurrentMediaInfo() : null;
    }

    public String getCacheFilePath() {
        return mListener != null ? mListener.getCacheFilePath() : null;
    }

    /**
     * 获取缓存目录的文件大小
     *
     * @return 文件大小, 单位为:B
     */
    public long getCachedFileSize() {
        try {
            return mListener != null ? mListener.getCachedFileSize() : 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 清除已经缓存的音乐
     *
     * @return 清除是否成功
     */
    public void clearCachedFile() {
        if (mListener != null) {
            mListener.clearCachedFile();
        }
    }

    /**
     * 判断当前的音乐是不是正在播放的音乐
     *
     * @param musicName 音乐名
     * @return
     */
    public boolean isCurrMusicIsPlayingMusic(String musicName) {
        return mListener != null && mListener.isCurrMusicIsPlayingMusic(musicName);
    }

    /**
     * 获取时长
     *
     * @return 时长 单位：秒
     */

    public int getDuration() {
        return mListener != null ? mListener.getDuration() : 0;
    }

    /**
     * 添加播放事件监听
     *
     * @param listener
     */
    public void addPlayerEventListener(OnPlayerEventListener listener) {
        if (mListener != null) {
            mListener.addPlayerEventListener(listener);
        }
    }

    /**
     * 移除播放事件监听
     *
     * @param listener
     */
    public void removePlayerEventListener(OnPlayerEventListener listener) {
        if (mListener != null) {
            mListener.removePlayerEventListener(listener);
        }
    }
}
