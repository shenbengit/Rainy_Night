package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.mine_frag.contract.EmailContract;
import com.example.ben.rainy_night.http.bmob.model.UserModel;
import com.example.ben.rainy_night.http.bmob.model.UserModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;
import com.vondear.rxtools.RxRegTool;

/**
 * @author Ben
 * @date 2018/2/3
 */

public class EmailPresenterImpl implements EmailContract.Presenter {
    private EmailContract.View view;
    private UserModel model;
    private String email;

    public EmailPresenterImpl(EmailContract.View view) {
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

        if (!view.isNetworkAvailable()) {
            view.showToast("当前网络不可用");
            return;
        }

        view.showDialog();
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        model.updateUser(Constant.REQUEST_EMAIL, entity);
    }

    /**
     * 修改邮箱是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     */
    @Override
    public void isChangeEmailSuccess(String message) {
        view.cancelDialog();
        if (TextUtils.equals(Constant.OK, message)) {
            view.showToast("邮箱地址更新成功");
        } else {
            view.showToast(message);
        }
    }
}
