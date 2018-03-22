package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

/**
 *
 * @author Ben
 * @date 2018/3/22
 */

public interface RegisterContract {

    interface View extends BaseView {

        /**
         * @return 手机号
         */
        PowerfulEditText getEditPhone();

        /**
         * @return 用户名
         */
        PowerfulEditText getEditName();

        /**
         * @return 密码
         */
        PowerfulEditText getEditPassWord();

        /**
         * @return 图片验证码
         */
        PowerfulEditText getEditPictureCode();

        /**
         * @return 手机验证码
         */
        PowerfulEditText getEditPhoneCode();

        /**
         * 发送手机验证码
         *
         * @return
         */
        TextView getTextPhoneCode();

        /**
         * 图片验证码
         *
         * @return
         */
        String getStringPictureCode();
    }

    interface Presenter extends BasePresenter {

        /**
         * 发送短信验证码
         */
        void sendPhoneCode();

        /**
         * 用户注册
         */
        void registerUser();

        /**
         * 用户注册是否成功
         *
         * @param message 成功：“ok”,失败：“e.getMessage()”
         * @param bean
         */
        void isRegisterSuccess(String message, UserEntity bean);

        /**
         * 用于取消定时器
         */
        void cancel();
    }
}
