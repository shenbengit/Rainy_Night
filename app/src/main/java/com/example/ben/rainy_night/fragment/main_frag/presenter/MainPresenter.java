package com.example.ben.rainy_night.fragment.main_frag.presenter;

import com.example.ben.rainy_night.base.BasePresenter;

/**
 *
 * @author Ben
 * @date 2018/1/11
 */

public interface MainPresenter extends BasePresenter{

    /**
     *初始化Fragment
     */
    void loadRootFragment();

    /**
     *显示隐藏Fragment
     * @param position
     * @param prePosition
     */
    void showHideFragment(int position, int prePosition);
}
