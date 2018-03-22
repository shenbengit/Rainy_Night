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
import com.example.ben.rainy_night.fragment.mine_frag.contract.NickNameContract;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.NickNamePresenterImpl;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.DialogLoadingUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ben
 * @date 2018/1/26
 */

public class NickNameFragment extends BaseBackFragment<NickNameContract.Presenter> implements NickNameContract.View {

    private static final String ARG_NAME = "arg_name";

    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.pet_petName)
    PowerfulEditText petPetName;
    @BindView(R.id.btn_changePetName)
    Button btnChangePetName;

    @OnClick({R.id.btn_changePetName})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_changePetName:
                if (TextUtils.isEmpty(petPetName.getText().toString().trim())) {
                    toastShow("请输入昵称");
                    return;
                }
                presenter.changeNickName();
                break;
            default:
                break;
        }
    }

    public static NickNameFragment newInstance(String name) {
        Bundle bundle = new Bundle();
        NickNameFragment fragment = new NickNameFragment();
        bundle.putString(ARG_NAME, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    private DialogLoadingUtil mDialog;

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.nick_name_fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {
        presenter = new NickNamePresenterImpl(this);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        petPetName.setText(getArguments().getString(ARG_NAME));
        baseToolbar.setTitle(getString(R.string.change_nickname));
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
    public void isChangeNickNameSuccess(OnUserEvent event) {
        if (TextUtils.equals(event.getRequest(), Constant.REQUEST_NICK_NAME)) {
            presenter.isChangeNickNameSuccess(event.getResult());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        mDialog.cancel();
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

    /**
     * 获取PowerfulEditText
     *
     * @return
     */
    @Override
    public PowerfulEditText getEditText() {
        return petPetName;
    }
}
