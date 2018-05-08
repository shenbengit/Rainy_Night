package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.event.OnPostCommentEvent;
import com.example.ben.rainy_night.event.OnPostLikesEvent;

/**
 * @author Ben
 * @date 2018/5/8
 */

public interface PostDetailContract {

    interface View extends BaseView {
        /**
         * 获取Context
         *
         * @return
         */
        Context getCon();

        /**
         * 获取SwipeRefreshLayout
         *
         * @return
         */
        SwipeRefreshLayout getSwipRefresh();

        /**
         * 获取RecyclerView  (评论列表)
         *
         * @return
         */
        RecyclerView getRecy();

        /**
         * 获取“赞”控件
         *
         * @return
         */
        TextView getTextLikes();

        /**
         * 获取评论控件
         *
         * @return
         */
        TextView getTextComment();
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化
         */
        void init();

        /**
         * 获得返回的评论结果
         *
         * @param event
         */
        void getPostComment(OnPostCommentEvent event);

        /**
         * 获得返回的点赞结果
         *
         * @param event
         */
        void getPostLikes(OnPostLikesEvent event);
    }
}
