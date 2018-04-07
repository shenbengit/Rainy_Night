package com.example.ben.rainy_night.fragment.home_frag.contract;

import android.content.Context;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.widget.FullScreenVideoView;

/**
 * @author Ben
 * @date 2018/3/29
 */

public interface SleepMusicVideoContract {
    interface View extends BaseView {
        /**
         * 获取Context
         *
         * @return
         */
        Context getCon();

        /**
         * 获取VideoView
         *
         * @return
         */
        FullScreenVideoView getVideoView();

        /**
         * 获取ImageView
         *
         * @return
         */
        ImageView getImageView();
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化音频播放代理
         *
         * @param videoUrl        视频地址
         */
        void initProxy(String videoUrl);

        /**
         * 开始播放视频
         */
        void startVideo();

        /**
         * 停止播放视频
         */
        void stopVideo();
    }
}
