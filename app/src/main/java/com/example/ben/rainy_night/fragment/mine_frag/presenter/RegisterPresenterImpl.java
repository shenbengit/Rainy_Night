package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.fragment.mine_frag.contract.RegisterContract;
import com.example.ben.rainy_night.http.bmob.model.UserModel;
import com.example.ben.rainy_night.http.bmob.model.UserModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;
import com.vondear.rxtools.RxRegTool;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author Ben
 * @date 2018/1/22
 */

public class RegisterPresenterImpl implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private UserModel model;
    private CountDownTimer mTimer;

    public RegisterPresenterImpl(RegisterContract.View view) {
        this.view = view;
        model = new UserModelImpl();
    }

    /**
     * 发送短信验证码
     */
    @Override
    public void sendPhoneCode() {
        if (!isMatch()) {
            return;
        }

        if (!view.isNetworkAvailable()) {
            view.showToast("当前网络不可用");
            return;
        }

        view.getTextPhoneCode().setEnabled(false);
        if (mTimer == null) {
            mTimer = new CountDownTimer(60000, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    view.getTextPhoneCode().setTextColor(R.color.tab_unselect);
                    view.getTextPhoneCode().setText(millisUntilFinished / 1000L + "S后重新发送");
                }

                @Override
                public void onFinish() {
                    view.getTextPhoneCode().setEnabled(true);
                    view.getTextPhoneCode().setTextColor(R.color.colorPrimary);
                    view.getTextPhoneCode().setText("发送验证码");
                }
            };
        }
        mTimer.start();

        BmobSMS.requestSMSCode(view.getEditPhone().getText().toString().trim(), "手机验证码", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {

                } else {
                    if (mTimer != null) {
                        mTimer.cancel();
                        mTimer = null;
                    }
                    view.showToast(e.getMessage() + ",ErrorCode: " + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 用户注册
     */
    @Override
    public void registerUser() {
        if (!isMatch()) {
            return;
        }

        if (!view.isNetworkAvailable()) {
            view.showToast("当前网络不可用");
            return;
        }

        BmobSMS.verifySmsCode(view.getEditPhone().getText().toString().trim(),
                view.getEditPhoneCode().getText().toString().trim(),
                new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            view.showDialog();
                            model.register(Constant.REQUEST_REGISTER,
                                    view.getEditPhone().getText().toString().trim(),
                                    view.getEditName().getText().toString().trim(),
                                    view.getEditPassWord().getText().toString().trim());
                        } else {
                            view.showToast("手机验证码输入错误，Error: " + e.getMessage() + ",ErroeCode: " + e.getMessage());
                        }
                    }
                });
    }


    /**
     * 用户注册是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     * @param bean
     */
    @Override
    public void isRegisterSuccess(String message, UserEntity bean) {
        view.cancelDialog();
        if (TextUtils.equals(Constant.OK, message)) {
            view.showToast("注册成功");
        } else {
            view.showToast(message);
        }
    }

    /**
     * 用于取消定时器
     */
    @Override
    public void cancel() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private boolean isMatch() {
        if (!RxRegTool.isMobile(view.getEditPhone().getText().toString().trim())) {
            view.showToast("手机号格式不正确!");
            return false;
        }

        String name = view.getEditName().getText().toString().trim();
        String regex_name = "[A-Za-z0-9_\\u4e00-\\u9fa5]{3,8}";
        if (!RxRegTool.isMatch(regex_name, name)) {
            view.showToast("用户名格式不正确!");
            return false;
        }

        String password = view.getEditPassWord().getText().toString().trim();
        String regex_password = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        if (!RxRegTool.isMatch(regex_password, password)) {
            view.showToast("请输入8~16位数字、字母组合密码!");
            return false;
        }

        String pictureCode = view.getEditPictureCode().getText().toString().trim();
        if (!TextUtils.equals(pictureCode, view.getStringPictureCode())) {
            view.showToast("图片验证码输入错!");
            return false;
        }
        return true;
    }

}
