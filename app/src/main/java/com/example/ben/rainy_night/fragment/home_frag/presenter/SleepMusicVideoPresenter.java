package com.example.ben.rainy_night.fragment.home_frag.presenter;

import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicVideoContract;
import com.example.ben.rainy_night.util.HttpProxyUtil;

import java.net.URLDecoder;

/**
 * @author Ben
 * @date 2018/3/29
 */

public class SleepMusicVideoPresenter implements SleepMusicVideoContract.Presenter {

    private SleepMusicVideoContract.View view;

    private String mVideoUrl;

    public SleepMusicVideoPresenter(SleepMusicVideoContract.View view) {
        this.view = view;
    }

    @Override
    public void initProxy(String videoUrl) {
        if (TextUtils.isEmpty(videoUrl)) {
            view.showToast("暂无视频数据");
            return;
        }
        HttpProxyCacheServer mServer = HttpProxyUtil.getProxy(view.getCon().getApplicationContext());
        mVideoUrl = mServer.getProxyUrl(URLDecoder.decode(videoUrl));
    }

    @Override
    public void startVideo() {
        view.getVideoView().setVideoPath(mVideoUrl);
//        view.getVideoView().setOnPreparedListener(mp -> {
//            mp.start();
//            mp.setLooping(true);
//            mp.setOnInfoListener((mp1, what, extra) -> {
//                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
//                    new Handler().postDelayed(() -> view.getImageView().setVisibility(View.GONE),500);
//                    return true;
//                }
//                return false;
//            });
//        });

        view.getVideoView().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            new Handler().postDelayed(() -> view.getImageView().setVisibility(View.GONE),500);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public void stopVideo() {
        view.getVideoView().suspend();
        view.getVideoView().stopPlayback();
    }

}
