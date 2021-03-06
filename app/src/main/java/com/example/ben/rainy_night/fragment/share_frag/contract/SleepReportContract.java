package com.example.ben.rainy_night.fragment.share_frag.contract;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.http.bmob.entity.SleepMusicEntity;

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
         * 请求睡眠报告列表
         */
        void requsetSleepReportList();

        /**
         * 获取睡眠报告列表数据
         *
         * @param result 返回结果是否成功
         * @param entity 数据
         */
        void getSleepReportListData(String result, SleepMusicEntity entity);


        /**
         * 销毁操作
         */
        void onDestroy();
    }
}
