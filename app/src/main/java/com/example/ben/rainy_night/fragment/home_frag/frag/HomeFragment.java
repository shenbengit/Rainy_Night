package com.example.ben.rainy_night.fragment.home_frag.frag;

import android.os.Bundle;
import android.util.Log;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;

/**
 * @author Ben
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 返回界面layout
     */
    @Override
    public int getLayout() {
        return R.layout.home_fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    public void setPresenter() {

    }

    /**
     * 初始化界面
     */
    @Override
    public void initView() {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }
}
