package com.example.ben.rainy_night.fragment.main_frag.view;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 *
 * @author Ben
 * @date 2018/1/8
 */

public interface IMainView {

    /**
     * 获取栈内的fragment对象
     * @param fragmentClass
     * @return
     */
    SupportFragment findChildFrag(Class fragmentClass);

    /**
     * 加载多个同级根Fragment,类似Wechat, QQ主页的场景
     * @param containerId
     * @param showPosition
     * @param toFragments
     */
    void loadMultipleRootFrag(int containerId, int showPosition, ISupportFragment... toFragments);

    /**
     * 显示隐藏fragment
     * @param showFragment
     * @param hideFragment
     */
    void showHideFrag(ISupportFragment showFragment, ISupportFragment hideFragment);

}
