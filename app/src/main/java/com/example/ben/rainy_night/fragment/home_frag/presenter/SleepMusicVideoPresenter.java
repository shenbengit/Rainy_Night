package com.example.ben.rainy_night.fragment.home_frag.presenter;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.View;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicVideoContract;
import com.example.ben.rainy_night.util.HttpProxyUtil;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.widget.FullScreenVideoView;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author Ben
 * @date 2018/3/29
 */

public class SleepMusicVideoPresenter implements SleepMusicVideoContract.Presenter, SurfaceHolder.Callback {

    private SleepMusicVideoContract.View view;

    private HttpProxyCacheServer mServer;

    private MediaPlayer mPlayer;
    private SurfaceHolder mHolder;

    private boolean isInitPlayer = false;

    private String mVideoUrl;

    public SleepMusicVideoPresenter(SleepMusicVideoContract.View view) {
        this.view = view;
        mPlayer = new MediaPlayer();
        mHolder = view.getVideoView().getHolder();
        mHolder.addCallback(this);

    }


    @Override
    public void initProxy(String videoUrl) {
        if (TextUtils.isEmpty(videoUrl)) {
            view.showToast("暂无视频数据");
            return;
        }
        mServer = HttpProxyUtil.getProxy(view.getCon().getApplicationContext());
        mVideoUrl = mServer.getProxyUrl(URLDecoder.decode(videoUrl));
    }

    @Override
    public void startVideo() {
        LoggerUtil.e("startVideo");

        if (mPlayer != null && !isInitPlayer) {
            try {
                mPlayer.reset();
                mPlayer.setDataSource(mVideoUrl);
                mPlayer.setLooping(true);
                mPlayer.prepareAsync();
                mPlayer.setOnPreparedListener(mp -> {
                    mp.start();
                    isInitPlayer = true;
                    LoggerUtil.e("视频播放");
                    new Handler().postDelayed(() -> view.getImageView().setVisibility(View.GONE), 500);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mPlayer != null && isInitPlayer && !mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }


    @Override
    public void pauseVideo() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    @Override
    public void stopVideo() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
            }
            mPlayer.reset();
            mPlayer.release();
            mPlayer = null;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LoggerUtil.e("surfaceCreated");
        if (isInitPlayer) {
            return;
        }
        if (mPlayer != null) {
            mPlayer.setDisplay(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LoggerUtil.e("surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mPlayer != null) {
            LoggerUtil.e("surfaceDestroyed");
            mPlayer.reset();

            mPlayer.release();
            mPlayer = null;
        }
    }
}
