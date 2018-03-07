package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.TextUtils;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IMineView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.LoggerUtil;
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

        } else {
            view.showToast(message);
        }
    }
}
