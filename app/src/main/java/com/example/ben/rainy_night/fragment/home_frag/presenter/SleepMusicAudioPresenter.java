package com.example.ben.rainy_night.fragment.home_frag.presenter;

import android.text.TextUtils;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicAudioContract;
import com.example.ben.rainy_night.util.HttpProxyUtil;

/**
 * @author Ben
 * @date 2018/3/29
 */

public class SleepMusicAudioPresenter implements SleepMusicAudioContract.Presenter {

    private SleepMusicAudioContract.View view;

    private HttpProxyCacheServer mServer;
    private String mVideoUrl;

    public SleepMusicAudioPresenter(SleepMusicAudioContract.View view) {
        this.view = view;
    }


    @Override
    public void initProxy(String videoUrl, String audioUrl) {
        if (TextUtils.isEmpty(videoUrl)) {
            view.showToast("暂无视频数据");
            return;
        }
        mServer = HttpProxyUtil.getProxy(view.getCon());
        mVideoUrl=mServer.getProxyUrl(videoUrl);
    }

    @Override
    public void startAudio() {
        view.getVideo().setVideoPath(mVideoUrl);
        view.getVideo().setOnPreparedListener(mp -> view.getVideo().start());
        view.getVideo().setOnCompletionListener(mediaPlayer -> {
            view.getVideo().setVideoPath(mVideoUrl);
            view.getVideo().start();
        });
    }
}
