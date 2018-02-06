package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.bean.UserBean;

/**
 * @author Ben
 * @date 2018/1/22
 */

public interface LoginPresenter extends BasePresenter {
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
    void isLoginSuccess(String message, UserBean bean);
}
