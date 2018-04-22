package com.example.ben.rainy_night.fragment.share_frag.frag.analysis;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.share_frag.contract.SleepReportContract;
import com.example.ben.rainy_night.fragment.share_frag.presenter.SleepReportPresenter;

import butterknife.BindView;

/**
 * @author Ben
 * @date 2018/4/22
 */

public class SleepReportFragment extends BaseFragment<SleepReportContract.Presenter> implements SleepReportContract.View {

    private static final String TITLE = "title";

    @BindView(R.id.recy_sleep_repost_list)
    RecyclerView recySleepRepostList;

    public static SleepReportFragment newInstance(String title) {
        SleepReportFragment fragment = new SleepReportFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mTitle;

    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_report;
    }


    @Override
    protected void setPresenter() {
        presenter = new SleepReportPresenter(this);
    }


    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(TITLE);
        }

        presenter.init(mTitle);
    }


    @Override
    protected void initData() {
        presenter.getSleepReportList();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
        return recySleepRepostList;
    }
}
