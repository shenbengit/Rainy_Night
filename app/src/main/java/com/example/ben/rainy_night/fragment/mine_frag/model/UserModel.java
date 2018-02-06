package com.example.ben.rainy_night.fragment.mine_frag.model;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.listener.OnUserListener;

/**
 * @author Ben
 * @date 2018/1/22
 */

public interface UserModel {

    /**
     * 用户注册
     *
     * @param phone    手机号
     * @param password 密码
     */
    void register(String phone, String password);

    /**
     * 用户登陆
     *
     * @param phone
     * @param password
     */
    void login(String phone, String password);

    /**
     * 获取用户信息
     *
     * @param objectId 用户objectId
     */
    void getUserInformation(String objectId);

    /**
     * 更新用户信息
     *
     * @param userBean 用户实体
     */
    void updateUser(UserBean userBean);
}
