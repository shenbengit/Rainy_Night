package com.example.ben.rainy_night.fragment.mine_frag.contract;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/3/22
 */

public interface EmailContract {

    interface View extends BaseView {
        /**
         * 获取邮箱控件
         *
         * @return
         */
        PowerfulEditText getTextEmail();
    }

    interface Presenter extends BasePresenter {
        /**
         * 修改邮箱
         */
        void changeEmail();

        /**
         * 修改邮箱是否成功
         *
         * @param message 成功：“ok”,失败：“e.getMessage()”
         */
        void isChangeEmailSuccess(String message);
    }
}
