package com.example.ben.rainy_night.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.ben.rainy_night.http.bmob.entity.SleepMusicEntity;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.http.okgo.entity.SleepFmEntity;
import com.example.ben.rainy_night.listener.MusicActionListener;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.db.CacheManager;

import java.math.BigDecimal;
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
     * 睡前伴读集合
     */
    private List<SongInfo> mListRead;
    /**
     * 催眠ASMR集合
     */
    private List<SongInfo> mListHypnosis;
    /**
     * 耐撕の人集合
     */
    private List<SongInfo> mListNice;
    /**
     * 说晚安集合
     */
    private List<SongInfo> mListNight;
    /**
     * 当前播放音乐的种类
     */
    private String mMusicType;

    private MusicActionListenerImpl() {
        mListCurrent = new ArrayList<>();
        mListNatural = new ArrayList<>();
        mListLight = new ArrayList<>();
        mListRead = new ArrayList<>();
        mListHypnosis = new ArrayList<>();
        mListNice = new ArrayList<>();
        mListNight = new ArrayList<>();
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
                mListNatural.addAll(getNaturalOrLightMusic(Constant.DOLPHIN_NATURAL_MUSIC_CACHE));
//                LoggerUtil.e(Constant.DOLPHIN_NATURAL_MUSIC_CACHE + ",音乐大小: " + mListNatural.size());
                break;
            case Constant.DOLPHIN_LIGHT_MUSIC_CACHE:
                mListLight.clear();
                mListLight.addAll(getNaturalOrLightMusic(Constant.DOLPHIN_LIGHT_MUSIC_CACHE));
//                LoggerUtil.e(Constant.DOLPHIN_LIGHT_MUSIC_CACHE + ",音乐大小: " + mListLight.size());
                break;
            case Constant.DOLPHIN_HYPNOSIS_CACHE:
                mListHypnosis.clear();
                mListHypnosis.addAll(getFmMusicList(Constant.DOLPHIN_HYPNOSIS_CACHE));
//                LoggerUtil.e(Constant.DOLPHIN_HYPNOSIS_CACHE + ",音乐大小: " + mListHypnosis.size());
                break;
            case Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE:
                mListRead.clear();
                mListRead.addAll(getFmMusicList(Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE));
//                LoggerUtil.e(Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE + ",音乐大小: " + mListRead.size());
                break;
            case Constant.DOLPHIN_NICE_PEOPLE_CACHE:
                mListNice.clear();
                mListNice.addAll(getFmMusicList(Constant.DOLPHIN_NICE_PEOPLE_CACHE));
//                LoggerUtil.e(Constant.DOLPHIN_NICE_PEOPLE_CACHE + ",音乐大小: " + mListNice.size());
                break;
            case Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE:
                mListNight.clear();
                mListNight.addAll(getFmMusicList(Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE));
//                LoggerUtil.e(Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE + ",音乐大小: " + mListNight.size());
                break;
            default:
                LoggerUtil.e("MusicActionListenerImpl: setData()音乐类型错误，cacheName: " + cacheName);
                break;
        }
    }

    /**
     * 获取播放音乐的数据
     *
     * @param key    key
     * @param entity 音乐数据
     */
    @Override
    public void setData(String key, SleepFmEntity entity) {
        switch (key) {
            case Constant.DOLPHIN_HYPNOSIS_CACHE:
                mListHypnosis.clear();
                mListHypnosis.addAll(getFmMusicList(entity));
                break;
            case Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE:
                mListRead.clear();
                mListRead.addAll(getFmMusicList(entity));
                break;
            case Constant.DOLPHIN_NICE_PEOPLE_CACHE:
                mListNice.clear();
                mListNice.addAll(getFmMusicList(entity));
                break;
            case Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE:
                mListNight.clear();
                mListNight.addAll(getFmMusicList(entity));
                break;
            default:
                LoggerUtil.e("MusicActionListenerImpl: setData()音乐类型错误，key: " + key);
                break;
        }
    }

    /**
     * 追加音乐数据
     *
     * @param key    key
     * @param object 追加的音乐数据
     */
    @Override
    public void addData(String key, Object object) {
        switch (key) {
            case Constant.DOLPHIN_HYPNOSIS_CACHE:
                mListHypnosis.addAll(getFmMusicList(object));
                break;
            case Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE:
                mListRead.addAll(getFmMusicList(object));
                break;
            case Constant.DOLPHIN_NICE_PEOPLE_CACHE:
                mListNice.addAll(getFmMusicList(object));
                break;
            case Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE:
                mListNight.addAll(getFmMusicList(object));
                break;
            default:
                LoggerUtil.e("MusicActionListenerImpl: addData()音乐类型错误，key: " + key);
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
        start(musicType, position, -1);
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
        if (TextUtils.equals(mMusicType, musicType) && !mListCurrent.isEmpty()) {
            MusicManager.get().playMusicByIndex(position);
            return;
        }
        mMusicType = musicType;
        switch (mMusicType) {
            case Constant.DOLPHIN_NATURAL_MUSIC_CACHE:
                if (mListNatural.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:自然音乐数据为空");
                    return;
                }
                setCurrentMusicList(mListNatural);
                break;
            case Constant.DOLPHIN_LIGHT_MUSIC_CACHE:
                if (mListLight.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:轻音乐数据为空");
                    return;
                }
                setCurrentMusicList(mListLight);
                break;
            case Constant.DOLPHIN_HYPNOSIS_CACHE:
                if (mListHypnosis.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:催眠ASMR数据为空");
                    return;
                }
                setCurrentMusicList(mListHypnosis);
                break;
            case Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE:
                if (mListRead.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:睡前伴读数据为空");
                    return;
                }
                setCurrentMusicList(mListRead);
                break;
            case Constant.DOLPHIN_NICE_PEOPLE_CACHE:
                if (mListNice.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:耐撕の人数据为空");
                    return;
                }
                setCurrentMusicList(mListNice);
                break;
            case Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE:
                if (mListNight.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:说晚安数据为空");
                    return;
                }
                setCurrentMusicList(mListNight);
                break;
            default:
                LoggerUtil.e("MusicActionListenerImpl: start()音乐类型错误，" + musicType);
                break;
        }

        if (!mListCurrent.isEmpty()) {
            if (MusicManager.isPlaying()) {
                MusicManager.get().stopMusic();
            }
            if (remainTime > 0) {
                MusicManager.get().pausePlayInMillis(remainTime * 1000 * 60);
            }
            if (playMode > 0) {
                MusicManager.get().setPlayMode(playMode);
            }
            MusicManager.get().playMusic(mListCurrent, position);
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
     * @param playMode 循环模式
     */
    @Override
    public void setPlayMode(int playMode) {
        MusicManager.get().setPlayMode(playMode);
    }

    /**
     * 设置倒计时
     *
     * @param remainTime 剩余时间 单位毫秒
     */
    @Override
    public void setRemainTime(long remainTime) {
        MusicManager.get().pausePlayInMillis(remainTime * 1000 * 60);
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
     * 获取当前循环模式
     *
     * @return
     */
    @Override
    public int getPlayMode() {
        return MusicManager.get().getPlayMode();
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
     * @return 时长 单位：秒
     */
    @Override
    public int getDuration(String musicType) {
        if (TextUtils.equals(mMusicType, musicType)) {
            double duration = (double) MusicManager.get().getDuration() / 1000;
            return new BigDecimal(duration).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        }
        return 0;
    }

    /**
     * 获取自然音符或轻音乐
     *
     * @param cacheKey 缓存名
     * @return 音乐集合
     */
    private List<SongInfo> getNaturalOrLightMusic(String cacheKey) {
        List<SongInfo> lists = new ArrayList<>();
        CacheEntity<MusicEntity> cache = CacheManager.getInstance().get(cacheKey, MusicEntity.class);
        if (cache != null) {
            MusicEntity entity = cache.getData();
            List<MusicEntity.DataBean> beans = entity.getData();
            for (MusicEntity.DataBean bean : beans) {
                SongInfo info = new SongInfo();
                info.setSongId(String.valueOf(bean.getSceneId()));
                info.setSongUrl(URLDecoder.decode(bean.getAudioUrl()));
                info.setDownloadUrl(URLDecoder.decode(bean.getAudioUrl()));
                info.setSongName(bean.getSceneName());
                info.setSize(String.valueOf(bean.getAudioSize()));
                lists.add(info);
            }
        }
        return lists;
    }

    /**
     * 获取电台音乐
     *
     * @param cacheKey 缓存名
     * @return
     */
    private List<SongInfo> getFmMusicList(String cacheKey) {
        List<SongInfo> lists = new ArrayList<>();
        CacheEntity<SleepFmEntity> cache = CacheManager.getInstance().get(cacheKey, SleepFmEntity.class);
        if (cache != null) {
            SleepFmEntity entity = cache.getData();
            List<SleepFmEntity.DataBean.ListBeanX> beans = entity.getData().getList();
            for (SleepFmEntity.DataBean.ListBeanX bean : beans) {
                SongInfo info = new SongInfo();
                info.setSongId(String.valueOf(bean.getList().get(0).getMediaId()));
                info.setSongUrl(URLDecoder.decode(bean.getList().get(0).getMediaUrl()));
                info.setDownloadUrl(URLDecoder.decode(bean.getList().get(0).getMediaUrl()));
                info.setFavorites(bean.getList().get(0).getCumulativeNum());
                info.setDuration((long) (bean.getList().get(0).getDuration() * 1000));
                lists.add(info);
            }
        }
        return lists;
    }

    /**
     * 获取电台音乐
     *
     * @param bean 音乐数据
     * @return
     */
    private <T> List<SongInfo> getFmMusicList(T bean) {
        List<SongInfo> lists = new ArrayList<>();
        if (bean instanceof MusicEntity) {
            MusicEntity entity = (MusicEntity) bean;
            List<MusicEntity.DataBean> beans = entity.getData();
            if (beans != null) {
                for (MusicEntity.DataBean dataBean : beans) {
                    SongInfo info = new SongInfo();
                    info.setSongId(String.valueOf(dataBean.getSceneId()));
                    info.setSongUrl(URLDecoder.decode(dataBean.getAudioUrl()));
                    info.setDownloadUrl(URLDecoder.decode(dataBean.getAudioUrl()));
                    info.setSongName(dataBean.getSceneName());
                    info.setSize(String.valueOf(dataBean.getAudioSize()));
                    lists.add(info);
                }
            }
        } else if (bean instanceof SleepFmEntity) {
            SleepFmEntity entity = (SleepFmEntity) bean;
            List<SleepFmEntity.DataBean.ListBeanX> beans = entity.getData().getList();
            if (beans != null) {
                for (SleepFmEntity.DataBean.ListBeanX beanX : beans) {
                    SongInfo info = new SongInfo();
                    info.setSongId(String.valueOf(beanX.getList().get(0).getMediaId()));
                    info.setSongUrl(URLDecoder.decode(beanX.getList().get(0).getMediaUrl()));
                    info.setDownloadUrl(URLDecoder.decode(beanX.getList().get(0).getMediaUrl()));
                    info.setFavorites(beanX.getList().get(0).getCumulativeNum());
                    info.setDuration((long) (beanX.getList().get(0).getDuration() * 1000));
                    lists.add(info);
                }
            }
        }
        return lists;
    }

    /**
     * 获取当前播放的音乐集合
     *
     * @param list
     */
    private void setCurrentMusicList(List<SongInfo> list) {
        mListCurrent.clear();
        mListCurrent.addAll(list);
    }
}
