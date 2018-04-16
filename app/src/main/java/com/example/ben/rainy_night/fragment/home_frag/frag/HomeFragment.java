package com.example.ben.rainy_night.fragment.home_frag.frag;


import android.view.View;
import android.widget.Button;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.home_frag.frag.music.SleepMusicFragment;
import com.example.ben.rainy_night.fragment.main_frag.frag.MainFragment;
import com.tencent.bugly.crashreport.CrashReport;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Ben
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.btn_home_sleep)
    Button btnHomeSleep;

    @OnClick({R.id.btn_home_sleep})
    public void viewOnClick(View view) {
        assert (getParentFragment()) != null;
        switch (view.getId()) {
            case R.id.btn_home_sleep:
                ((MainFragment) getParentFragment()).startBrotherFragment(SleepMusicFragment.newInstance());
                break;
            default:
                break;
        }
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    /**
     * 返回界面layout
     */
    @Override
    public int getLayout() {
        return R.layout.fragment_home;
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

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }
}
