package com.example.ben.rainy_night.http.bmob.model;

import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.http.bmob.UserBmob;

/**
 * @author Ben
 * @date 2018/1/22
 */

public class UserModelImpl implements UserModel {

    /**
     * 用户注册
     *
     * @param request
     * @param phone
     * @param nickname
     * @param password
     */
    @Override
    public void register(String request, String phone, String nickname, String password) {
        UserBmob.getInstance().registerUser(request, phone, nickname, password);
    }

    /**
     * 用户登陆
     *
     * @param request
     * @param phone
     * @param password
     */
    @Override
    public void login(String request, String phone, String password) {
        UserBmob.getInstance().loginUser(request, phone, password);
    }

    /**
     * 获取用户信息
     *
     * @param request
     */
    @Override
    public void getUserInformation(String request) {
        UserBmob.getInstance().getUserInformation(request);
    }

    /**
     * 更新用户信息
     *
     * @param request
     * @param userEntity 用户实体
     */
    @Override
    public void updateUser(String request, UserEntity userEntity) {
        UserBmob.getInstance().updateUser(request, userEntity);
    }

    /**
     * 用户更新账号密码
     *
     * @param request
     * @param oldPwd  旧密码
     * @param newPwd  新密码
     */
    @Override
    public void updateCurrentUserPassword(String request, String oldPwd, String newPwd) {
        UserBmob.getInstance().updateCurrentUserPassword(request, oldPwd, newPwd);
    }

}
