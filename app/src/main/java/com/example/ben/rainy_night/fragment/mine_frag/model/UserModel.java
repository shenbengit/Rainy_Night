package com.example.ben.rainy_night.fragment.mine_frag.model;

import com.example.ben.rainy_night.bean.UserBean;

/**
 * @author Ben
 * @date 2018/1/22
 */

public interface UserModel {

    /**
     * 用户注册
     *
     * @param request
     * @param phone
     * @param password
     */
    void register(String request, String phone, String password);

    /**
     * 用户登陆
     *
     * @param request
     * @param phone
     * @param password
     */
    void login(String request, String phone, String password);

    /**
     * 获取用户信息
     *
     * @param request
     * @param objectId 用户objectId
     */
    void getUserInformation(String request, String objectId);

    /**
     * 更新用户信息
     *
     * @param request
     * @param userBean 用户实体
     */
    void updateUser(String request, UserBean userBean);
}
