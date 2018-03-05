package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IMineView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

/**
 * @author Ben
 * @date 2018/1/19
 */

public class MinePresenterImpl implements MinePresenter {

    private IMineView view;
    private UserModel model;

    public MinePresenterImpl(IMineView view) {
        this.view = view;
        model = new UserModelImpl();
    }

    /**
     * 用户登陆
     *
     * @param request  类型
     * @param account  帐户名
     * @param password 密码
     */
    @Override
    public void loginUser(String request, String account, String password) {
        model.login(request, account, password);
    }

    /**
     * 用户登陆是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     * @param bean
     */
    @Override
    public void isLoginSuccess(String message, UserBean bean) {
        if (TextUtils.equals(ConstantUtil.OK, message)) {
            if (bean.getHeadimg() != null) {
                isNull(SharedPreferencesUtil.USER_HEAD_IMAGE, bean.getHeadimg().getFileUrl());
            }
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

    /**
     * 加载图片
     *
     * @param imageUri 头像uri地址
     */
    private void loadImage(String imageUri) {
        GlideApp.with(view.getFragmentActivity())
                .load(imageUri)
                .placeholder(R.mipmap.ic_head)
                .error(R.mipmap.ic_head)
                .into(view.getHeadImg());
    }
}
