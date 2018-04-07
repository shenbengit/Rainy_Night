package com.example.ben.rainy_night.activity;

import android.content.Context;
import android.content.Intent;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseActivity;
import com.example.ben.rainy_night.fragment.main_frag.frag.MainFragment;
import com.example.ben.rainy_night.service.MusicPlayerService;
import com.gyf.barlibrary.ImmersionBar;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * @author Ben
 */
public class MainActivity extends BaseActivity {

    private ImmersionBar mImmersionBar;

    @Override
    public void setPresenter() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
        //启动播放音乐的服务
        startService(new Intent(this, MusicPlayerService.class));
    }

    @Override
    public void initData() {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.frame_main, MainFragment.newInstance());
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        stopService(new Intent(this, MusicPlayerService.class));
    }
}
