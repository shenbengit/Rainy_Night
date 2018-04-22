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
import com.example.ben.rainy_night.fragment.share_frag.adapter.SleepReportListAdapter;
import com.example.ben.rainy_night.fragment.share_frag.contract.SleepReportContract;
import com.example.ben.rainy_night.http.bmob.entity.SleepAnalysisEntity;
import com.example.ben.rainy_night.http.okgo.entity.SleepReportEntity;
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

public class SleepReportPresenter implements SleepReportContract.Presenter {
    private SleepReportContract.View view;

    private String mTitle;
    private SleepAnalysisEntity mEntity;
    private SleepReportListAdapter mAdapter;
    private List<SleepReportEntity.DataBean> mList;

    private View mViewNetError;
    private View mViewDataError;
    private View mViewLoading;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mEntity != null) {
                        SleepReportEntity entity = GsonUtil.fromJson(mEntity.getJson(), SleepReportEntity.class);
                        mList = entity.getData();
                        if (mList.isEmpty()) {
                            mAdapter.setEmptyView(mViewDataError);
                            return;
                        }
                        mAdapter.setNewData(mList);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public SleepReportPresenter(SleepReportContract.View view) {
        this.view = view;
    }


    /**
     * 初始化Adapter
     *
     * @param title 标题
     */
    @Override
    public void init(String title) {
        mTitle = title;
        mList = new ArrayList<>();
        mAdapter = new SleepReportListAdapter(mList);
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
            new Handler(Looper.getMainLooper()).postDelayed(this::getSleepReportList, 1000);
        });

        mViewLoading = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_loading, (ViewGroup) view.getRecycler().getParent(), false);

        mViewDataError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_data_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewDataError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::getSleepReportList, 1000);
        });
    }

    /**
     * 获取睡眠报告列表
     */
    @Override
    public void getSleepReportList() {
        if (!view.isNetworkAvailable()) {
            mAdapter.setEmptyView(mViewNetError);
            return;
        }

        if (TextUtils.isEmpty(mTitle)) {
            LoggerUtil.e("SleepReportFragment 请传入标题");
            mAdapter.setEmptyView(mViewDataError);
            return;
        }

        BmobQuery<SleepAnalysisEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("title", mTitle);
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findObjects(new FindListener<SleepAnalysisEntity>() {
            @Override
            public void done(List<SleepAnalysisEntity> list, BmobException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
                        mEntity = list.get(0);
                        mHandler.sendEmptyMessage(1);
                    }
                } else {
                    LoggerUtil.e("SleepReportFragment: " + e.getMessage());
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
