package com.example.ben.rainy_night.fragment.night_frag.contract;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

/**
 * @author Ben
 * @date 2018/4/24
 */

public interface SleepFmContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化
         * @param albumsId
         */
        void init(int albumsId);

        /**
         * 获取FM集合
         */
        void getAlbumsMediaList();
    }
}
