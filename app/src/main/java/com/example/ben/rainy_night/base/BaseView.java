package com.example.ben.rainy_night.base;

/**
 * @author Ben
 * @date 2018/1/22
 */

public interface BaseView {

    /**
     * 显示Toast
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
