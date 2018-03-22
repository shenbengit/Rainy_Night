package com.example.ben.rainy_night.base;

/**
 * @author Ben
 * @date 2018/1/22
 */

public interface BaseView {

    /**
     * 当前网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    boolean isNetworkAvailable();

    /**
     * 显示Toast
     *
     * @param text
     */
    void showToast(String text);

    /**
     * 显示网络加载Dialog
     */
    void showDialog();

    /**
     * 关闭网络加载Dialog
     */
    void cancelDialog();
}
