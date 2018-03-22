package com.example.ben.rainy_night.fragment.main_frag.contract;

import com.example.ben.rainy_night.base.BasePresenter;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Ben
 * @date 2018/3/22
 */

public interface MainContract {

    interface View {

        /**
         * 获取栈内的fragment对象
         *
         * @param fragmentClass
         * @return
         */
        SupportFragment findChildFrag(Class fragmentClass);

        /**
         * 加载多个同级根Fragment,类似Wechat, QQ主页的场景
         *
         * @param containerId
         * @param showPosition
         * @param toFragments
         */
        void loadMultipleRootFrag(int containerId, int showPosition, ISupportFragment... toFragments);

        /**
         * 显示隐藏fragment
         *
         * @param showFragment
         * @param hideFragment
         */
        void showHideFrag(ISupportFragment showFragment, ISupportFragment hideFragment);
    }

    interface Presenter extends BasePresenter {

        /**
         * 初始化Fragment
         */
        void loadRootFragment();

        /**
         * 显示隐藏Fragment
         *
         * @param position
         * @param prePosition
         */
        void showHideFragment(int position, int prePosition);
    }
}
