package com.example.ben.rainy_night.fragment.share_frag.contract;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/4/22
 */

public interface SleepReportContract {

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

    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化Adapter
         * * @param title 标题
         */
        void init(String title);

        /**
         * 获取睡眠报告列表
         */
        void getSleepReportList();

        /**
         * 销毁操作
         */
        void onDestroy();
    }
}
