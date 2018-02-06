package com.example.ben.rainy_night.activity;

import android.content.Intent;
import android.view.KeyEvent;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseActivity;
import com.example.ben.rainy_night.fragment.main_frag.frag.MainFragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * @author Ben
 */
public class MainActivity extends BaseActivity {

    @Override
    public void setPresenter() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

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

}
