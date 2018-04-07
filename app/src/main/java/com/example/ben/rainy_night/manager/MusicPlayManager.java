package com.example.ben.rainy_night.manager;

import com.example.ben.rainy_night.listener.MusicPlayListener;
import com.example.ben.rainy_night.listener.MusicPlayListenerImpl;

/**
 * 音乐播放工具类
 *
 * @author Ben
 * @date 2018/4/7
 */

public class MusicPlayManager {

    private MusicPlayListener mListener;

    private MusicPlayManager() {
        mListener = MusicPlayListenerImpl.getInstance();
    }

    public static MusicPlayManager gerInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final MusicPlayManager INSTANCE = new MusicPlayManager();
    }

    /**
     * 音乐播放
     *
     * @param musicUrl 播放音乐的地址
     */
    public void start(String musicUrl) {
        if (mListener != null) {
            mListener.start(musicUrl);
        }
    }

    /**
     * 继续播放
     */
    public void resume() {
        if (mListener!=null){
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
}
