package com.example.ben.rainy_night.fragment.share_frag.frag.analysis;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.event.OnSleepMusicEvent;
import com.example.ben.rainy_night.fragment.share_frag.contract.SleepReadContract;
import com.example.ben.rainy_night.fragment.share_frag.frag.ShareFragment;
import com.example.ben.rainy_night.fragment.share_frag.presenter.SleepReadPresenterImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/4/22
 */

public class SleepReadFragment extends BaseFragment<SleepReadContract.Presenter> implements SleepReadContract.View {

    private static final String TITLE = "title";

    @BindView(R.id.recy_sleep_read_list)
    RecyclerView recySleepReadList;

    public static SleepReadFragment newInstance(String title) {
        SleepReadFragment fragment = new SleepReadFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mTitle;

    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_read;
    }

    @Override
    protected void setPresenter() {
        presenter = new SleepReadPresenterImpl(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(TITLE);
        }
        presenter.init(mTitle);
    }

    @Override
    protected void initData() {
        presenter.requsetSleepReadList();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getReadListData(OnSleepMusicEvent event) {
        presenter.getSleepReadListData(event.getResult(), event.getEntity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        presenter.onDestroy();
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
     * 获取Context
     *
     * @return
     */
    @Override
    public Context getCon() {
        return _mActivity;
    }

    /**
     * 获取RecyclerView
     *
     * @return
     */
    @Override
    public RecyclerView getRecycler() {
        return recySleepReadList;
    }

    /**
     * 从兄弟fragment进行跳转
     *
     * @param fragment 要跳转的fragment
     */
    @Override
    public void startBrotherFragment(ISupportFragment fragment) {
        assert getParentFragment() != null;
        ((ShareFragment) getParentFragment()).startBrotherFragment(fragment);
    }
}
