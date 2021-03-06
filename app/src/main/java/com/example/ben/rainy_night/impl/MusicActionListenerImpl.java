package com.example.ben.rainy_night.impl;

import android.content.Context;
import android.text.TextUtils;

import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.http.okgo.entity.SleepFmEntity;
import com.example.ben.rainy_night.listener.MusicActionListener;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.db.CacheManager;

import java.io.File;
import java.io.IOException;
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
    private String mMusicType = null;

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
            case Constant.DOLPHIN_HYPNOSIS_CACHE:
                mListHypnosis.clear();
                mListHypnosis.addAll(getFmMusicList(Constant.DOLPHIN_HYPNOSIS_CACHE));
//                LoggerUtil.e(Constant.DOLPHIN_HYPNOSIS_CACHE + ",音乐大小: " + mListHypnosis.size());
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
    public <T> void setData(String key, T entity) {
        switch (key) {
            case Constant.DOLPHIN_NATURAL_MUSIC_CACHE:
                mListNatural.clear();
                mListNatural.addAll(getMusicList(key, entity));
                break;
            case Constant.DOLPHIN_LIGHT_MUSIC_CACHE:
                mListLight.clear();
                mListLight.addAll(getMusicList(key, entity));
                break;
            case Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE:
                mListRead.clear();
                mListRead.addAll(getMusicList(key, entity));
                break;
            case Constant.DOLPHIN_NICE_PEOPLE_CACHE:
                mListNice.clear();
                mListNice.addAll(getMusicList(key, entity));
                break;
            case Constant.DOLPHIN_HYPNOSIS_CACHE:
                mListHypnosis.clear();
                mListHypnosis.addAll(getMusicList(key, entity));
                break;
            case Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE:
                mListNight.clear();
                mListNight.addAll(getMusicList(key, entity));
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
    public <T> void addData(String key, T object) {
        switch (key) {
            case Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE:
                mListRead.addAll(getMusicList(key, object));
                break;
            case Constant.DOLPHIN_NICE_PEOPLE_CACHE:
                mListNice.addAll(getMusicList(key, object));
                break;
            case Constant.DOLPHIN_HYPNOSIS_CACHE:
                mListHypnosis.addAll(getMusicList(key, object));
                break;
            case Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE:
                mListNight.addAll(getMusicList(key, object));
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
            if (MusicManager.isPlaying()) {
                MusicManager.get().stopMusic();
            }
            MusicManager.get().playMusicByIndex(position);
            return;
        }
        setCurrentMusicList(musicType);
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
     * 设置播放列表,索引默认为0
     *
     * @param musicType 当前播放的音乐的种类
     */
    @Override
    public void setPlayList(String musicType) {
        setPlayListWithIndex(musicType, 0);
    }

    /**
     * 设置播放列表，并指定索引
     *
     * @param musicType 当前播放的音乐的种类
     * @param index     索引
     */
    @Override
    public void setPlayListWithIndex(String musicType, int index) {
        setCurrentMusicList(musicType);
        if (!mListCurrent.isEmpty()) {
            MusicManager.get().setPlayListWithIndex(mListCurrent, index);
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
     */
    @Override
    public void pause() {
        MusicManager.get().pauseMusic();
    }

    /**
     * 继续播放
     */
    @Override
    public void resume() {
        MusicManager.get().resumeMusic();
    }

    /**
     * 停止
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
        } else {
            ToastUtil.show(mContext, "已经是第一首了");
        }
    }

    /**
     * 播放下一个
     */
    @Override
    public void startNext() {
        if (MusicManager.get().hasNext()) {
            MusicManager.get().playNext();
        } else {
            ToastUtil.show(mContext, "已经是最后一首了");
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
     * @param position 播放的位置
     */
    @Override
    public void seekTo(int position) {
        MusicManager.get().seekTo(position);
    }

    /**
     * 设置播放音量
     *
     * @param audioVolume audioVolume 播放音量,范围: 0f ~ 1f
     */
    @Override
    public void setVolume(float audioVolume) {
        MusicManager.get().setVolume(audioVolume);
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
     * 获取当前播放音乐的进度
     *
     * @return
     */
    @Override
    public long getProgress() {
        return MusicManager.get().getProgress();
    }

    /**
     * 获取当前播放音频的在List中的位置
     *
     * @return
     */
    @Override
    public int getCurrPlayingIndex() {
        return MusicManager.get().getCurrPlayingIndex();
    }

    /**
     * 获取当前播放音乐的信息
     *
     * @return
     */
    @Override
    public SongInfo getCurrentMediaInfo() {
        return MusicManager.get().getCurrPlayingMusic();
    }

    @Override
    public String getCacheFilePath() {
        return MusicManager.get().getCacheFilePath();
    }

    /**
     * 获取缓存目录的文件大小
     *
     * @return 文件大小, 单位为:B
     * @throws IOException
     */
    @Override
    public long getCachedFileSize() throws IOException {
        long size = 0;
        File mFile = new File(getCacheFilePath());
        if (mFile.exists()) {
            if (mFile.isDirectory()) {
                File[] list = mFile.listFiles();
                for (File file : list) {
                    if (file.isDirectory()) {
                        size = size + getDirectorySize(file);
                    } else if (file.isFile()) {
                        size = size + getFileSize(file);
                    }
                }
            } else {
                size = mFile.length();
            }
        } else {
            LoggerUtil.e("音乐缓存目录不存在，重新创建目录");
            mFile.mkdir();
        }
        return size;
    }

    /**
     * 清除已经缓存的音乐
     */
    @Override
    public void clearCachedFile() {
        File mFile = new File(getCacheFilePath());
        if (mFile.exists()) {
            if (mFile.isDirectory()) {
                File[] list = mFile.listFiles();
                for (File file : list) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else if (file.isFile()) {
                        file.delete();
                    }
                }
            } else {
                mFile.delete();
            }
        } else {
            LoggerUtil.e("清除缓存音乐目录不存在");
        }
    }

    /**
     * 判断当前的音乐是不是正在播放的音乐
     *
     * @param musicName 音乐名
     * @return
     */
    @Override
    public boolean isCurrMusicIsPlayingMusic(String musicName) {
        SongInfo info = MusicManager.get().getCurrPlayingMusic();
        return info != null && TextUtils.equals(info.getSongName(), musicName);
    }

    /**
     * 获取时长
     *
     * @return 时长 单位：秒
     */
    @Override
    public int getDuration() {
        double duration = (double) MusicManager.get().getDuration() / 1000;
        return new BigDecimal(duration).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     * 添加播放事件监听
     *
     * @param listener
     */
    @Override
    public void addPlayerEventListener(OnPlayerEventListener listener) {
        if (listener != null) {
            MusicManager.get().addPlayerEventListener(listener);
        }
    }

    /**
     * 移除播放事件监听
     *
     * @param listener
     */
    @Override
    public void removePlayerEventListener(OnPlayerEventListener listener) {
        if (listener != null) {
            MusicManager.get().removePlayerEventListener(listener);
        }
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
                info.setSongName(bean.getSceneName());
                info.setSongId(String.valueOf(bean.getSceneId()));
                if (TextUtils.equals(cacheKey, Constant.DOLPHIN_NATURAL_MUSIC_CACHE)) {
                    info.setVideoUrl(URLDecoder.decode(bean.getVideoUrl()));
                    info.setVideoPictureUrl(URLDecoder.decode(bean.getVideoPictureUrl()));
                } else if (TextUtils.equals(cacheKey, Constant.DOLPHIN_LIGHT_MUSIC_CACHE)) {
                    info.setSongPictureUrl(URLDecoder.decode(bean.getAudioPictureUrl()));
                }
                info.setSongCover(Constant.MUSIC_SQUARE_COVER);
                info.setSize(String.valueOf(bean.getAudioSize()));
                info.setSongUrl(URLDecoder.decode(bean.getAudioUrl()));
                info.setDownloadUrl(URLDecoder.decode(bean.getAudioUrl()));
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
                if (bean.getMediaName().contains("【海豚FM说晚安】")) {
                    info.setSongName(bean.getMediaName().replace("【海豚FM说晚安】", ""));
                } else if (bean.getMediaName().contains("【耐撕の人】")) {
                    info.setSongName(bean.getMediaName().replace("【耐撕の人】", ""));
                } else {
                    info.setSongName(bean.getMediaName());
                }
                info.setSongCover(Constant.MUSIC_SQUARE_COVER);
                info.setFavorites(bean.getList().get(0).getCumulativeNum());
                info.setSongId(String.valueOf(bean.getList().get(0).getMediaId()));
                info.setDuration((long) (bean.getList().get(0).getDuration() * 1000));
                info.setSongUrl(URLDecoder.decode(bean.getList().get(0).getMediaUrl()));
                info.setDownloadUrl(URLDecoder.decode(bean.getList().get(0).getMediaUrl()));
                lists.add(info);
            }
        }
        return lists;
    }

    /**
     * 获取电台音乐
     *
     * @param key    key
     * @param object 音乐数据
     * @return
     */
    private <T> List<SongInfo> getMusicList(String key, T object) {
        List<SongInfo> lists = new ArrayList<>();
        if (object instanceof MusicEntity) {
            MusicEntity entity = (MusicEntity) object;
            List<MusicEntity.DataBean> beans = entity.getData();
            if (beans != null) {
                for (MusicEntity.DataBean bean : beans) {
                    SongInfo info = new SongInfo();
                    info.setSongName(bean.getSceneName());
                    info.setSongId(String.valueOf(bean.getSceneId()));
                    if (TextUtils.equals(key, Constant.DOLPHIN_NATURAL_MUSIC_CACHE)) {
                        info.setVideoUrl(URLDecoder.decode(bean.getVideoUrl()));
                        info.setVideoPictureUrl(URLDecoder.decode(bean.getVideoUrl()));
                    } else if (TextUtils.equals(key, Constant.DOLPHIN_LIGHT_MUSIC_CACHE)) {
                        info.setSongPictureUrl(URLDecoder.decode(bean.getAudioPictureUrl()));
                    }
                    info.setSongCover(Constant.MUSIC_SQUARE_COVER);
                    info.setSize(String.valueOf(bean.getAudioSize()));
                    info.setSongUrl(URLDecoder.decode(bean.getAudioUrl()));
                    info.setDownloadUrl(URLDecoder.decode(bean.getAudioUrl()));
                    lists.add(info);
                }
            }
        } else if (object instanceof SleepFmEntity) {
            SleepFmEntity entity = (SleepFmEntity) object;
            List<SleepFmEntity.DataBean.ListBeanX> beans = entity.getData().getList();
            if (beans != null) {
                for (SleepFmEntity.DataBean.ListBeanX bean : beans) {
                    SongInfo info = new SongInfo();
                    if (bean.getMediaName().contains("【海豚FM说晚安】")) {
                        info.setSongName(bean.getMediaName().replace("【海豚FM说晚安】", ""));
                    } else if (bean.getMediaName().contains("【耐撕の人】")) {
                        info.setSongName(bean.getMediaName().replace("【耐撕の人】", ""));
                    } else {
                        info.setSongName(bean.getMediaName());
                    }
                    info.setSongCover(Constant.MUSIC_SQUARE_COVER);
                    info.setFavorites(bean.getList().get(0).getCumulativeNum());
                    info.setSongId(String.valueOf(bean.getList().get(0).getMediaId()));
                    info.setDuration((long) (bean.getList().get(0).getDuration() * 1000));
                    info.setSongUrl(URLDecoder.decode(bean.getList().get(0).getMediaUrl()));
                    info.setDownloadUrl(URLDecoder.decode(bean.getList().get(0).getMediaUrl()));
                    lists.add(info);
                }
            }
        }
        return lists;
    }

    /**
     * 获取当前播放的音乐集合
     *
     * @param musicType
     */
    private void setCurrentMusicList(String musicType) {
        mMusicType = musicType;
        mListCurrent.clear();
        switch (mMusicType) {
            case Constant.DOLPHIN_NATURAL_MUSIC_CACHE:
                if (mListNatural.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:自然音乐数据为空");
                    return;
                }
                mListCurrent.addAll(mListNatural);
                break;
            case Constant.DOLPHIN_LIGHT_MUSIC_CACHE:
                if (mListLight.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:轻音乐数据为空");
                    return;
                }
                mListCurrent.addAll(mListLight);
                break;
            case Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE:
                if (mListRead.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:睡前伴读数据为空");
                    return;
                }
                mListCurrent.addAll(mListRead);
                break;
            case Constant.DOLPHIN_NICE_PEOPLE_CACHE:
                if (mListNice.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:耐撕の人数据为空");
                    return;
                }
                mListCurrent.addAll(mListNice);
                break;
            case Constant.DOLPHIN_HYPNOSIS_CACHE:
                if (mListHypnosis.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:催眠ASMR数据为空");
                    return;
                }
                mListCurrent.addAll(mListHypnosis);
                break;
            case Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE:
                if (mListNight.isEmpty()) {
                    LoggerUtil.e("MusicActionListenerImpl:说晚安数据为空");
                    return;
                }
                mListCurrent.addAll(mListNight);
                break;
            default:
                LoggerUtil.e("MusicActionListenerImpl: start()音乐类型错误，" + musicType);
                break;
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param file
     * @return
     */
    private long getDirectorySize(File file) {
        long size = 0;
        File[] mFile = file.listFiles();
        for (File f : mFile) {
            if (f.isDirectory()) {
                size = size + getDirectorySize(f);
            } else if (f.isFile()) {
                size = size + getFileSize(f);
            }
        }
        return size;
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    private long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            size = file.length();
        }
        return size;
    }

    /**
     * 删除目录文件
     *
     * @param file
     */
    private void deleteDirectory(File file) {
        File[] list = file.listFiles();
        for (File f : list) {
            if (f.isDirectory()) {
                deleteDirectory(f);
            } else if (f.isFile()) {
                f.delete();
            }
        }
    }
}
