package com.example.ben.rainy_night.fragment.night_frag.frag;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;


import butterknife.BindView;

/**
 * @author Ben
 */
public class NightFragment extends BaseFragment {



    public static NightFragment newInstance() {
        return new NightFragment();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_night;
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

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }
}
