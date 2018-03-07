package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.view.View;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.ISettingView;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import cn.bmob.v3.BmobUser;

/**
 * @author Ben
 * @date 2018/3/7
 */

public class SettingPresenterImpl implements SettingPresenter {

    private ISettingView view;
    private UserModel model;

    public SettingPresenterImpl(ISettingView view) {
        this.view = view;
        model = new UserModelImpl();
    }

    /**
     * 修改密码
     *
     * @param requset 请求类型
     * @param oldPwd  旧密码
     * @param newPwd  新密码
     */
    @Override
    public void updateCurrentUserPassword(String requset, String oldPwd, String newPwd) {

    }

    /**
     * 关于我们
     */
    @Override
    public void aboutUs() {

    }

    /**
     * 清除缓存
     */
    @Override
    public void clearCache() {

    }

    /**
     * 退出登录
     */
    @Override
    public void loginOut() {
        final RxDialogSureCancel dialog = new RxDialogSureCancel(view.getAct());
        dialog.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                UserBean bean = BmobUser.getCurrentUser(UserBean.class);
                if (bean == null) {
                    dialog.cancel();
                    view.showToast("用户已退出登陆");
                    view.clearSP();
                    view.startWithPopToFragment(LoginFragment.newInstance());
                }
            }
        });
        dialog.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
