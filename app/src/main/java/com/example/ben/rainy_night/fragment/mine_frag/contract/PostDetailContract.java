package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.event.OnPostCommentEvent;
import com.example.ben.rainy_night.event.OnPostLikesEvent;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

import me.yokeyword.fragmentation.ISupportFragment;

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
        SwipeRefreshLayout getSwipeRefresh();

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
        CheckBox getCheckLikes();

        /**
         * 获取评论控件
         *
         * @return
         */
        CheckBox getCheckComment();

        /**
         * 评论数量
         *
         * @return
         */
        TextView getTextCommentList();

        /**
         * 无评论布局
         *
         * @return
         */
        LinearLayout getLinearNoComment();

        /**
         * 评论输入框
         *
         * @return
         */
        EditText getPostComment();

        /**
         * 跳转至fragment
         *
         * @param fragment 目标fragment
         */
        void startBrotherFragment(ISupportFragment fragment);
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化
         *
         * @param objectId 帖子的objectId
         */
        void init(String objectId);

        /**
         * 添加评论数据
         */
        void loadCommentData();

        /**
         * 获取当前登陆用户信息
         *
         * @param entity 用户信息
         */
        void getCurrentUser(UserEntity entity);

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

        /**
         * 给帖子添加评论
         *
         * @param comment 评论内容
         */
        void addPostComment(String comment);

        /**
         * 设置帖子点赞状态
         *
         * @param isLikes 是否喜欢
         */
        void setPostLikes(boolean isLikes);

    }
}
