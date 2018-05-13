package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.mine_frag.contract.LoginContract;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.http.bmob.model.UserModel;
import com.example.ben.rainy_night.http.bmob.model.UserModelImpl;
import com.example.ben.rainy_night.util.Constant;

/**
 * @author Ben
 * @date 2018/1/22
 */

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View view;
    private UserModel model;

    public LoginPresenterImpl(LoginContract.View view) {
        this.view = view;
        model = new UserModelImpl();
    }


    /**
     * 用户登陆
     */
    @Override
    public void login() {
        if (!view.isNetworkAvailable()) {
            view.showToast("当前网络不可用");
            return;
        }

        view.showDialog();
        model.login(Constant.REQUEST_LOGIN, view.getEditPhone().getText().toString().trim(), view.getEditPassWord().getText().toString().trim());
    }

    /**
     * 用户登陆是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     * @param bean
     */
    @Override
    public void isLoginSuccess(String message, UserEntity bean) {
        view.cancelDialog();
        if (TextUtils.equals(Constant.OK, message)) {
            view.putSpValue(Constant.USER_PASSWORD,view.getEditPassWord().getText().toString().trim());
            view.putSpValue(Constant.USER_PHONE,bean.getMobilePhoneNumber());
            view.showToast("登陆成功");
            view.getAct().onBackPressed();
        } else {
            view.showToast(message);
        }
    }
}
