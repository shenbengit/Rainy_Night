package com.example.ben.rainy_night.fragment.home_frag.presenter;

import com.danikula.videocache.HttpProxyCacheServer;
import com.devbrackets.android.exomedia.core.video.scale.ScaleType;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicVideoContract;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.GsonUtil;
import com.example.ben.rainy_night.util.HttpProxyUtil;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.db.CacheManager;

import java.net.URLDecoder;

/**
 * @author Ben
 * @date 2018/3/29
 */

public class SleepMusicVideoPresenterImpl implements SleepMusicVideoContract.Presenter, OnPlayerEventListener {

    private SleepMusicVideoContract.View view;
    private HttpProxyCacheServer mServer;
    private MusicEntity mEntity;
    private int mPosition;
    private boolean isChanged = false;

    public SleepMusicVideoPresenterImpl(SleepMusicVideoContract.View view) {
        this.view = view;
    }

    @Override
    public void init(int position) {
        mServer = HttpProxyUtil.getProxy(view.getCon().getApplicationContext());
        mPosition = position;
        mEntity = GsonUtil.fromJson(String.valueOf(SharedPreferencesUtil.getInstance(view.getCon().getApplicationContext()).getValue(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, "")), MusicEntity.class);
        MusicActionManager.getInstance().addPlayerEventListener(this);
        MusicActionManager.getInstance().setVolume(0.4f);
        MusicActionManager.getInstance().setPlayMode(Constant.PLAY_IN_SINGLE_LOOP);
        MusicActionManager.getInstance().setPlayList(Constant.DOLPHIN_NATURAL_MUSIC_CACHE);
    }

    @Override
    public void startVideo() {
        start(mPosition);
    }

    @Override
    public void onSupportVisible() {
        MusicActionManager.getInstance().pause();
        if (isChanged) {
            start(mPosition);
            return;
        }
        if (!view.getVideoView().isPlaying()) {
            view.getVideoView().start();
        }
    }

    @Override
    public void onSupportInVisible() {
        if (view.getVideoView().isPlaying()) {
            view.getVideoView().pause();
        }
        if (!MusicActionManager.getInstance().isCurrMusicIsPlayingMusic(mEntity.getData().get(mPosition).getSceneName())) {
            MusicActionManager.getInstance().startWithIndex(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, mPosition);
            MusicActionManager.getInstance().seekTo((int) view.getVideoView().getCurrentPosition());
        } else {
            MusicActionManager.getInstance().resume();
        }
    }

    @Override
    public void onDestroy() {
        view.getVideoView().release();
        MusicActionManager.getInstance().removePlayerEventListener(this);
    }

    @Override
    public void startPrevious() {
        if (mPosition == 0) {
            view.showToast("已经是第一个了哦");
            return;
        }
        isChanged = false;
        view.getVideoView().stopPlayback();
        mPosition--;
        start(mPosition);
    }

    @Override
    public void startNext() {
        if (mPosition == mEntity.getData().size() - 1) {
            view.showToast("已经是最后一个了哦");
            return;
        }
        isChanged = false;
        view.getVideoView().stopPlayback();
        mPosition++;
        start(mPosition);
    }

    /**
     * 获取代理地址
     *
     * @param videoUrl 视频地址
     * @return 代理地址
     */
    private String getProxyUrl(String videoUrl) {
        return mServer.getProxyUrl(URLDecoder.decode(videoUrl));
    }

    /**
     * 视频播放
     *
     * @param position
     */
    private void start(int position) {
        if (view.getVideoView().isPlaying()) {
            view.getVideoView().pause();
        }
        view.getVideoView().setScaleType(ScaleType.FIT_XY);
        GlideApp.with(view.getCon()).load(mEntity.getData().get(position).getVideoPictureUrl()).into(view.getVideoView().getPreviewImageView());
        view.getVideoView().setVideoPath(getProxyUrl(mEntity.getData().get(position).getVideoUrl()));
        //循环模式 ：单曲循环
        view.getVideoView().setRepeatMode(2);
        view.getVideoView().setOnPreparedListener(() -> view.getVideoView().start());
    }

    @Override
    public void onMusicSwitch(SongInfo music) {
        if (MusicActionManager.getInstance().getCurrPlayingIndex() != mPosition) {
            mPosition = MusicActionManager.getInstance().getCurrPlayingIndex();
            isChanged = true;
        }
    }

    @Override
    public void onPlayerStart() {

    }

    @Override
    public void onPlayerPause() {

    }

    @Override
    public void onPlayCompletion() {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onAsyncLoading(boolean isFinishLoading) {

    }
}
