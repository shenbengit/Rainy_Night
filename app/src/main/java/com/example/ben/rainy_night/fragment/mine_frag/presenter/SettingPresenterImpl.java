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
        dialog.getSureView().setOnClickListener(v -> {
            BmobUser.logOut();
            UserBean bean = BmobUser.getCurrentUser(UserBean.class);
            if (bean == null) {
                dialog.cancel();
                view.showToast("用户已退出登陆");
                view.clearSP();
                view.startWithPopToFragment(LoginFragment.newInstance());
            }
        });
        dialog.getCancelView().setOnClickListener(v -> dialog.cancel());

        dialog.show();
    }
}
