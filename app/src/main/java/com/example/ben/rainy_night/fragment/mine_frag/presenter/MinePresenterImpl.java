package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.mine_frag.contract.MineContract;
import com.example.ben.rainy_night.http.bmob.model.UserModel;
import com.example.ben.rainy_night.http.bmob.model.UserModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;

/**
 * @author Ben
 * @date 2018/1/19
 */

public class MinePresenterImpl implements MineContract.Presenter {
    private MineContract.View view;
    private UserModel model;

    public MinePresenterImpl(MineContract.View view) {
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
    public void isLoginSuccess(String message, UserEntity bean) {
        if (TextUtils.equals(Constant.OK, message)) {

        } else {
            view.showToast(message);
        }
    }
}
