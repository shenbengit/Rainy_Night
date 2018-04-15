package com.example.ben.rainy_night.player.callback;

import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.util.Constant;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.constans.PlayMode;
import com.lzx.musiclibrary.manager.MusicManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/4/12
 */

public class MusicPlayerImpl implements MusicPlayerCallback {

    /**
     * 音乐数据
     */
    private MusicEntity mEntity;
    /**
     * 当前播放位置
     */
    private int mCurrentMediaId = 0;
    /**
     * 循环模式
     */
    private String mCycle = Constant.SINGLE_CYCLE;
    /**
     * 定时时间 ，仅针对伴我睡有效
     * 对于其他不需要定时器的地方传: -1
     * 单位分钟
     */
    private int mTime = 30;

    private List<SongInfo> mList = new ArrayList<>();

    private MusicPlayerImpl() {

    }

    public static MusicPlayerImpl getInstance() {
        return new MusicPlayerImpl();
    }

    /**
     * 开始播放
     *
     * @param list      播放音乐的列表
     * @param postition 开始播放的位置
     */
    @Override
    public void start(List<SongInfo> list, int postition) {
        mList.clear();
        this.mList = list;
        MusicManager.get().setPlayListWithIndex(mList, postition);
    }

    /**
     * 暂停
     */
    @Override
    public void pause() {
        MusicManager.get().pauseMusic();
    }

    /**
     * 继续
     */
    @Override
    public void resume() {
        MusicManager.get().resumeMusic();
    }

    /**
     * 停止播放
     */
    @Override
    public void stop() {
        MusicManager.get().stopMusic();
    }

    /**
     * 播放上一个
     */
    @Override
    public void startPrevious() {
        if (MusicManager.get().hasPre()) {
            MusicManager.get().playPre();
        }
    }

    /**
     * 播放下一个
     */
    @Override
    public void startNext() {
        if (MusicManager.get().hasNext()) {
            MusicManager.get().playNext();
        }
    }

    /**
     * 寻求指定的时间位置
     *
     * @param position
     */
    @Override
    public void seekTo(int position) {
        MusicManager.get().seekTo(position);
    }

    /**
     * 设置播放状态
     *
     * @param isListLooping
     */
    @Override
    public void setPlayMode(boolean isListLooping) {
        if (isListLooping) {
            MusicManager.get().setPlayMode(PlayMode.PLAY_IN_LIST_LOOP);
        } else {
            MusicManager.get().setPlayMode(PlayMode.PLAY_IN_SINGLE_LOOP);
        }
    }

    /**
     * 获取当前的播放状态
     */
    @Override
    public int getState() {
        return MusicManager.get().getStatus();
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    @Override
    public boolean isPlaying() {
        return MusicManager.isPlaying();
    }

    /**
     * @return 获取当前播放流的进度位置
     */
    @Override
    public long getCurrentStreamPosition() {
        return 0;
    }

    /**
     * 获取当前播放音频的id
     *
     * @return
     */
    @Override
    public int getmCurrentMediaId() {
        return MusicManager.get().getCurrPlayingIndex();
    }

    /**
     * 获取时长
     *
     * @return
     */
    @Override
    public int getDuration() {
        return MusicManager.get().getDuration();
    }

    /**
     * 设置定时时间
     *
     * @param time 定时时间，单位分钟
     */
    @Override
    public void setRemainTime(int time) {

    }
}
