package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/5/23
 */

public interface ForgotPasswordContract {
    interface View extends BaseView {
        /**
         * 获取FragmentActivity
         *
         * @return
         */
        FragmentActivity getAct();

        /**
         * 手机号
         *
         * @return
         */
        PowerfulEditText getEditPhone();

        /**
         * 发送手机验证码控件
         *
         * @return
         */
        TextView getTextPhoneCode();

        /**
         * 新密码
         *
         * @return
         */
        PowerfulEditText getEditPass();

        /**
         * 获取手机验证码
         *
         * @return
         */
        PowerfulEditText getEditPhoneCode();
    }

    interface Presenter extends BasePresenter {
        /**
         * 发送手机验证码
         */
        void sendPhoneCode();

        /**
         * 修改密码
         */
        void changePassword();

        /**
         * 销毁
         */
        void onDestroy();
    }
}
