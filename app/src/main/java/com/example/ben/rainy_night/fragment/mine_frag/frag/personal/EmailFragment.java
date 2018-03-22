package com.example.ben.rainy_night.fragment.mine_frag.frag.personal;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.mine_frag.contract.EmailContract;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.EmailPresenterImpl;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.DialogLoadingUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ben
 * @date 2018/2/3
 */

public class EmailFragment extends BaseBackFragment<EmailContract.Presenter> implements EmailContract.View {

    private static final String ARG_NAME = "arg_name";

    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.pet_email)
    PowerfulEditText petEmail;
    @BindView(R.id.btn_changeEmail)
    Button btnChangeEmail;

    @OnClick({R.id.btn_changeEmail})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_changeEmail:
                presenter.changeEmail();
                break;
            default:
                break;
        }
    }

    public static EmailFragment newInstance(String email) {
        Bundle bundle = new Bundle();
        EmailFragment fragment = new EmailFragment();
        bundle.putString(ARG_NAME, email);
        fragment.setArguments(bundle);
        return fragment;
    }

    private DialogLoadingUtil mDialog;

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.email_fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {
        presenter = new EmailPresenterImpl(this);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        petEmail.setText(getArguments().getString(ARG_NAME));
        baseToolbar.setTitle("修改邮箱");
        initToolbarNav(baseToolbar);

        mDialog=new DialogLoadingUtil(_mActivity);

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
    public void isChangeEmailSuccess(OnUserEvent event) {
        if (TextUtils.equals(event.getRequest(), Constant.REQUEST_EMAIL)) {
            presenter.isChangeEmailSuccess(event.getResult());
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
     * 获取邮箱控件
     *
     * @return
     */
    @Override
    public PowerfulEditText getTextEmail() {
        return petEmail;
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
