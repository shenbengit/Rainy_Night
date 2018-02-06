package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IMineView;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

/**
 * @author Ben
 * @date 2018/1/19
 */

public class MinePresenterImpl implements MinePresenter {

    private IMineView view;
    private UserModel model;

    private static final String OK = "ok";

    public MinePresenterImpl(IMineView view) {
        this.view = view;
        model = new UserModelImpl();
    }


    /**
     * 获取用户信息
     */
    @Override
    public void getUserInformation() {
        model.getUserInformation(String.valueOf(view.getSpValue(SharedPreferencesUtil.USER_OBJECT_ID, "")));
    }

    /**
     * 获取用户信息是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     * @param bean
     */
    @Override
    public void isGetUserInformationSuccess(String message, UserBean bean) {
        if (TextUtils.equals(OK, message)) {
            view.putSpValue(SharedPreferencesUtil.USER_PHONE, bean.getMobilePhoneNumber());
            view.putSpValue(SharedPreferencesUtil.USER_NAME, bean.getUsername());
            isNull(SharedPreferencesUtil.USER_NICK_NAME, bean.getNickName());
            isNull(SharedPreferencesUtil.USER_SEX, bean.getSex());
            isNull(SharedPreferencesUtil.USER_BIRTHDAY, bean.getBirthday());
            isNull(SharedPreferencesUtil.USER_EMAIL, bean.getEmail());

            if (TextUtils.isEmpty(bean.getNickName())) {
                view.getTextUser().setText(bean.getUsername());
            } else {
                view.getTextUser().setText(bean.getNickName());
            }

            if (bean.getHeadimg() == null) {
                view.getHeadImg().setImageDrawable(view.getFragmentActivity().getResources().getDrawable(R.mipmap.ic_head));
            } else {
                view.putSpValue(SharedPreferencesUtil.USER_HEAD_IMAGE, bean.getHeadimg().getFileUrl());
                loadImage(bean.getHeadimg().getFileUrl());
            }
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
                .error(R.mipmap.ic_head)
                .into(view.getHeadImg());
    }
}
