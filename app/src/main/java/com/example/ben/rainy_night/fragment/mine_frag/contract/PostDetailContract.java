package com.example.ben.rainy_night.fragment.mine_frag.contract;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/5/8
 */

public interface PostDetailContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化
         */
        void init();
    }
}
