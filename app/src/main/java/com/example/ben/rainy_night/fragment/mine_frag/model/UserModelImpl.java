package com.example.ben.rainy_night.fragment.mine_frag.model;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.bmob.UserBmob;

/**
 * @author Ben
 * @date 2018/1/22
 */

public class UserModelImpl implements UserModel {

    /**
     * 用户注册
     *
     * @param phone    手机号
     * @param password 密码
     */
    @Override
    public void register(String phone, String password) {
        UserBmob.getInstance().registerUser(phone, password);
    }

    /**
     * 用户登陆
     *
     * @param phone
     * @param password
     */
    @Override
    public void login(String phone, String password) {
        UserBmob.getInstance().loginUser(phone, password);
    }

    /**
     * 获取用户信息
     *
     * @param objectId 用户objectId
     */
    @Override
    public void getUserInformation(String objectId) {
        UserBmob.getInstance().getUserInformation(objectId);
    }

    /**
     * 更新用户信息
     *
     * @param userBean 用户实体
     */
    @Override
    public void updateUser(UserBean userBean) {
        UserBmob.getInstance().updateUser(userBean);
    }

}
