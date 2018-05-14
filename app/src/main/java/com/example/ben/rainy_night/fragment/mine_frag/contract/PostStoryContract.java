package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/3/22
 */

public interface PostStoryContract {

    interface View extends BaseView {
        /**
         * 获取Activity
         *
         * @return
         */
        FragmentActivity getFragAct();

        /**
         * 获取RecyclerView
         *
         * @return
         */
        RecyclerView getRecy();

        /**
         * 获取EditText
         *
         * @return
         */
        EditText getEditText();

        /**
         * 类似 {@link Activity#setResult(int, Intent)}
         *
         * @param resultCode
         * @param bundle
         */
        void setFragResult(int resultCode, Bundle bundle);
    }

    interface Presenter extends BasePresenter {

        /**
         * 初始化GridView
         */
        void initGridView();

        /**
         * 选择照片返回的数据
         *
         * @param requestCode 请求码
         * @param resultCode  结果码
         * @param data        数据
         */
        void onActivityResult(int requestCode, int resultCode, Intent data);

        /**
         * 发表帖子
         */
        void publishPost();

        /**
         * 发表帖子是否成功
         *
         * @param message
         */
        void publishPostIsSuccess(String message);

    }
}
