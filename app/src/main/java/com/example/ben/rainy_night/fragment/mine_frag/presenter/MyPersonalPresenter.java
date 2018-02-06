package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.content.Intent;

import com.example.ben.rainy_night.base.BasePresenter;

/**
 * @author Ben
 * @date 2018/1/22
 */

public interface MyPersonalPresenter extends BasePresenter {
    /**
     * 选择照片返回的数据
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * 更新用户信息
     */
    void updateUser();

    /**
     * 更新用户信息是否成功
     * @param message 成功：“ok”,失败：“e.getMessage()”
     */
    void isUpdateUserSuccess(String message);
}
