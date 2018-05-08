package com.example.ben.rainy_night.fragment.home_frag.contract;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/3/28
 */

public interface SleepMusicListContract {

    interface View extends BaseView {
        /**
         * 获取RecyclerView
         *
         * @return
         */
        RecyclerView getRecycler();

        /**
         * 获取Context
         *
         * @return
         */
        Context getCon();

        /**
         * 从兄弟fragment进行跳转
         *
         * @param fragment 要跳转的fragment
         */
        void startBrotherFragment(ISupportFragment fragment);

        /**
         * 获取当前网络类型
         *
         * @return
         */
        int getAPNType();
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化
         *
         * @param sceneType 1: 自然音符 ,2: 轻音乐
         */
        void initRecyclerView(String sceneType);

        /**
         * 获取音乐数据
         */
        void getMusic();
    }
}
