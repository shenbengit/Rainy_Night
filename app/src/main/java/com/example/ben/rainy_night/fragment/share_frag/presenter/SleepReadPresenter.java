package com.example.ben.rainy_night.fragment.share_frag.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.example.ben.rainy_night.http.okgo.entity.SleepReadEntity;
import com.example.ben.rainy_night.util.GsonUtil;
import com.example.ben.rainy_night.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Ben
 * @date 2018/4/22
 */

public class SleepReadPresenter implements SleepReadContract.Presenter {

    private SleepReadContract.View view;

    private String mTitle;
    private SleepMusicEntity mEntity;
    private SleepReadListAdapter mAdapter;
    private List<SleepReadEntity.DataBean> mList;

    private View mViewNetError;
    private View mViewDataError;
    private View mViewLoading;
    private View mViewNoMoreData;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mEntity != null) {
                        mList.clear();
                        SleepReadEntity entity = GsonUtil.fromJson(mEntity.getJson(), SleepReadEntity.class);
                        mList.addAll(entity.getData());
                        if (mList.isEmpty()) {
                            mAdapter.setEmptyView(mViewDataError);
                            return;
                        }
                        mAdapter.setNewData(mList);
                        mAdapter.addFooterView(mViewNoMoreData);
                    } else {
                        mAdapter.setEmptyView(mViewDataError);
                    }
                    break;
                default:
                    break;
            }
        }
    };


    public SleepReadPresenter(SleepReadContract.View view) {
        this.view = view;
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
                .inflate(R.layout.item_net_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewNetError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::getSleepReadList, 1000);
        });

        mViewLoading = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_loading, (ViewGroup) view.getRecycler().getParent(), false);

        mViewDataError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_data_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewDataError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::getSleepReadList, 1000);
        });

        mViewNoMoreData = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_no_more_data, (ViewGroup) view.getRecycler().getParent(), false);

        mAdapter.setOnItemClickListener((adapter, v, position) -> view.startBrotherFragment(WebViewFragment.newInstance(mList.get(position).getArticleTitle(), mList.get(position).getArticleUrl())));

    }

    /**
     * 获取睡眠报告列表
     */
    @Override
    public void getSleepReadList() {
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
        BmobQuery<SleepMusicEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("title", mTitle);
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findObjects(new FindListener<SleepMusicEntity>() {
            @Override
            public void done(List<SleepMusicEntity> list, BmobException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
                        mEntity = list.get(0);
                        mHandler.sendEmptyMessage(1);
                    }
                } else {
                    LoggerUtil.e("SleepReadFragment: " + e.getMessage());
                }
            }
        });
    }

    /**
     * 销毁操作
     */
    @Override
    public void onDestroy() {
        mHandler.removeMessages(1);
    }
}
