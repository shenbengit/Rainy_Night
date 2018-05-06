package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.fragment.event.OnPostEvent;

/**
 * @author Ben
 * @date 2018/5/4
 */

public interface SpaceContract {

    interface View extends BaseView {
        /**
         * 获取Context
         *
         * @return
         */
        Context getCon();

        /**
         * 下拉刷新控件
         *
         * @return
         */
        SwipeRefreshLayout getSwipeRefresh();

        /**
         * 列表控件
         *
         * @return
         */
        RecyclerView getRecycler();
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化
         */
        void init();

        /**
         * 加载数据
         */
        void loadData();

        /**
         * 获取帖子数据
         * @param event
         */
        void getPostData(OnPostEvent event);
    }

}
