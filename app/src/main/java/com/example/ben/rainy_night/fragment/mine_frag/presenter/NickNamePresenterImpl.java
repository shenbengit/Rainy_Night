package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IPetNameView;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.vondear.rxtools.RxRegTool;

/**
 * @author Ben
 * @date 2018/1/26
 */

public class NickNamePresenterImpl implements NickNamePresenter {

    private IPetNameView view;
    private UserModel model;
    private String nickName = "";

    private static final String OK = "ok";

    public NickNamePresenterImpl(IPetNameView view) {
        this.view = view;
        model = new UserModelImpl();
    }


    /**
     * 修改昵称
     */
    @Override
    public void changeNickName() {
        nickName = view.getTextView().getText().toString().trim();
        String regex = "[A-Za-z0-9_\\u4e00-\\u9fa5]{3,8}";
        if (!RxRegTool.isMatch(regex, nickName)) {
            view.showToast("昵称格式不正确");
            return;
        }
        view.showDialog();
        UserBean bean = new UserBean();
        bean.setObjectId(String.valueOf(view.getSpValue(SharedPreferencesUtil.USER_OBJECT_ID, "")));
        bean.setNickName(nickName);
        model.updateUser(bean);
    }

    /**
     * 修改昵称是否成功
     *
     * @param message
     */
    @Override
    public void isChangeNickNameSuccess(String message) {
        if (TextUtils.equals(OK, message)) {
            view.putSpValue(SharedPreferencesUtil.USER_NICK_NAME, nickName);
            view.showToast("昵称更改成功");
            view.cancelDialog();
        } else {
            view.cancelDialog();
            view.showToast(message);
        }
    }
}
