package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.support.v4.app.FragmentActivity;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/3/22
 */

public interface SettingContract {
    interface View extends BaseView{

        /**
         * 获取FragmentActivity
         *
         * @return
         */
        FragmentActivity getAct();

        /**
         * 跳转到指定fragment，并关闭自己
         *
         * @param fragment 要跳转到的fragment
         */
        void startWithPopToFragment(ISupportFragment fragment);

        /**
         * 清空SP
         */
        void clearSP();
    }

    interface Presenter extends BasePresenter {

        /**
         * 关于我们
         */
        void aboutUs();

        /**
         * 清除缓存
         */
        void clearCache();

        /**
         * 退出登录
         */
        void loginOut();
    }
}
