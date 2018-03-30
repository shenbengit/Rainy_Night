package com.example.ben.rainy_night.fragment.home_frag.contract;

import android.content.Context;
import android.widget.VideoView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/3/29
 */

public interface SleepMusicAudioContract {
    interface View extends BaseView {
        /**
         * 获取VideoView实例
         *
         * @return
         */
        VideoView getVideo();

        /**
         * 获取Context实例
         *
         * @return
         */
        Context getCon();
    }

    interface Presenter extends BasePresenter {

        /**
         * 初始化音频播放代理
         *
         * @param videoUrl 视频地址
         * @param audioUrl 音乐地址
         */
        void initProxy(String videoUrl, String audioUrl);

        /**
         * 播放音频
         */
        void startAudio();
    }
}
