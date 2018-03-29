package com.example.ben.rainy_night.fragment.home_frag.contract;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

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
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取音乐数据
         *
         * @param sceneType 1: 自然音符 ,2: 轻音乐
         */
        void getMusic(String sceneType);
    }
}
