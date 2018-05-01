package com.example.ben.rainy_night.fragment.mine_frag.frag.space;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;

/**
 * @author Ben
 * @date 2018/2/9
 */

public class SpaceFragment extends BaseFragment {

    public static SpaceFragment newInstance() {
        return new SpaceFragment();
    }

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_space;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {

    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }

    @Override
    protected boolean isTransparentStatusBar() {
        return true;
    }
}
