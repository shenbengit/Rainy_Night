package com.example.ben.rainy_night.fragment.share_frag.presenter;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.fragment.share_frag.adapter.SleepReadListAdapter;
import com.example.ben.rainy_night.fragment.share_frag.contract.SleepReadContract;
import com.example.ben.rainy_night.fragment.share_frag.frag.analysis.WebViewFragment;
import com.example.ben.rainy_night.http.bmob.entity.SleepMusicEntity;
import com.example.ben.rainy_night.http.bmob.model.SleepMusicModel;
import com.example.ben.rainy_night.http.bmob.model.SleepMusicModelImpl;
import com.example.ben.rainy_night.http.okgo.entity.SleepReadEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.GsonUtil;
import com.example.ben.rainy_night.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/4/22
 */

public class SleepReadPresenterImpl implements SleepReadContract.Presenter {

    private SleepReadContract.View view;
    private SleepMusicModel model;
    private String mTitle;
    private SleepReadEntity mEntity;
    private SleepReadListAdapter mAdapter;
    private List<SleepReadEntity.DataBean> mList;

    private View mViewNetError;
    private View mViewDataError;
    private View mViewLoading;
    private View mViewNoMoreData;

    public SleepReadPresenterImpl(SleepReadContract.View view) {
        this.view = view;
        model = new SleepMusicModelImpl();
    }

    /**
     * 初始化
     * * @param title 标题
     *
     * @param title
     */
    @Override
    public void init(String title) {
        mTitle = title;
        mList = new ArrayList<>();
        mAdapter = new SleepReadListAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(view.getCon(),
                LinearLayoutManager.VERTICAL, false);
        view.getRecycler().setItemAnimator(new DefaultItemAnimator());
        view.getRecycler().setLayoutManager(manager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        view.getRecycler().setAdapter(mAdapter);

        mViewNetError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.layout_net_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewNetError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::requsetSleepReadList, 1000);
        });

        mViewLoading = LayoutInflater.from(view.getCon())
                .inflate(R.layout.layout_loading, (ViewGroup) view.getRecycler().getParent(), false);

        mViewDataError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.layout_data_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewDataError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::requsetSleepReadList, 1000);
        });

        mViewNoMoreData = LayoutInflater.from(view.getCon())
                .inflate(R.layout.layout_no_more_data, (ViewGroup) view.getRecycler().getParent(), false);

        mAdapter.setOnItemClickListener((adapter, v, position) -> view.startBrotherFragment(WebViewFragment.newInstance(mList.get(position).getArticleTitle(), mList.get(position).getArticleUrl())));

    }

    /**
     * 获取睡眠报告列表
     */
    @Override
    public void requsetSleepReadList() {
        if (!view.isNetworkAvailable()) {
            mAdapter.setEmptyView(mViewNetError);
            return;
        }
        if (TextUtils.isEmpty(mTitle)) {
            LoggerUtil.e("SleepReadFragment 请传入标题");
            mAdapter.setEmptyView(mViewDataError);
            return;
        }
        mAdapter.setEmptyView(mViewLoading);
        model.querySleepMusic(mTitle);
    }

    /**
     * 获取睡眠报告列表数据
     *
     * @param result 返回是否成功
     * @param entity 数据
     */
    @Override
    public void getSleepReadListData(String result, SleepMusicEntity entity) {
        if (TextUtils.equals(result, Constant.OK)) {
            if (TextUtils.equals(entity.getTitle(), mTitle)) {
                if (!TextUtils.isEmpty(entity.getJson())) {
                    mList.clear();
                    mEntity = GsonUtil.fromJson(entity.getJson(), SleepReadEntity.class);
                    if (mEntity != null) {
                        mList.addAll(mEntity.getData());
                        if (mList.isEmpty()) {
                            mAdapter.setEmptyView(mViewDataError);
                            return;
                        }
                        mAdapter.setNewData(mList);
                        mAdapter.addFooterView(mViewNoMoreData);
                    } else {
                        mAdapter.setEmptyView(mViewDataError);
                    }
                } else {
                    mAdapter.setEmptyView(mViewDataError);
                }
            }
        } else {
            mAdapter.setEmptyView(mViewDataError);
            view.showToast(result);
        }
    }


    /**
     * 销毁操作
     */
    @Override
    public void onDestroy() {

    }
}
