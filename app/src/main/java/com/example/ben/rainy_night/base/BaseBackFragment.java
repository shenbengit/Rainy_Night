package com.example.ben.rainy_night.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ben.rainy_night.App;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.util.DialogLoadingUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * 有进入、返回动画的Fragment继承在、此类
 *
 * @author Ben
 * @date 2018/1/25
 */

public abstract class BaseBackFragment<T extends BasePresenter> extends SwipeBackFragment {
    protected T presenter;
    protected ImmersionBar mImmersionBar;
    protected UserBean mUserBean;
    private Unbinder unbinder = null;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mImmersionBar = ImmersionBar.with(_mActivity);
        v = view.findViewById(setStatusBarView());
        mUserBean = BmobUser.getCurrentUser(UserBean.class);
        setPresenter();
        initView();
        return attachToSwipeBack(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setParallaxOffset(0.5f);
        initData();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isTransparentStatusBar()) {
            mImmersionBar.transparentStatusBar().init();
        } else {
            if (v != null) {
                ImmersionBar.setStatusBarView(_mActivity, v);
            }
            mImmersionBar.statusBarColor(R.color.colorPrimary).init();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //fragment 销毁时ButterKnife解绑
        unbinder.unbind();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        RefWatcher refWatcher = App.getRefWatcher(_mActivity);
        refWatcher.watch(this);
    }

    protected int setStatusBarView() {
        return R.id.view;
    }

    /**
     * @return 返回界面layout
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * 设置presenter
     */
    protected abstract void setPresenter();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 是否透明化状态栏
     *
     * @return
     */
    protected abstract boolean isTransparentStatusBar();

    /**
     * 设置ToolBar
     *
     * @param toolbar 传入子类中的ToolBar
     */
    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    protected void toastShow(String text) {
        ToastUtil.show(_mActivity.getApplicationContext(), text);
    }

    /**
     * 显示网络加载Dialog
     */
    protected void dialogShow() {
        DialogLoadingUtil.getInstance(_mActivity).show();
    }

    /**
     * @return 网络加载Dialog是否正在显示
     */
    protected boolean dialogIsShow() {
        return DialogLoadingUtil.getInstance(_mActivity).isShowing();
    }

    /**
     * 关闭网络加载Dialog
     */
    protected void dialogCancel() {
        DialogLoadingUtil.getInstance(_mActivity).cancel();
    }

    /**
     * 使用SharedPreferences存储信息
     *
     * @param keyName 键
     * @param value   值
     */
    protected void putSharedPreferences(String keyName, Object value) {
        SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).putValue(keyName, value);
    }
}
