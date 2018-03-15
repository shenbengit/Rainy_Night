package com.example.ben.rainy_night.fragment.mine_frag.frag.login_register;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.LoginPresenter;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.LoginPresenterImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.ILoginView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.DialogLoadingUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ben
 */
public class LoginFragment extends BaseBackFragment<LoginPresenter> implements ILoginView {


    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.pet_login_phone)
    PowerfulEditText petLoginPhone;
    @BindView(R.id.pet_login_password)
    PowerfulEditText petLoginPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;

    @OnClick({R.id.btn_login, R.id.tv_regist, R.id.tv_forget_password})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                presenter.login();
                break;
            case R.id.tv_regist:
                start(RegisterFragment.newInstance());
                break;
            case R.id.tv_forget_password:

                break;
            default:
                break;
        }
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private DialogLoadingUtil mDialog;
    /**
     * 设置presenter
     */
    @Override
    public void setPresenter() {
        presenter = new LoginPresenterImpl(this);
    }

    /**
     * 返回界面layout
     *
     * @return
     */
    @Override
    public int getLayout() {
        return R.layout.login_fragment;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        baseToolbar.setTitle(R.string.login);
        initToolbarNav(baseToolbar);

        mDialog=new DialogLoadingUtil(_mActivity);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        petLoginPhone.setText(String.valueOf(SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).getValue(SharedPreferencesUtil.USER_PHONE,"")));
    }

    /**
     * 是否透明化状态栏
     *
     * @return
     */
    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void isLoginSuccess(OnUserEvent event) {
        if (TextUtils.equals(event.getRequest(), ConstantUtil.REQUEST_LOGIN)) {
            presenter.isLoginSuccess(event.getResult(), event.getBean());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        hideSoftInput();
        mDialog.cancel();
    }

    /**
     * @return 手机号
     */
    @Override
    public PowerfulEditText getEditPhone() {
        return petLoginPhone;
    }

    /**
     * @return 密码
     */
    @Override
    public PowerfulEditText getEditPassWord() {
        return petLoginPassword;
    }

    /**
     * 保存到 SP
     *
     * @param key
     * @param value
     */
    @Override
    public void putSpValue(String key, String value) {
        SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).putValue(key, value);
    }

    /**
     * 获取FragmentActivity
     *
     * @return
     */
    @Override
    public FragmentActivity getAct() {
        return _mActivity;
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
        mDialog.show();
    }

    /**
     * 关闭网络加载Dialog
     */
    @Override
    public void cancelDialog() {
        mDialog.cancel();
    }
}
