package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.bean.UserBean;

/**
 * @author Ben
 * @date 2018/1/22
 */

public interface RegisterPresenter extends BasePresenter {

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
    void isRegisterSuccess(String message, UserBean bean);

    /**
     * 用于取消定时器
     */
    void cancel();
}
