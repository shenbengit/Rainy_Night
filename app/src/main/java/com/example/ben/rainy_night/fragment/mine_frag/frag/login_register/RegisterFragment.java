package com.example.ben.rainy_night.fragment.mine_frag.frag.login_register;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.RegisterPresenter;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.RegisterPresenterImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IRegisterView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ben
 */
public class RegisterFragment extends BaseBackFragment<RegisterPresenter> implements IRegisterView {

    @BindView(R.id.pet_register_name)
    PowerfulEditText petRegisterName;
    @BindView(R.id.pet_register_phone)
    PowerfulEditText petRegisterPhone;
    @BindView(R.id.pet_register_password)
    PowerfulEditText petRegisterPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @OnClick({R.id.btn_register})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (TextUtils.isEmpty(petRegisterPhone.getText().toString().trim()) ||
                        TextUtils.isEmpty(petRegisterPassword.getText().toString().trim())) {
                    showToast("注册用户不能为空");
                    return;
                }
                presenter.registerUser();
                break;
            default:
                break;
        }
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    public void setPresenter() {
        presenter = new RegisterPresenterImpl(this);
    }

    /**
     * 返回界面layout
     *
     * @return
     */
    @Override
    public int getLayout() {
        return R.layout.register_fragment;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void isRegisterSuccess(OnUserEvent event) {
        presenter.isRegisterSuccess(event.getMessage(), event.getBean());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        hideSoftInput();
    }

    /**
     * @return 手机号
     */
    @Override
    public PowerfulEditText getEditPhone() {
        return petRegisterPhone;
    }

    /**
     * @return 密码
     */
    @Override
    public PowerfulEditText getEditPassWord() {
        return petRegisterPassword;
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
        dialogShow();
    }

    /**
     * @return 网络加载Dialog是否正在显示
     */
    @Override
    public boolean dialogIsShowing() {
        return dialogIsShow();
    }

    /**
     * 关闭网络加载Dialog
     */
    @Override
    public void cancelDialog() {
        dialogCancel();
    }

    /**
     * 使用SharedPreferences存储信息
     *
     * @param keyName 键
     * @param value   值
     */
    @Override
    public void putSpValue(String keyName, Object value) {
        putSharedPreferences(keyName, value);
    }

    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     *
     * @param keyName      键
     * @param defaultValue 默认值
     * @return
     */
    @Override
    public Object getSpValue(String keyName, Object defaultValue) {
        return getSharedPreferences(keyName, defaultValue);
    }
}
