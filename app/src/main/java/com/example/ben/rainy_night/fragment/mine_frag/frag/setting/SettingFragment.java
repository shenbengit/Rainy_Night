package com.example.ben.rainy_night.fragment.mine_frag.frag.setting;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.SettingPresenter;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.SettingPresenterImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.ISettingView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/3/5
 */

public class SettingFragment extends BaseBackFragment<SettingPresenter> implements ISettingView {

    @BindView(R.id.view)
    View view;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.rela_setting_change_password)
    RelativeLayout relaSettingChangePassword;
    @BindView(R.id.rela_setting_about_us)
    RelativeLayout relaSettingAboutUs;
    @BindView(R.id.tv_setting_cache)
    TextView tvSettingCache;
    @BindView(R.id.rela_setting_clear_cache)
    RelativeLayout relaSettingClearCache;
    @BindView(R.id.btn_setting_login_out)
    Button linearSettingLoginOut;

    @OnClick({R.id.rela_setting_change_password, R.id.rela_setting_about_us,
            R.id.rela_setting_clear_cache, R.id.btn_setting_login_out})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.rela_setting_change_password:
                startForResult(ChangePasswordFragment.newInstance(), ConstantUtil.REQUEST_POP);
                break;
            case R.id.rela_setting_about_us:

                break;
            case R.id.rela_setting_clear_cache:

                break;
            case R.id.btn_setting_login_out:
                presenter.loginOut();
                break;
            default:
                break;
        }
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.setting_fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {
        presenter = new SettingPresenterImpl(this);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        baseToolbar.setTitle(R.string.setting);
        initToolbarNav(baseToolbar);
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

    /**
     * 显示Toast
     *
     * @param text 文本
     */
    @Override
    public void showToast(String text) {
        toastShow(text);
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
     * 跳转到指定fragment,并关闭自己
     *
     * @param fragment 要跳转到的fragment
     */
    @Override
    public void startWithPopToFragment(ISupportFragment fragment) {
        startWithPop(fragment);
    }

    /**
     * 清空SP
     */
    @Override
    public void clearSP() {
        SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).clear();
    }
}
