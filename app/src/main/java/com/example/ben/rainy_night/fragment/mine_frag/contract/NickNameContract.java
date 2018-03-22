package com.example.ben.rainy_night.fragment.mine_frag.contract;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/3/22
 */

public interface NickNameContract {

    interface View extends BaseView {
        /**
         * 获取PowerfulEditText
         *
         * @return
         */
        PowerfulEditText getEditText();
    }

    interface Presenter extends BasePresenter {
        /**
         * 修改昵称
         */
        void changeNickName();

        /**
         * 修改昵称是否成功
         *
         * @param message
         */
        void isChangeNickNameSuccess(String message);
    }
}
