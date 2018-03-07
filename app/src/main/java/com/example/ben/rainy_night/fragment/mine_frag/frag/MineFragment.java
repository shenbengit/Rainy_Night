package com.example.ben.rainy_night.fragment.mine_frag.frag;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.main_frag.frag.MainFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.personal.MyPersonalFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.space.SpaceFragment;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.MinePresenter;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.MinePresenterImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IMineView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author Ben
 */
public class MineFragment extends BaseFragment<MinePresenter> implements IMineView {

    @BindView(R.id.civ_mine_head)
    CircleImageView civMineHead;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.civ_mine_setting)
    CircleImageView civMineSetting;
    @BindView(R.id.rela_space)
    RelativeLayout relaSpace;

    @OnClick({R.id.civ_mine_head, R.id.civ_mine_setting, R.id.rela_space})
    public void viewOnClick(View view) {
        assert ((MainFragment) getParentFragment()) != null;
        switch (view.getId()) {
            case R.id.civ_mine_head:
                if (mUserBean == null) {
                    ((MainFragment) getParentFragment()).startBrotherFragment(LoginFragment.newInstance());
                } else {
                    ((MainFragment) getParentFragment()).startBrotherFragment(MyPersonalFragment.newInstance());
                }
                break;
            case R.id.civ_mine_setting:
                ((MainFragment) getParentFragment()).startBrotherFragment(LoginFragment.newInstance());
                break;
            case R.id.rela_space:
                ((MainFragment) getParentFragment()).startBrotherFragment(SpaceFragment.newInstance());
                break;
            default:
                break;
        }
    }

    private String objectId = "";
    private String account = "";
    private String password = "";

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.mine_fragment;
    }

    @Override
    public void setPresenter() {
        presenter = new MinePresenterImpl(this);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        if (mUserBean != null) {
            account = mUserBean.getMobilePhoneNumber();
            password = String.valueOf(SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).getValue(SharedPreferencesUtil.USER_PASSWORD, ""));
            presenter.loginUser(ConstantUtil.REQUEST_LOGIN_MINE, account, password);
        } else {
            civMineHead.setImageResource(R.mipmap.ic_head);
            tvMineName.setText(getString(R.string.login_register));
        }


    }

    /**
     * 此Fragment对用户可见时调用
     */
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mUserBean = BmobUser.getCurrentUser(UserBean.class);
        if (mUserBean != null) {
            tvMineName.setText(mUserBean.getNickName());
            if (mUserBean.getHeadimg() != null) {
                GlideApp.with(_mActivity)
                        .load(mUserBean.getHeadimg().getFileUrl())
                        .placeholder(R.mipmap.ic_head)
                        .error(R.mipmap.ic_head)
                        .into(civMineHead);
            } else {
                civMineHead.setImageResource(R.mipmap.ic_head);
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void isLoginSuccess(OnUserEvent event) {
        if (TextUtils.equals(event.getRequest(), ConstantUtil.REQUEST_LOGIN_MINE)) {
            presenter.isLoginSuccess(event.getResult(), event.getBean());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * @return FragmentActivity实例
     */
    @Override
    public FragmentActivity getFragmentActivity() {
        return this._mActivity;
    }

    /**
     * @return img控件
     */
    @Override
    public CircleImageView getHeadImg() {
        return this.civMineHead;
    }

    /**
     * @return username控件
     */
    @Override
    public TextView getTextUser() {
        return this.tvMineName;
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
        return dialogIsShowing();
    }

    /**
     * 关闭网络加载Dialog
     */
    @Override
    public void cancelDialog() {
        dialogCancel();
    }
}
