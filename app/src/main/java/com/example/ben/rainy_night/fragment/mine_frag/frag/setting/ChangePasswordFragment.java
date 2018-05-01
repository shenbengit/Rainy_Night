package com.example.ben.rainy_night.fragment.mine_frag.frag.setting;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.mine_frag.contract.ChangePasswordContract;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.ChangePasswordPresenterImpl;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.DialogLoadingUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ben
 * @date 2018/3/7
 */

public class ChangePasswordFragment extends BaseFragment<ChangePasswordContract.Presenter> implements ChangePasswordContract.View {

    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.pet_oldPwd)
    PowerfulEditText petOldPwd;
    @BindView(R.id.pet_newPwd)
    PowerfulEditText petNewPwd;
    @BindView(R.id.btn_changePwd)
    Button btnChangePwd;

    @OnClick({R.id.btn_changePwd})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_changePwd:
                if (TextUtils.isEmpty(petOldPwd.getText().toString().trim()) ||
                        TextUtils.isEmpty(petNewPwd.getText().toString().trim())) {
                    toastShow("不能为空！");
                    return;
                }
                presenter.updateCurrentUserPassword();
                break;
            default:
                break;
        }
    }

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    private DialogLoadingUtil mDialog;

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_change_password;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {
        presenter = new ChangePasswordPresenterImpl(this);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        baseToolbar.setTitle(R.string.change_user_password);
        initToolbarNav(baseToolbar);

        mDialog = new DialogLoadingUtil(_mActivity);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

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
    public void isUpdatePasswordSuccess(OnUserEvent event) {
        if (TextUtils.equals(event.getRequest(), Constant.REQUEST_CHANGE_PASSWORD)) {
            presenter.isUpdateCurrentUserPasswordSuccess(event.getResult());
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
     * 获取旧密码
     *
     * @return 旧密码
     */
    @Override
    public String getOldPwd() {
        return petOldPwd.getText().toString().trim();
    }

    /**
     * 获取新密码
     *
     * @return 新密码
     */
    @Override
    public String getNewPwd() {
        return petNewPwd.getText().toString().trim();
    }

    /**
     * 弹出回退栈中，SettingFragment(包括本身)以上的Fragment
     */
    @Override
    public void getPreFragmentPopAndStart() {
        popTo(SettingFragment.class, true, () -> start(LoginFragment.newInstance()));
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
