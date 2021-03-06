package com.example.ben.rainy_night.fragment.share_frag.contract;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.http.bmob.entity.SleepMusicEntity;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/4/22
 */

public interface SleepReadContract {

    interface View extends BaseView {
        /**
         * 获取Context
         *
         * @return
         */
        Context getCon();

        /**
         * 获取RecyclerView
         *
         * @return
         */
        RecyclerView getRecycler();

        /**
         * 从兄弟fragment进行跳转
         *
         * @param fragment 要跳转的fragment
         */
        void startBrotherFragment(ISupportFragment fragment);

    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化
         * * @param title 标题
         */
        void init(String title);

        /**
         * 请求睡眠报告列表
         */
        void requsetSleepReadList();

        /**
         * 获取睡眠报告列表数据
         *
         * @param result 返回结果是否成功
         * @param entity 数据
         */
        void getSleepReadListData(String result, SleepMusicEntity entity);

        /**
         * 销毁操作
         */
        void onDestroy();
    }
}
