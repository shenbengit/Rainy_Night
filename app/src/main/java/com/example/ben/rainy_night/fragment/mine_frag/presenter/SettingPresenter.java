package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;

/**
 * @author Ben
 * @date 2018/3/7
 */

public interface SettingPresenter extends BasePresenter {

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
