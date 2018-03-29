package com.example.ben.rainy_night.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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
    protected ImmersionBar mImmersionBar;
    protected UserEntity mUserEntity;
    private Unbinder unbinder = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mImmersionBar = ImmersionBar.with(_mActivity);
        View v = view.findViewById(setStatusBarView());
        if (v != null) {
            ImmersionBar.setStatusBarView(_mActivity, v);
        }
        mUserEntity = BmobUser.getCurrentUser(UserEntity.class);
        setPresenter();
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mImmersionBar.statusBarColor(R.color.colorPrimaryDark).init();
    }

    protected int setStatusBarView() {
        return R.id.view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //fragment 销毁时ButterKnife解绑
        unbinder.unbind();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        RefWatcher refWatcher = App.getRefWatcher(_mActivity);
        refWatcher.watch(this);
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
