package com.example.ben.rainy_night.fragment.mine_frag.contract;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/3/22
 */

public interface ChangePasswordContract {

    interface View extends BaseView {
        /**
         * 获取旧密码
         *
         * @return 旧密码
         */
        String getOldPwd();

        /**
         * 获取新密码
         *
         * @return 新密码
         */
        String getNewPwd();

        /**
         * 弹出回退栈中，SettingFragment(包括本身)以上的Fragment
         */
        void getPreFragmentPopAndStart();
    }

    interface Presenter extends BasePresenter {
        /**
         * 用户更新账号密码
         */
        void updateCurrentUserPassword();

        /**
         * 修改密码是否成功
         *
         * @param message 成功：“ok”,失败：“e.getMessage()”
         */
        void isUpdateCurrentUserPasswordSuccess(String message);
    }
}
