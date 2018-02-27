package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IEmailView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.vondear.rxtools.RxRegTool;

/**
 * @author Ben
 * @date 2018/2/3
 */

public class EmailPresenterImpl implements EmailPresenter {
    private IEmailView view;
    private UserModel model;
    private String email;

    public EmailPresenterImpl(IEmailView view) {
        this.view = view;
        model = new UserModelImpl();
    }

    /**
     * 修改邮箱
     */
    @Override
    public void changeEmail() {
        email = view.getTextEmail().getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            view.showToast("请输入邮箱地址!");
            return;
        }
        if (!RxRegTool.isEmail(email)) {
            view.showToast("请输入正确的邮箱地址!");
            return;
        }
        view.showDialog();
        UserBean bean = new UserBean();
        bean.setObjectId(String.valueOf(view.getSpValue(SharedPreferencesUtil.USER_OBJECT_ID, "")));
        bean.setEmail(email);
        model.updateUser(ConstantUtil.REQUEST_EMAIL,bean);
    }

    /**
     * 修改邮箱是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     */
    @Override
    public void isChangeEmailSuccess(String message) {
        view.cancelDialog();
        if (TextUtils.equals(ConstantUtil.OK, message)) {
            view.putSpValue(SharedPreferencesUtil.USER_EMAIL, email);
            view.showToast("邮箱地址更新成功");
        } else {
            view.showToast(message);
        }
    }
}
