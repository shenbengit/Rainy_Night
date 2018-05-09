package com.example.ben.rainy_night.fragment.mine_frag.frag;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.http.bmob.entity.PostEntity;

/**
 *
 * @author Ben
 * @date 2018/5/9
 */

public class TestFragment extends BaseFragment {
    private static final String ENTITY = "entity";

    public static TestFragment newInstance(PostEntity entity) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_test;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
}
