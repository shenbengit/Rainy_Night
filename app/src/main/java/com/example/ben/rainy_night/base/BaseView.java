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
     * @return 网络加载Dialog是否正在显示
     */
    boolean dialogIsShowing();

    /**
     * 关闭网络加载Dialog
     */
    void cancelDialog();

    /**
     * 使用SharedPreferences存储信息
     *
     * @param keyName 键
     * @param value   值
     */
    void putSpValue(String keyName, Object value);

    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     *
     * @param keyName 键
     * @param defaultValue 默认值
     * @return
     */
    Object getSpValue(String keyName, Object defaultValue);
}
