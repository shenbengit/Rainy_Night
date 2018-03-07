package com.example.ben.rainy_night.fragment.mine_frag.view;

import android.support.v4.app.FragmentActivity;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/3/7
 */

public interface ISettingView {

    /**
     * 显示Toast
     *
     * @param text 文本
     */
    void showToast(String text);

    /**
     * 获取FragmentActivity
     *
     * @return
     */
    FragmentActivity getAct();

    /**
     * 跳转到指定fragment，并关闭自己
     *
     * @param fragment 要跳转到的fragment
     */
    void startWithPopToFragment(ISupportFragment fragment);

    /**
     * 清空SP
     */
    void clearSP();
}
