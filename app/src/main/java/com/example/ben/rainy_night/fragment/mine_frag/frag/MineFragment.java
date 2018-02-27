package com.example.ben.rainy_night.fragment.mine_frag.frag;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.main_frag.frag.MainFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.RegisterFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.personal.MyPersonalFragment;
import com.example.ben.rainy_night.fragment.mine_frag.frag.space.SpaceFragment;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.MinePresenter;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.MinePresenterImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IMineView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
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

    @OnClick({R.id.civ_mine_head, R.id.civ_mine_setting,R.id.rela_space})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.civ_mine_head:
                if (TextUtils.equals(objectId, "")) {
                    ((MainFragment) getParentFragment()).startBrotherFragment(RegisterFragment.newInstance());
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

    }

    /**
     * 此Fragment对用户可见时调用
     */
    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        objectId = String.valueOf(getSharedPreferences(SharedPreferencesUtil.USER_OBJECT_ID, ""));
        if (TextUtils.equals(objectId, "")) {
            civMineHead.setImageDrawable(getResources().getDrawable(R.mipmap.ic_head));
            tvMineName.setText(getString(R.string.login_register));
            return;
        }
        presenter.getUserInformation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void isGetUserInformationSuccess(OnUserEvent event) {
        if (TextUtils.equals(event.getRequest(), ConstantUtil.REQUEST_MINE)) {
            presenter.isGetUserInformationSuccess(event.getResult(), event.getBean());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)){
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
