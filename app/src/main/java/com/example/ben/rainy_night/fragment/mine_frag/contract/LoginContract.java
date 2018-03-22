package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.support.v4.app.FragmentActivity;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

/**
 * @author Ben
 * @date 2018/3/22
 */

public interface LoginContract {

    interface View extends BaseView {

        /**
         * @return 手机号
         */
        PowerfulEditText getEditPhone();

        /**
         * @return 密码
         */
        PowerfulEditText getEditPassWord();

        /**
         * 保存到 SP
         *
         * @param key
         * @param value
         */
        void putSpValue(String key, String value);

        /**
         * 获取FragmentActivity
         *
         * @return
         */
        FragmentActivity getAct();
    }

    interface Presenter extends BasePresenter {
        /**
         * 用户登陆
         */
        void login();

        /**
         * 用户登陆是否成功
         *
         * @param message 成功：“ok”,失败：“e.getMessage()”
         * @param bean
         */
        void isLoginSuccess(String message, UserEntity bean);
    }
}
