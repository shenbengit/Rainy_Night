package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.bean.UserBean;

/**
 * @author Ben
 * @date 2018/1/19
 */

public interface MinePresenter extends BasePresenter {

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
    void isLoginSuccess(String message, UserBean bean);
}
