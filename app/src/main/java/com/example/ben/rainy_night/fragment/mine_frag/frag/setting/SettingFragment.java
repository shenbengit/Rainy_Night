package com.example.ben.rainy_night.fragment.mine_frag.frag.setting;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.mine_frag.contract.SettingContract;
import com.example.ben.rainy_night.fragment.mine_frag.frag.personal.MyPersonalFragment;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.SettingPresenterImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/3/5
 */

public class SettingFragment extends BaseBackFragment<SettingContract.Presenter> implements SettingContract.View {

    @BindView(R.id.view)
    View view;
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.rela_setting_head)
    RelativeLayout relaSettingHead;
    @BindView(R.id.iv_setting_head)
    CircleImageView ivSettingHead;
    @BindView(R.id.tv_setting_name)
    TextView tvSettingName;
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

    @OnClick({R.id.rela_setting_head, R.id.rela_setting_change_password, R.id.rela_setting_about_us,
            R.id.rela_setting_clear_cache, R.id.btn_setting_login_out})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.rela_setting_head:
                start(MyPersonalFragment.newInstance());
                break;
            case R.id.rela_setting_change_password:
                startForResult(ChangePasswordFragment.newInstance(), Constant.REQUEST_POP);
                break;
            case R.id.rela_setting_about_us:
                presenter.aboutUs();
                break;
            case R.id.rela_setting_clear_cache:
                presenter.clearCache();
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

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mUserEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (mUserEntity != null) {
            tvSettingName.setText(mUserEntity.getNickName());
            if (mUserEntity.getHeadimg() != null) {
                GlideApp.with(_mActivity)
                        .load(mUserEntity.getHeadimg().getFileUrl())
                        .placeholder(R.mipmap.ic_head)
                        .error(R.mipmap.ic_head)
                        .into(ivSettingHead);
            } else {
                ivSettingHead.setImageResource(R.mipmap.ic_head);
            }

        }
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
     * @param text 文本
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
