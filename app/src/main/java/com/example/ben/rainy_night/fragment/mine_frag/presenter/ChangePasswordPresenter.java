package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;

/**
 * @author Ben
 * @date 2018/3/12
 */

public interface ChangePasswordPresenter extends BasePresenter {
    /**
     * 用户更新账号密码
     */
    void updateCurrentUserPassword();

    /**
     * 修改密码是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     */
    void isUpdateCurrentUserPasswordSuccess(String message);
}
