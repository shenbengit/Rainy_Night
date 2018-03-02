package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.ILoginView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

/**
 * @author Ben
 * @date 2018/1/22
 */

public class LoginPresenterImpl implements LoginPresenter {
    private ILoginView view;
    private UserModel model;

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
        model.login(ConstantUtil.REQUEST_LOGIN, view.getEditPhone().getText().toString().trim(), view.getEditPassWord().getText().toString().trim());
    }

    /**
     * 用户登陆是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     * @param bean
     */
    @Override
    public void isLoginSuccess(String message, UserBean bean) {
        view.cancelDialog();
        if (TextUtils.equals(ConstantUtil.OK, message)) {
            view.showToast("登陆成功");
            view.putSpValue(SharedPreferencesUtil.USER_OBJECT_ID, bean.getObjectId());
            view.putSpValue(SharedPreferencesUtil.USER_PHONE, bean.getMobilePhoneNumber());
            view.putSpValue(SharedPreferencesUtil.USER_NAME, bean.getUsername());
            isNull(SharedPreferencesUtil.USER_HEAD_IMAGE, bean.getHeadimg().getFileUrl());
            isNull(SharedPreferencesUtil.USER_NICK_NAME, bean.getNickName());
            isNull(SharedPreferencesUtil.USER_SEX, bean.getSex());
            isNull(SharedPreferencesUtil.USER_BIRTHDAY, bean.getBirthday());
            isNull(SharedPreferencesUtil.USER_EMAIL, bean.getEmail());
        } else {
            view.showToast(message);
        }
    }

    /**
     * 判断取到的用户信息是否为空
     *
     * @param key   存在SP的key
     * @param value 值
     */
    private void isNull(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            view.putSpValue(key, "");
        } else {
            view.putSpValue(key, value);
        }
    }
}
