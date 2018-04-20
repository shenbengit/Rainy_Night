package com.example.ben.rainy_night.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.listener.MusicActionListener;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.db.CacheManager;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/4/7
 */

public class MusicActionListenerImpl implements MusicActionListener {
    private Context mContext;

    /**
     * 当前播放音乐的集合
     */
    private List<SongInfo> mListCurrent;

    /**
     * 自然音乐集合
     */
    private List<SongInfo> mListNatural;
    /**
     * 轻音乐集合
     */
    private List<SongInfo> mListLight;
    /**
     * 当前播放音乐的种类
     */
    private String mMusicType;

    private MusicActionListenerImpl() {
        mListCurrent = new ArrayList<>();
        mListNatural = new ArrayList<>();
        mListLight = new ArrayList<>();
    }

    public static MusicActionListenerImpl getInstance() {
        return new MusicActionListenerImpl();
    }

    /**
     * 获取Context
     *
     * @param context
     */
    @Override
    public void initContext(Context context) {
        mContext = context;
    }

    /**
     * 获取播放音乐的数据
     *
     * @param cacheName 缓存key
     */
    @Override
    public void setData(String cacheName) {
        switch (cacheName) {
            case Constant.DOLPHIN_NATURAL_MUSIC_CACHE:
                mListNatural.clear();
                CacheEntity<MusicEntity> cache_natural = CacheManager.getInstance().get(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, MusicEntity.class);
                MusicEntity entity1;
                if (cache_natural != null) {
                    entity1 = cache_natural.getData();
                    List<MusicEntity.DataBean> beans = entity1.getData();
                    MusicEntity.DataBean bean;
                    for (int i = 0; i < beans.size(); i++) {
                        SongInfo info = new SongInfo();
                        bean = beans.get(i);
                        info.setSongId(String.valueOf(i));
                        info.setSongUrl(URLDecoder.decode(bean.getAudioUrl()));
                        info.setDownloadUrl(URLDecoder.decode(bean.getAudioUrl()));
                        info.setSongName(bean.getSceneName());
                        info.setSize(String.valueOf(bean.getAudioSize()));
                        info.setSongRectCover(bean.getCoverUrl());
                        info.setSongCover(bean.getCoverUrl());
                        mListNatural.add(info);
                    }
                }
                break;
            case Constant.DOLPHIN_LIGHT_MUSIC_CACHE:
                mListLight.clear();
                CacheEntity<MusicEntity> cache_light = CacheManager.getInstance().get(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, MusicEntity.class);
                MusicEntity entity2;
                if (cache_light != null) {
                    entity2 = cache_light.getData();
                    List<MusicEntity.DataBean> beans = entity2.getData();
                    MusicEntity.DataBean bean;
                    for (int i = 0; i < beans.size(); i++) {
                        SongInfo info = new SongInfo();
                        bean = beans.get(i);
                        info.setSongId(String.valueOf(i));
                        info.setSongUrl(URLDecoder.decode(bean.getAudioUrl()));
                        info.setDownloadUrl(URLDecoder.decode(bean.getAudioUrl()));
                        info.setSongName(bean.getSceneName());
                        info.setSize(String.valueOf(bean.getAudioSize()));
                        info.setSongRectCover(bean.getCoverUrl());
                        info.setSongCover(bean.getCoverUrl());
                        mListLight.add(info);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取当前播放音乐的类型
     *
     * @return
     */
    @Override
    public String getCurrentMusicType() {
        return mMusicType;
    }

    /**
     * 开始播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     */
    @Override
    public void start(String musicType, int position) {
        start(musicType, position, Constant.PLAY_IN_SINGLE_LOOP);
    }

    /**
     * 开始播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     * @param playMode  是否列表循环
     */
    @Override
    public void start(String musicType, int position, int playMode) {
        start(musicType, position, playMode, -1);
    }

    /**
     * 开始播放
     *
     * @param musicType  当前播放的音乐的种类
     * @param position   播放的位置
     * @param playMode   是否列表循环
     * @param remainTime 剩余时间
     */
    @Override
    public void start(String musicType, int position, int playMode, long remainTime) {
        mMusicType = musicType;
        if (TextUtils.equals(mMusicType, Constant.DOLPHIN_NATURAL_MUSIC_CACHE)) {
            if (mListNatural.isEmpty()) {
                LoggerUtil.e("MusicActionListenerImpl:自然音乐数据为空");
                return;
            }
            mListCurrent.clear();
            mListCurrent.addAll(mListNatural);
        } else if (TextUtils.equals(mMusicType, Constant.DOLPHIN_LIGHT_MUSIC_CACHE)) {
            if (mListLight.isEmpty()) {
                LoggerUtil.e("MusicActionListenerImpl:轻音乐数据为空");
                return;
            }
            mListCurrent.clear();
            mListCurrent.addAll(mListLight);
        }

        if (!mListCurrent.isEmpty()) {
            if (MusicManager.isPlaying()) {
                MusicManager.get().stopMusic();
            }
            if (remainTime != -1) {
                MusicManager.get().pausePlayInMillis(remainTime * 1000 * 60);
            }
            MusicManager.get().setPlayMode(playMode);
            MusicManager.get().setPlayListWithIndex(mListCurrent, position);
        }
    }

    /**
     * 根据索引播放
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  跳转到要播放的位置
     */
    @Override
    public void startWithIndex(String musicType, int position) {
        if (TextUtils.equals(mMusicType, musicType)) {
            if (MusicManager.get().getPlayList() != null || !MusicManager.get().getPlayList().isEmpty()) {
                MusicManager.get().playMusicByIndex(position);
            }
        }
    }

    /**
     * 暂停
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void pause(String musicType) {
        if (TextUtils.equals(mMusicType, musicType)) {
            MusicManager.get().pauseMusic();
        }
    }

    /**
     * 继续播放
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void resume(String musicType) {
        if (TextUtils.equals(mMusicType, musicType)) {
            MusicManager.get().resumeMusic();
        }
    }

    /**
     * 停止
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void stop(String musicType) {
        if (TextUtils.equals(mMusicType, musicType)) {
            MusicManager.get().stopMusic();
        }
    }

    /**
     * 播放上一个
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void startPrevious(String musicType) {
        if (TextUtils.equals(mMusicType, musicType)) {
            if (MusicManager.get().hasPre()) {
                MusicManager.get().playPre();
            } else {
                ToastUtil.show(mContext, "已经是第一首了");
            }
        }
    }

    /**
     * 播放下一个
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void startNext(String musicType) {
        if (TextUtils.equals(mMusicType, musicType)) {
            if (MusicManager.get().hasNext()) {
                MusicManager.get().playNext();
            } else {
                ToastUtil.show(mContext, "已经是最后一首了");
            }
        }
    }

    /**
     * 设置循环模式
     *
     * @param musicType 当前播放的音乐的种类
     * @param playMode  循环模式
     */
    @Override
    public void setPlayMode(String musicType, int playMode) {
        if (TextUtils.equals(mMusicType, musicType)) {
            MusicManager.get().setPlayMode(playMode);
        }
    }

    /**
     * 设置倒计时
     *
     * @param musicType  当前播放的音乐的种类
     * @param remainTime 剩余时间 单位毫秒
     */
    @Override
    public void setRemainTime(String musicType, long remainTime) {
        if (TextUtils.equals(mMusicType, musicType)) {
            MusicManager.get().pausePlayInMillis(remainTime * 1000 * 60);
        }
    }

    /**
     * 寻求指定的时间位置
     *
     * @param musicType 当前播放的音乐的种类
     * @param position  播放的位置
     */
    @Override
    public void seekTo(String musicType, int position) {
        if (TextUtils.equals(mMusicType, musicType)) {
            MusicManager.get().seekTo(position);
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
     * 获取当前播放流的进度位置
     *
     * @param musicType 当前播放的音乐的种类
     * @return
     */
    @Override
    public long getCurrentStreamPosition(String musicType) {
        return 0;
    }

    /**
     * 获取当前播放音频的id
     *
     * @param musicType 当前播放的音乐的种类
     * @return
     */
    @Override
    public int getmCurrentMediaId(String musicType) {
        if (TextUtils.equals(mMusicType, musicType)) {
            return MusicManager.get().getCurrPlayingIndex();
        }
        return -1;
    }

    /**
     * 获取当前播放音乐的信息
     *
     * @param musicType
     * @return
     */
    @Override
    public SongInfo getCurrentMediaInfo(String musicType) {
        if (TextUtils.equals(mMusicType, musicType)) {
            return MusicManager.get().getCurrPlayingMusic();
        }
        return null;
    }

    /**
     * 获取时长
     *
     * @param musicType 当前播放的音乐的种类
     * @param musicType
     * @return
     */
    @Override
    public int getDuration(String musicType) {
        if (TextUtils.equals(mMusicType, musicType)) {
            return MusicManager.get().getDuration();
        }
        return 0;
    }
}
