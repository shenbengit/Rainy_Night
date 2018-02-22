package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IRegisterView;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

/**
 * @author Ben
 * @date 2018/1/22
 */

public class RegisterPresenterImpl implements RegisterPresenter {
    private IRegisterView view;
    private UserModel model;

    private static final String OK = "ok";
    private static final String REQUEST_REGISTER = "register";

    public RegisterPresenterImpl(IRegisterView view) {
        this.view = view;
        model = new UserModelImpl();
    }

    /**
     * 用户注册
     */
    @Override
    public void registerUser() {
        view.showDialog();
        model.register(REQUEST_REGISTER, view.getEditPhone().getText().toString().trim(), view.getEditPassWord().getText().toString().trim());
    }

    /**
     * 用户注册是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     * @param bean
     */
    @Override
    public void isRegisterSuccess(String message, UserBean bean) {
        if (TextUtils.equals(OK, message)) {
            view.cancelDialog();
            view.showToast("注册成功");
            view.putSpValue(SharedPreferencesUtil.USER_OBJECT_ID, bean.getObjectId());
        } else {
            view.cancelDialog();
            view.showToast(message);
        }
    }


}
