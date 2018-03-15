package com.example.ben.rainy_night.fragment.mine_frag.view;

import com.example.ben.rainy_night.base.BaseView;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/3/12
 */

public interface IChangePasswordView extends BaseView{
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
