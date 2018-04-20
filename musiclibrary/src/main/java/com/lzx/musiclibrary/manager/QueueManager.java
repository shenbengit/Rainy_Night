package com.lzx.musiclibrary.manager;

import android.graphics.Bitmap;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;

import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.constans.PlayMode;
import com.lzx.musiclibrary.helper.QueueHelper;
import com.lzx.musiclibrary.utils.AlbumArtCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xian on 2018/1/20.
 */

public class QueueManager {

    private List<SongInfo> mPlayingQueue;
    private int mCurrentIndex;
    private MetadataUpdateListener mListener;
    private PlayMode mPlayMode;

    public QueueManager(MetadataUpdateListener listener, PlayMode playMode) {
        mPlayingQueue = Collections.synchronizedList(new ArrayList<SongInfo>());
        mCurrentIndex = 0;
        mListener = listener;
        mPlayMode = playMode;
    }

    public void setListener(MetadataUpdateListener listener) {
        mListener = listener;
    }

    /**
     * 获取播放列表
     *
     * @return
     */
    public List<SongInfo> getPlayingQueue() {
        return mPlayingQueue;
    }

    /**
     * 获取当前索引
     *
     * @return
     */
    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    /**
     * 设置当前的播放列表
     *
     * @param newQueue     整个队列
     * @param currentIndex 当前第几首
     */
    public void setCurrentQueue(List<SongInfo> newQueue, int currentIndex) {
        int index = 0;
        if (currentIndex != -1) {
            index = currentIndex;
        }
        mCurrentIndex = Math.max(index, 0);
        mPlayingQueue.clear();

        mPlayingQueue.addAll(newQueue);

        //通知播放列表更新了
        List<MediaSessionCompat.QueueItem> queueItems = QueueHelper.getQueueItems(mPlayingQueue);
        if (mListener != null) {
            mListener.onQueueUpdated(queueItems, mPlayingQueue);
        }
    }

    /**
     * 设置当前的播放列表 默认第一首
     *
     * @param newQueue 整个队列
     */
    public void setCurrentQueue(List<SongInfo> newQueue) {
        setCurrentQueue(newQueue, -1);
    }

    /**
     * 添加一个音乐信息到队列中
     *
     * @param info 音乐信息
     */
    public void addQueueItem(SongInfo info) {
        if (mPlayingQueue.contains(info)) {
            return;
        }
        mPlayingQueue.add(info);
        //通知播放列表更新了
        List<MediaSessionCompat.QueueItem> queueItems = QueueHelper.getQueueItems(mPlayingQueue);
        if (mListener != null) {
            mListener.onQueueUpdated(queueItems, mPlayingQueue);
        }
    }

    public void deleteQueueItem(SongInfo info, boolean isNeedToPlayNext) {
        if (mPlayingQueue.size() == 0) {
            return;
        }
        if (!mPlayingQueue.contains(info)) {
            return;
        }
        mPlayingQueue.remove(info);

        List<MediaSessionCompat.QueueItem> queueItems = QueueHelper.getQueueItems(mPlayingQueue);
        if (mListener != null) {
            mListener.onQueueUpdated(queueItems, mPlayingQueue);
            //播放下一首
            if (isNeedToPlayNext) {
                mListener.onCurrentQueueIndexUpdated(mCurrentIndex, false, true);
            }
        }
    }

    /**
     * 得到列表长度
     *
     * @return 队列长度
     */
    public int getCurrentQueueSize() {
        if (mPlayingQueue == null) {
            return 0;
        }
        return mPlayingQueue.size();
    }

    /**
     * 得到当前播放的音乐信息
     *
     * @return 音乐信息
     */
    public SongInfo getCurrentMusic() {
        if (!QueueHelper.isIndexPlayable(mCurrentIndex, mPlayingQueue)) {
            return null;
        } else {
            return mPlayingQueue.get(mCurrentIndex);
        }
    }

    public void setCurrentMusic(int currentIndex) {
        if (mPlayingQueue.size() == 0) {
            return;
        }
        if (!QueueHelper.isIndexPlayable(currentIndex, mPlayingQueue)) {
            return;
        }
        this.mCurrentIndex = currentIndex;
    }

    /**
     * 转跳到指定位置
     *
     * @param amount 维度
     * @return
     */
    public boolean skipQueuePosition(int amount) {
        int index = mCurrentIndex + amount;
        if (index < 0) {
            // 在第一首歌曲之前向后跳，让你在第一首歌曲上
            index = 0;
        } else {
            //当在最后一首歌时点下一首将返回第一首个
            index %= mPlayingQueue.size();
        }
        if (!QueueHelper.isIndexPlayable(index, mPlayingQueue)) {
            return false;
        }
        mCurrentIndex = index;
        return true;
    }

    /**
     * 更新音乐艺术家信息
     *
     * @param musicId
     * @param bitmap
     * @param icon
     */
    public void updateSongCoverBitmap(String musicId, Bitmap bitmap, Bitmap icon) {
        SongInfo musicInfo = QueueHelper.getMusicInfoById(mPlayingQueue, musicId);
        if (musicInfo == null) {
            return;
        }
        musicInfo.setSongCoverBitmap(bitmap);
        int index = mPlayingQueue.indexOf(musicInfo);
        mPlayingQueue.set(index, musicInfo);
    }

    /**
     * 设置当前的音乐item，用于播放
     *
     * @param musicId 音乐id
     * @return
     */
    public void setCurrentQueueItem(String musicId, boolean isJustPlay, boolean isSwitchMusic) {
        int index = QueueHelper.getMusicIndexOnQueue(mPlayingQueue, musicId);
        setCurrentQueueIndex(index, isJustPlay, isSwitchMusic);
    }

    /**
     * 设置当前的音乐item，用于播放
     *
     * @param index 队列下标
     */
    private void setCurrentQueueIndex(int index, boolean isJustPlay, boolean isSwitchMusic) {
        if (index >= 0 && index < mPlayingQueue.size()) {
            mCurrentIndex = index;
            if (mListener != null) {
                mListener.onCurrentQueueIndexUpdated(mCurrentIndex, isJustPlay, isSwitchMusic);
            }
        }
    }

    /**
     * 得到上一首音乐信息
     */
    public SongInfo getPreMusicInfo() {
        return getNextOrPreMusicInfo(-1);
    }

    /**
     * 得到下一首音乐信息
     */
    public SongInfo getNextMusicInfo() {
        return getNextOrPreMusicInfo(1);
    }

    private SongInfo getNextOrPreMusicInfo(int amount) {
        SongInfo info = null;
        switch (mPlayMode.getCurrPlayMode()) {
            //单曲循环
            case PlayMode.PLAY_IN_SINGLE_LOOP:
                info = getCurrentMusic();
                break;
            //随机播放
            case PlayMode.PLAY_IN_RANDOM:
                //0到size-1的随机数
                int random = (int) (Math.random() * getCurrentQueueSize() - 1);
                if (skipQueuePosition(random)) {
                    info = getCurrentMusic();
                }
                break;
            //列表循环
            case PlayMode.PLAY_IN_LIST_LOOP:
                if (skipQueuePosition(amount)) {
                    info = getCurrentMusic();
                }
                break;
            //顺序播放
            case PlayMode.PLAY_IN_ORDER:
                if (amount == 1) {
                    if (mCurrentIndex != mPlayingQueue.size() - 1) {
                        if (skipQueuePosition(amount)) {
                            info = getCurrentMusic();
                        }
                    } else {
                        info = null;
                    }
                } else if (amount == -1) {
                    if (mCurrentIndex != 0) {
                        if (skipQueuePosition(amount)) {
                            info = getCurrentMusic();
                        }
                    } else {
                        info = null;
                    }
                }
                break;
            default:
                info = null;
                break;
        }
        return info;
    }

    /**
     * 更新媒体信息
     */
    public void updateMetadata() {
        SongInfo currentMusic = getCurrentMusic();
        if (currentMusic == null) {
            if (mListener != null) {
                mListener.onMetadataRetrieveError();
            }
            return;
        }
        final String musicId = currentMusic.getSongId();
        SongInfo metadata = QueueHelper.getMusicInfoById(mPlayingQueue, musicId);
        if (metadata == null) {
            throw new IllegalArgumentException("Invalid musicId " + musicId);
        }
        if (!TextUtils.isEmpty(metadata.getSongCover())) {
            String coverUri = metadata.getSongCover();
            //获取图片bitmap
            AlbumArtCache.getInstance().fetch(coverUri, new AlbumArtCache.FetchListener() {
                @Override
                public void onFetched(String artUrl, Bitmap bitmap, Bitmap icon) {
                    updateSongCoverBitmap(musicId, bitmap, icon);
                    SongInfo currentMusic = getCurrentMusic();
                    if (currentMusic == null) {
                        return;
                    }
                    String currentPlayingId = currentMusic.getSongId();
                    if (musicId.equals(currentPlayingId)) {
                        if (mListener != null) {
                            mListener.onMetadataChanged(QueueHelper.getMusicInfoById(mPlayingQueue, currentPlayingId));
                        }
                    }
                }
            });
        }
    }


    public interface MetadataUpdateListener {
        void onMetadataChanged(SongInfo metadata);

        void onMetadataRetrieveError();

        void onCurrentQueueIndexUpdated(int queueIndex, boolean isJustPlay, boolean isSwitchMusic);

        void onQueueUpdated(List<MediaSessionCompat.QueueItem> newQueue, List<SongInfo> playingQueue);
    }


}
