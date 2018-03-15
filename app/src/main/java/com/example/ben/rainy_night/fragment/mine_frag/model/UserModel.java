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
     * @param nickname
     * @param password
     */
    void register(String request, String phone, String nickname, String password);

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
     */
    void getUserInformation(String request);

    /**
     * 更新用户信息
     *
     * @param request
     * @param userBean 用户实体
     */
    void updateUser(String request, UserBean userBean);

    /**
     * 用户更新账号密码
     *
     * @param request
     * @param oldPwd  旧密码
     * @param newPwd  新密码
     */
    void updateCurrentUserPassword(String request, final String oldPwd, final String newPwd);
}
