package com.example.ben.rainy_night.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.event.OnMusicDataTypeEvent;
import com.example.ben.rainy_night.fragment.event.OnMusicPlayerEvent;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.player.manager.MusicPlayerManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.db.CacheManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static com.vondear.rxtools.view.progressing.animation.AnimationUtils.stop;

/**
 * @author Ben
 * @date 2018/4/3
 */

public class MusicPlayerService extends Service {
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

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        mListNatural = new ArrayList<>();
        mListLight = new ArrayList<>();

        MusicPlayerManager.getInstance().initContext(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 先加载音乐数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void setMusicData(OnMusicDataTypeEvent event) {
        switch (event.getMusicType()) {
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
     * 设置音乐动作
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void setMusicPlayer(OnMusicPlayerEvent event) {
        if (TextUtils.isEmpty(event.getMusicAction())) {
            return;
        }
        LoggerUtil.e("音乐动作：" + event.getMusicAction() + ",音乐类型: " + event.getMusicType());
        switch (event.getMusicAction()) {
            case Constant.MUSIC_START:
                mMusicType = event.getMusicType();
                if (TextUtils.equals(mMusicType, Constant.DOLPHIN_NATURAL_MUSIC_CACHE)) {
                    LoggerUtil.e("自然音乐长度：" + mListNatural.size());
                    if (mListNatural.isEmpty()) {
                        LoggerUtil.e("自然音乐暂未加载");
                        return;
                    }
                    MusicPlayerManager.getInstance().setPlayMode(false);
                    MusicPlayerManager.getInstance().start(mListNatural, event.getPosition());
                } else if (TextUtils.equals(mMusicType, Constant.DOLPHIN_LIGHT_MUSIC_CACHE)) {
                    LoggerUtil.e("轻音乐长度：" + mListLight.size());
                    if (mListLight.isEmpty()) {
                        LoggerUtil.e("轻音乐暂未加载");
                        return;
                    }
                    MusicPlayerManager.getInstance().setPlayMode(false);
                    MusicPlayerManager.getInstance().start(mListLight, event.getPosition());
                }
                break;
            case Constant.MUSIC_PAUSE:
                if (TextUtils.equals(mMusicType, event.getMusicType())) {
                    MusicPlayerManager.getInstance().pause();
                }
                break;
            case Constant.MUSIC_RESUME:
                if (TextUtils.equals(mMusicType, event.getMusicType())) {
                    MusicPlayerManager.getInstance().resume();
                }
                break;
            case Constant.MUSIC_STOP:
                if (TextUtils.equals(mMusicType, event.getMusicType())) {
                    MusicPlayerManager.getInstance().stop();
                }
                break;
            case Constant.MUSIC_PREVIOUS:
                if (TextUtils.equals(mMusicType, event.getMusicType())) {
                    MusicPlayerManager.getInstance().startPrevious();
                }
                break;
            case Constant.MUSIC_NEXT:
                if (TextUtils.equals(mMusicType, event.getMusicType())) {
                    MusicPlayerManager.getInstance().startNext();
                }
                break;
            case Constant.MUSIC_SET_CYCLE_MODE:
                if (TextUtils.equals(mMusicType, event.getMusicType())) {
                    MusicPlayerManager.getInstance().startPrevious();
                }
                break;
            case Constant.MUSIC_SET_TIME:
                if (TextUtils.equals(mMusicType, event.getMusicType())) {
                    MusicPlayerManager.getInstance().setRemainTime((int) event.getRemainTime());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 开始播放
     */
    private void start() {

    }

    /**
     * 继续播放
     */
    private void resume() {

    }

    /**
     * 暂停
     */
    private void pause() {

    }

    /**
     * 播放上一个
     */
    private void previous() {

    }

    /**
     * 播放下一个
     */
    private void next() {

    }

    /**
     * 设置循环模式
     */
    private void setCycleMode() {

    }

    /**
     * 设置定时时间
     */
    private void setTime() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
