package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.ILoginView;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

/**
 * @author Ben
 * @date 2018/1/22
 */

public class LoginPresenterImpl implements LoginPresenter {
    private ILoginView view;
    private UserModel model;

    private static final String OK = "ok";
    private static final String REQUEST_LOGIN = "login";

    public LoginPresenterImpl(ILoginView view) {
        this.view = view;
        model = new UserModelImpl();
    }


    /**
     * 用户登陆
     */
    @Override
    public void login() {
        view.showDialog();
        model.login(REQUEST_LOGIN, view.getEditPhone().getText().toString().trim(), view.getEditPassWord().getText().toString().trim());
    }

    /**
     * 用户登陆是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     * @param bean
     */
    @Override
    public void isLoginSuccess(String message, UserBean bean) {

        if (TextUtils.equals(OK, message)) {
            view.cancelDialog();
            view.showToast("登陆成功");
            view.putSpValue(SharedPreferencesUtil.USER_OBJECT_ID, bean.getObjectId());
            view.putSpValue(SharedPreferencesUtil.USER_NAME, bean.getUsername());
        } else {
            view.cancelDialog();
            view.showToast(message);
        }
    }
}
