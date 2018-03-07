package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;

/**
 * @author Ben
 * @date 2018/3/7
 */

public interface SettingPresenter extends BasePresenter {
    /**
     * 修改密码
     *
     * @param requset 请求类型
     * @param oldPwd  旧密码
     * @param newPwd  新密码
     */
    void updateCurrentUserPassword(String requset, String oldPwd, String newPwd);

    /**
     * 关于我们
     */
    void aboutUs();

    /**
     * 清除缓存
     */
    void clearCache();

    /**
     * 退出登录
     */
    void loginOut();
}
