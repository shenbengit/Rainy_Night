package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicListContract;
import com.example.ben.rainy_night.fragment.home_frag.presenter.SleepMusicListPresenter;
import com.example.ben.rainy_night.util.NetWorkUtil;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Ben
 * @date 2018/3/28
 */

public class SleepMusicListFragment extends BaseFragment<SleepMusicListContract.Presenter> implements SleepMusicListContract.View {

    private static final String SCENE_TYPE = "scene_type";

    @BindView(R.id.recy_sleep_music_list)
    RecyclerView recySleepMusicList;


    private String sceneType;

    public static SleepMusicListFragment newInstance(String sceneType) {
        Bundle bundle = new Bundle();
        SleepMusicListFragment fragment = new SleepMusicListFragment();
        bundle.putString(SCENE_TYPE, sceneType);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_music_list;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {
        presenter = new SleepMusicListPresenter(this);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
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

    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        presenter.getMusic();
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
        return recySleepMusicList;
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
    public void startBrotherFragment(SupportFragment fragment) {
        assert (getParentFragment()) != null;
        ((SleepMusicFragment) getParentFragment()).startBrotherFragment(fragment);
    }

    @Override
    public int getAPNType() {
        return NetWorkUtil.getInstance().getAPNType(_mActivity.getApplicationContext());
    }
}
