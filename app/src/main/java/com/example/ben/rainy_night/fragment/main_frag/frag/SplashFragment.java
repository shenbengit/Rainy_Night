package com.example.ben.rainy_night.fragment.main_frag.frag;

import android.os.Handler;
import android.os.Looper;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;

/**
 *
 * @author Ben
 * @date 2018/5/20
 */

public class SplashFragment extends BaseFragment {

    public static SplashFragment newInstance(){
        return new SplashFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragent_splash;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> startWithPop(MainFragment.newInstance()),2000);
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return true;
    }
}
