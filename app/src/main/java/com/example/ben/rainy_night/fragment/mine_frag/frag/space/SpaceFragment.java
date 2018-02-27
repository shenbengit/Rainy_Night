package com.example.ben.rainy_night.fragment.mine_frag.frag.space;


import android.view.View;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.flyco.roundview.RoundTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ben
 * @date 2018/2/9
 */

public class SpaceFragment extends BaseBackFragment {

    @BindView(R.id.rtv_post_story)
    RoundTextView rtvPostStory;

    @OnClick({R.id.rtv_post_story})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.rtv_post_story:
                start(PostStoryFragment.newInstance());
                break;
            default:
                break;
        }
    }

    public static SpaceFragment newInstance() {
        SpaceFragment fragment = new SpaceFragment();
        return fragment;
    }

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.space_fragment;
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

}
