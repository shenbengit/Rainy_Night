package com.example.ben.rainy_night.fragment.mine_frag.frag.login_register;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.mine_frag.contract.ForgotPasswordContract;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.ForgotPasswordPresenterImpl;
import com.vondear.rxtools.RxRegTool;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ben
 * @date 2018/5/23
 */

public class ForgotPasswordFragment extends BaseFragment<ForgotPasswordContract.Presenter> implements ForgotPasswordContract.View {

    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.pet_forgot_phone)
    PowerfulEditText petForgotPhone;
    @BindView(R.id.pet_forgot_password)
    PowerfulEditText petForgotPassword;
    @BindView(R.id.pet_forgot_phoneCode)
    PowerfulEditText petForgotPhoneCode;
    @BindView(R.id.tv_forgot_phoneCode)
    TextView tvPhoneCode;
    @BindView(R.id.btn_change)
    Button btnChange;

    @OnClick({R.id.btn_change, R.id.tv_forgot_phoneCode})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_phoneCode:
                if (!RxRegTool.isMobile(petForgotPhone.getText().toString().trim())) {
                    toastShow("手机号格式不正确!");
                    return;
                }
                presenter.sendPhoneCode();
                break;
            case R.id.btn_change:
                if (!RxRegTool.isMobile(petForgotPhone.getText().toString().trim())) {
                    toastShow("手机号格式不正确!");
                    return;
                }

                if (TextUtils.isEmpty(petForgotPhoneCode.getText().toString().trim())) {
                    toastShow("手机验证码不能为空");
                    return;
                }
                if (!petForgotPassword.getText().toString().trim().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")) {
                    toastShow("请输入8~16位数字、字母组合密码!");
                    return;
                }
                presenter.changePassword();
                break;
            default:
                break;
        }
    }

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    protected void setPresenter() {
        presenter = new ForgotPasswordPresenterImpl(this);
    }

    @Override
    protected void initView() {
        baseToolbar.setTitle(R.string.forgot_password);
        initToolbarNav(baseToolbar);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
    }

    /**
     * 当前网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    @Override
    public boolean isNetworkAvailable() {
        return isNetAvailable();
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    @Override
    public void showToast(String text) {
        toastShow(text);
    }

    /**
     * 显示网络加载Dialog
     */
    @Override
    public void showDialog() {

    }

    /**
     * 关闭网络加载Dialog
     */
    @Override
    public void cancelDialog() {

    }

    @Override
    public FragmentActivity getAct() {
        return _mActivity;
    }

    @Override
    public PowerfulEditText getEditPhone() {
        return petForgotPhone;
    }

    @Override
    public TextView getTextPhoneCode() {
        return tvPhoneCode;
    }

    @Override
    public PowerfulEditText getEditPass() {
        return petForgotPassword;
    }

    @Override
    public PowerfulEditText getEditPhoneCode() {
        return petForgotPhoneCode;
    }
}
