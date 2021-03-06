package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.event.OnSleepMusicEvent;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepNaturalMusicContract;
import com.example.ben.rainy_night.fragment.home_frag.presenter.SleepNaturalMusicPresenterImpl;
import com.example.ben.rainy_night.util.NetWorkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 *
 * @author Ben
 * @date 2018/5/13
 */

public class SleepNaturalMusicFragment extends BaseFragment<SleepNaturalMusicContract.Presenter> implements SleepNaturalMusicContract.View{

    private static final String SCENE_TYPE = "scene_type";
    @BindView(R.id.recy_sleep_natutal_music)
    RecyclerView recySleepNaturalMusic;
    private String sceneType;

    public static SleepNaturalMusicFragment newInstance(String sceneType) {
        Bundle bundle = new Bundle();
        SleepNaturalMusicFragment fragment = new SleepNaturalMusicFragment();
        bundle.putString(SCENE_TYPE, sceneType);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_natural_sleep_music;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {
        presenter = new SleepNaturalMusicPresenterImpl(this);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            sceneType = bundle.getString(SCENE_TYPE);
        }
        presenter.initRecyclerView(sceneType);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        presenter.requsetMusic();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getNaturalMusicData(OnSleepMusicEvent event) {
        presenter.getNaturalMusicData(event.getResult(), event.getEntity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 当前网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    @Override
    public boolean isNetworkAvailable() {
        return isNetAvailable();
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    @Override
    public void showToast(String text) {
        toastShow(text);
    }

    /**
     * 显示网络加载Dialog
     */
    @Override
    public void showDialog() {

    }

    /**
     * 关闭网络加载Dialog
     */
    @Override
    public void cancelDialog() {

    }

    /**
     * 获取RecyclerView
     *
     * @return
     */
    @Override
    public RecyclerView getRecycler() {
        return recySleepNaturalMusic;
    }

    /**
     * 获取Context
     *
     * @return
     */
    @Override
    public Context getCon() {
        return _mActivity;
    }

    @Override
    public void startBrotherFragment(ISupportFragment fragment) {
        assert (getParentFragment()) != null;
        ((SleepMusicFragment) getParentFragment()).startBrotherFragment(fragment);
    }

    @Override
    public int getAPNType() {
        return NetWorkUtil.getInstance().getAPNType(_mActivity.getApplicationContext());
    }
}
