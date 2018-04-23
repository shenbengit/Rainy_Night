package com.example.ben.rainy_night.fragment.share_frag.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.main_frag.frag.MainFragment;
import com.example.ben.rainy_night.fragment.share_frag.adapter.SleepAnalysisFragmentAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Ben
 */
public class ShareFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_share_date)
    TextView tvShareDate;
    @BindView(R.id.tab_sleep_analysis)
    TabLayout tabSleepAnalysis;
    @BindView(R.id.vp_sleep_analysis)
    ViewPager vpSleepAnalysis;

    public static ShareFragment newInstance() {
        return new ShareFragment();
    }

    /**
     * 返回界面layout
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_share;
    }

    /**
     * 设置presenter
     */
    @Override
    public void setPresenter() {

    }

    /**
     * 初始化界面
     */
    @Override
    public void initView() {
        tabSleepAnalysis.addTab(tabSleepAnalysis.newTab());
        tabSleepAnalysis.addTab(tabSleepAnalysis.newTab());
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        String day = format.format(date);
        format = new SimpleDateFormat("EEEE", Locale.CHINA);
        String weekDay = format.format(date);

        tvShareDate.setText(day + "\t" + weekDay);
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        vpSleepAnalysis.setAdapter(new SleepAnalysisFragmentAdapter(getChildFragmentManager(), "推荐阅读", "睡眠报告"));
        tabSleepAnalysis.setupWithViewPager(vpSleepAnalysis);
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        assert getParentFragment() != null;
        ((MainFragment) getParentFragment()).startBrotherFragment(targetFragment);
    }
}
