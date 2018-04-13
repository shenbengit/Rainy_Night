package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.home_frag.adapter.SleepMusicFragmentAdapter;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Ben
 * @date 2018/3/28
 */

public class SleepMusicFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_sleep_music)
    TabLayout tabSleepMusic;
    @BindView(R.id.vp_sleep_music)
    ViewPager vpSleepMusic;

    public static SleepMusicFragment newInstance() {
        return new SleepMusicFragment();
    }

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_music;
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
        toolbar.setTitle("伴我睡");
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v1 -> _mActivity.onBackPressed());

        tabSleepMusic.addTab(tabSleepMusic.newTab());
        tabSleepMusic.addTab(tabSleepMusic.newTab());
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        vpSleepMusic.setAdapter(new SleepMusicFragmentAdapter(getChildFragmentManager(), "自然音符", "轻音乐"));
        tabSleepMusic.setupWithViewPager(vpSleepMusic);
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }
}
