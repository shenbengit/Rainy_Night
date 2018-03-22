package com.example.ben.rainy_night.fragment.mine_frag.listener;

import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

/**
 *
 * @author Ben
 * @date 2018/1/22
 */

public interface OnUserListener {
    /**
     * 用户注册|登陆
     */
     interface OnLoginRegisterListener{
        /**
         * 用户注册|登陆 成功
         * @param bean
         */
        void onSuccess(UserEntity bean);

        /**
         * 用户注册|登陆 失败
         * @param error 错误内容
         */
        void onError(String error);
    }

    /**
     * 更新用户资料
     */
    interface OnUpdateUserListener{
        /**
         * 用户资料更新成功
         */
        void onSuccess();

        /**
         * 用户资料更新失败
         * @param error 错误内容
         */
        void onError(String error);
    }

    /**
     * 查询用户信息
     */
    interface OnQueryUserListener{
        /**
         * 查询用户信息成功
         * @param bean
         */
        void onSuccess(UserEntity bean);

        /**
         * 查询用户信息失败
         * @param error 错误内容
         */
        void onError(String error);
    }
}
