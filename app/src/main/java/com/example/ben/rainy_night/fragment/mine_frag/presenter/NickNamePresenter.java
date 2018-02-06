package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;

/**
 *
 * @author Ben
 * @date 2018/1/26
 */

public interface NickNamePresenter extends BasePresenter {
    /**
     * 修改昵称
     */
    void changeNickName();

    /**
     * 修改昵称是否成功
     * @param message
     */
    void isChangeNickNameSuccess(String message);
}
