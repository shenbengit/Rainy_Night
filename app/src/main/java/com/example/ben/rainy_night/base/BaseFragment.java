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
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.NetWorkUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Ben
 * @date 2018/1/3
 */

public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment {
    protected T presenter;
    protected UserEntity mUserEntity;
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder = null;
    private View mStatusView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserEntity = BmobUser.getCurrentUser(UserEntity.class);
        mImmersionBar = ImmersionBar.with(this);
        mStatusView = view.findViewById(setStatusBarView());
        if (mStatusView != null) {
            ImmersionBar.setStatusBarView(_mActivity, mStatusView);
        }
        setPresenter();
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isTransparentStatusBar()) {
            mImmersionBar.transparentStatusBar().init();
        } else {
            if (mStatusView != null) {
                mImmersionBar.statusBarColor(R.color.colorPrimaryDark).init();
            }
        }
    }

    protected int setStatusBarView() {
        return R.id.view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //fragment 销毁时ButterKnife解绑
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        RefWatcher refWatcher = App.getRefWatcher(_mActivity);
        refWatcher.watch(this);
    }

    /**
     * 获取界面布局
     *
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
     * @return 是 或 否
     */
    protected abstract boolean isTransparentStatusBar();

    /**
     * 设置ToolBar
     *
     * @param toolbar 传入子类中的ToolBar
     */
    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v1 -> _mActivity.onBackPressed());
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
     * 当前网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    protected boolean isNetAvailable() {
        return NetWorkUtil.getInstance().isNetworkAvailable(_mActivity.getApplicationContext());
    }
}