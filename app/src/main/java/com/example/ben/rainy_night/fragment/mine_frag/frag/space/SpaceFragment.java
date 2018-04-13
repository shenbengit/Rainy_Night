package com.example.ben.rainy_night.fragment.mine_frag.frag.space;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Ben
 * @date 2018/2/9
 */

public class SpaceFragment extends BaseFragment {


    @BindView(R.id.image)
    ImageView image;

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
        GlideApp.with(_mActivity).load("http://fileserver1.clife.net:8080/group1/M00/23/AA/Cvtlp1n67bSAWyLkAAHhZ23UF1k330.png").into(image);
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return true;
    }
}
