package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IChangePasswordView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.vondear.rxtools.RxRegTool;

/**
 * @author Ben
 * @date 2018/3/12
 */

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {
    private IChangePasswordView view;
    private UserModel model;
    public ChangePasswordPresenterImpl(IChangePasswordView view) {
        this.view = view;
        model=new UserModelImpl();
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
        view.showDialog();
        model.updateCurrentUserPassword(ConstantUtil.REQUEST_CHANGE_PASSWORD,view.getOldPwd(),view.getNewPwd());
    }

    /**
     * 修改密码是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     */
    @Override
    public void isUpdateCurrentUserPasswordSuccess(String message) {
        view.cancelDialog();
        if (TextUtils.equals(ConstantUtil.OK,message)){
            view.getPreFragmentPopAndStart();
            view.showToast("密码修改成功,请重新登录!");
        }else {
            view.showToast(message);
        }
    }
}
