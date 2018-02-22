package com.example.ben.rainy_night.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ben.rainy_night.util.DialogLoadingUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.example.ben.rainy_night.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Ben
 * @date 2018/1/3
 */

public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment {
    public T presenter;
    private Unbinder unbinder = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
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
    public void onDestroy() {
        super.onDestroy();
        //fragment 销毁时ButterKnife解绑
        unbinder.unbind();
        DialogLoadingUtil.getInstance(_mActivity).cancel();
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

    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     *
     * @param keyName      键
     * @param defaultValue 默认值
     * @return
     */
    protected Object getSharedPreferences(String keyName, Object defaultValue) {
        return SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).getValue(keyName, defaultValue);
    }
}
