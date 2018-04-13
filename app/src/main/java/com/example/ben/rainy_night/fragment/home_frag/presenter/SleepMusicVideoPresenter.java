package com.example.ben.rainy_night.fragment.home_frag.presenter;

import com.danikula.videocache.HttpProxyCacheServer;
import com.devbrackets.android.exomedia.core.video.scale.ScaleType;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicVideoContract;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.util.HttpProxyUtil;

import java.net.URLDecoder;

/**
 * @author Ben
 * @date 2018/3/29
 */

public class SleepMusicVideoPresenter implements SleepMusicVideoContract.Presenter {

    private SleepMusicVideoContract.View view;
    private HttpProxyCacheServer mServer;
    private MusicEntity mEntity;
    private int mPosition;

    public SleepMusicVideoPresenter(SleepMusicVideoContract.View view) {
        this.view = view;
    }

    @Override
    public void initProxy(int position) {
        mServer = HttpProxyUtil.getProxy(view.getCon().getApplicationContext());
        mPosition = position;
        mEntity = view.getEntity();
    }

    @Override
    public void startVideo() {
        start(mPosition);
    }

    @Override
    public void resumeVideo() {
        if (!view.getVideoView().isPlaying()) {
            view.getVideoView().start();
        }
    }

    @Override
    public void pauseVideo() {
        if (view.getVideoView().isPlaying()) {
            view.getVideoView().pause();
        }
    }

    @Override
    public void stopVideo() {
        view.getVideoView().release();
    }

    @Override
    public void startPrevious() {
        if (mPosition == 0) {
            view.showToast("已经是第一个了哦");
            return;
        }
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
     * 播放
     *
     * @param position
     */
    private void start(int position) {
        view.getVideoView().setScaleType(ScaleType.FIT_XY);
        GlideApp.with(view.getCon()).load(mEntity.getData().get(position).getVideoPictureUrl()).into(view.getVideoView().getPreviewImageView());
        view.getVideoView().setVideoPath(getProxyUrl(mEntity.getData().get(position).getVideoUrl()));
        view.getVideoView().setRepeatMode(2);
        view.getVideoView().setOnPreparedListener(() -> view.getVideoView().start());
    }
}
