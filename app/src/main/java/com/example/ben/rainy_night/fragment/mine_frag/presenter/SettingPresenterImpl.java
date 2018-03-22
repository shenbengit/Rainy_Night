package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.fragment.mine_frag.contract.SettingContract;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import cn.bmob.v3.BmobUser;

/**
 * @author Ben
 * @date 2018/3/7
 */

public class SettingPresenterImpl implements SettingContract.Presenter {

    private SettingContract.View view;
    private UserModel model;

    public SettingPresenterImpl(SettingContract.View view) {
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
            UserEntity bean = BmobUser.getCurrentUser(UserEntity.class);
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
