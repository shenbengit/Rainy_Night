package com.example.ben.rainy_night.fragment.mine_frag.frag;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.main_frag.frag.MainFragment;
import com.example.ben.rainy_night.fragment.mine_frag.contract.MineContract;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.personal.MyPersonalFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.setting.ScanerCodeFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.setting.SettingFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.space.SpaceFragment;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.MinePresenterImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;
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
public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View {

    @BindView(R.id.civ_mine_head)
    CircleImageView civMineHead;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.iv_mine_scan)
    ImageView ivMineScan;

    @OnClick({R.id.civ_mine_head, R.id.iv_mine_scan, R.id.rela_space, R.id.rela_setting})
    public void viewOnClick(View view) {
        assert (getParentFragment()) != null;
        switch (view.getId()) {
            case R.id.civ_mine_head:
                if (mUserEntity == null) {
                    ((MainFragment) getParentFragment()).startBrotherFragment(LoginFragment.newInstance());
                } else {
                    ((MainFragment) getParentFragment()).startBrotherFragment(MyPersonalFragment.newInstance());
                }
                break;
            case R.id.iv_mine_scan:
                ((MainFragment) getParentFragment()).startBrotherFragment(ScanerCodeFragment.newInstance());
                break;
            case R.id.rela_space:
                ((MainFragment) getParentFragment()).startBrotherFragment(SpaceFragment.newInstance());
                break;
            case R.id.rela_setting:
                ((MainFragment) getParentFragment()).startBrotherFragment(SettingFragment.newInstance());
                break;
            default:
                break;
        }
    }

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_mine;
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
        if (mUserEntity != null) {
            String account = mUserEntity.getMobilePhoneNumber();
            String password = String.valueOf(SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).getValue(Constant.USER_PASSWORD, ""));
            presenter.loginUser(Constant.REQUEST_LOGIN_MINE, account, password);
        }
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    /**
     * 此Fragment对用户可见时调用
     */
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mUserEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (mUserEntity != null) {
            tvMineName.setText(mUserEntity.getNickName());
            if (mUserEntity.getHeadimg() != null) {
                GlideApp.with(_mActivity)
                        .load(mUserEntity.getHeadimg().getFileUrl())
                        .placeholder(R.mipmap.ic_head)
                        .error(R.mipmap.ic_head)
                        .into(civMineHead);
            } else {
                civMineHead.setImageResource(R.mipmap.ic_head);
            }
        } else {
            civMineHead.setImageResource(R.mipmap.ic_head);
            tvMineName.setText(getString(R.string.login_register));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void isLoginSuccess(OnUserEvent event) {
        if (TextUtils.equals(event.getRequest(), Constant.REQUEST_LOGIN_MINE)) {
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

}
