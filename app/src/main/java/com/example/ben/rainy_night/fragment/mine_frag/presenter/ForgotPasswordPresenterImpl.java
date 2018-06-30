package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.fragment.mine_frag.contract.ForgotPasswordContract;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author Ben
 * @date 2018/5/23
 */

public class ForgotPasswordPresenterImpl implements ForgotPasswordContract.Presenter {

    private ForgotPasswordContract.View view;

    private CountDownTimer mTimer;

    public ForgotPasswordPresenterImpl(ForgotPasswordContract.View view) {
        this.view = view;
    }

    @Override
    public void sendPhoneCode() {
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

    @Override
    public void changePassword() {
        BmobUser.resetPasswordBySMSCode(view.getEditPhoneCode().getText().toString().trim(),
                view.getEditPass().getText().toString().trim(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            view.showToast("密码重置成功，请重新登陆!");
                            view.getAct().onBackPressed();
                        } else {
                            if (e.getErrorCode() == 100) {
                                view.showToast("此用户暂未注册");
                            } else {
                                view.showToast("ErrorMessage: " + e.getMessage() + ",ErrorCode: " + e.getErrorCode());
                            }

                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
