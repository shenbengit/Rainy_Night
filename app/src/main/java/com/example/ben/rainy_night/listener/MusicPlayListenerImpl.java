package com.example.ben.rainy_night.listener;

/**
 * @author Ben
 * @date 2018/4/7
 */

public class MusicPlayListenerImpl implements MusicPlayListener {

    private MusicPlayListenerImpl() {

    }

    private static class Holder {
        private static final MusicPlayListenerImpl INSTANCE = new MusicPlayListenerImpl();
    }

    public static MusicPlayListenerImpl getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 音乐播放
     *
     * @param musicUrl 播放音乐的地址
     */
    @Override
    public void start(String musicUrl) {

    }

    /**
     * 继续播放
     */
    @Override
    public void resume() {

    }

    /**
     * 音乐暂停
     */
    @Override
    public void pause() {

    }

    /**
     * 音乐停止
     */
    @Override
    public void stop() {

    }
}
