package com.example.ben.rainy_night.fragment.mine_frag.contract;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

/**
 * @author Ben
 * @date 2018/3/22
 */

public interface MineContract {

    interface View extends BaseView {

    }


    interface Presenter extends BasePresenter {

        /**
         * 用户登陆
         *
         * @param request  类型
         * @param account  帐户名
         * @param password 密码
         */
        void loginUser(String request, String account, String password);

        /**
         * 用户登陆是否成功
         *
         * @param message 成功：“ok”,失败：“e.getMessage()”
         * @param bean
         */
        void isLoginSuccess(String message, UserEntity bean);
    }
}
