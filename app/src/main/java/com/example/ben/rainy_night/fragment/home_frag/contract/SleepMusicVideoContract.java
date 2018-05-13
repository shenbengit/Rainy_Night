package com.example.ben.rainy_night.fragment.home_frag.contract;

import android.content.Context;
import android.widget.LinearLayout;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;

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
        VideoView getVideoView();
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化音频播放代理
         *
         * @param position 当前音频播放的位置
         */
        void init(int position);

        /**
         * 开始播放视频
         */
        void startVideo();

        /**
         * fragment可见
         */
        void onSupportVisible();

        /**
         * fragment不可见
         */
        void onSupportInVisible();

        /**
         * 停止播放视频
         */
        void onDestroy();

        /**
         * 播放上一个
         */
        void startPrevious();

        /**
         * 播放下一个
         */
        void startNext();
    }
}
