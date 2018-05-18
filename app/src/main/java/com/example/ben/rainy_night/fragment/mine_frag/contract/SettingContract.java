package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/3/22
 */

public interface SettingContract {
    interface View extends BaseView {

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

        /**
         * 获取显示缓存大小控件
         * @return
         */
        TextView getTextCached();
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化操作
         */
        void init();

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
