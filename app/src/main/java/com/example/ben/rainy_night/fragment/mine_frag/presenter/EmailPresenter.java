package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;

/**
 *
 * @author Ben
 * @date 2018/2/3
 */

public interface EmailPresenter extends BasePresenter {
    /**
     * 修改邮箱
     */
    void changeEmail();

    /**
     * 修改邮箱是否成功
     * @param message 成功：“ok”,失败：“e.getMessage()”
     */
    void isChangeEmailSuccess(String message);
}
