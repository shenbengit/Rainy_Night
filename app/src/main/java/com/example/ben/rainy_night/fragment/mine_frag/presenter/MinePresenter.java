package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.bean.UserBean;

/**
 * @author Ben
 * @date 2018/1/19
 */

public interface MinePresenter extends BasePresenter {
    /**
     * 获取用户信息
     */
    void getUserInformation();

    /**
     * 获取用户信息是否成功
     * @param message 成功：“ok”,失败：“e.getMessage()”
     * @param bean
     */
    void isGetUserInformationSuccess(String message, UserBean bean);
}
