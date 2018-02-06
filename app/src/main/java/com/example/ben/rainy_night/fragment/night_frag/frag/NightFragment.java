package com.example.ben.rainy_night.fragment.night_frag.frag;

import android.os.Bundle;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;

/**
 * @author Ben
 */
public class NightFragment extends BaseFragment {

    public static NightFragment newInstance() {
        Bundle args = new Bundle();
        NightFragment fragment = new NightFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.night_fragment;
    }

    @Override
    public void setPresenter() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
