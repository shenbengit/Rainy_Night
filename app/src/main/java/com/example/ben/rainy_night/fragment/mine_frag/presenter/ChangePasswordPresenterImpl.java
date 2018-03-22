package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.mine_frag.contract.ChangePasswordContract;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.util.Constant;
import com.vondear.rxtools.RxRegTool;

/**
 * @author Ben
 * @date 2018/3/12
 */

public class ChangePasswordPresenterImpl implements ChangePasswordContract.Presenter {
    private ChangePasswordContract.View view;
    private UserModel model;

    public ChangePasswordPresenterImpl(ChangePasswordContract.View view) {
        this.view = view;
        model = new UserModelImpl();
    }

    /**
     * 用户更新账号密码
     */
    @Override
    public void updateCurrentUserPassword() {
        String regex_password = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        if (!RxRegTool.isMatch(regex_password, view.getNewPwd())) {
            view.showToast("请输入8~16位数字、字母组合密码!");
            return;
        }

        if (!view.isNetworkAvailable()) {
            view.showToast("当前网络不可用");
            return;
        }
        view.showDialog();
        model.updateCurrentUserPassword(Constant.REQUEST_CHANGE_PASSWORD, view.getOldPwd(), view.getNewPwd());
    }

    /**
     * 修改密码是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     */
    @Override
    public void isUpdateCurrentUserPasswordSuccess(String message) {
        view.cancelDialog();
        if (TextUtils.equals(Constant.OK, message)) {
            view.getPreFragmentPopAndStart();
            view.showToast("密码修改成功,请重新登录!");
        } else {
            view.showToast(message);
        }
    }
}
