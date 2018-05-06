package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.fragment.event.OnPostEvent;
import com.example.ben.rainy_night.fragment.mine_frag.adapter.SpaceAdapter;
import com.example.ben.rainy_night.fragment.mine_frag.contract.SpaceContract;
import com.example.ben.rainy_night.fragment.mine_frag.model.PostModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.PostModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.PostEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Ben
 * @date 2018/5/4
 */

public class SpacePresenterImpl implements SpaceContract.Presenter, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SpaceContract.View view;
    private PostModel model;
    private List<PostEntity> mList;

    private SpaceAdapter mAdapter;
    private View mViewNetError;
    private View mViewDataError;
    private View mViewLoading;
    private View mViewNoMoreData;

    private boolean isLoadFirst = true;

    private String mTimeRefresh;
    private String mTimeLoadMore;

    public SpacePresenterImpl(SpaceContract.View view) {
        this.view = view;
        model = new PostModelImpl();
    }

    @Override
    public void init() {
        mList = new ArrayList<>();
        mAdapter = new SpaceAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(view.getCon(),
                LinearLayoutManager.VERTICAL, false);
        view.getRecycler().setLayoutManager(manager);
        mAdapter.isFirstOnly(false);
        view.getRecycler().setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, view.getRecycler());
        view.getSwipeRefresh().setOnRefreshListener(this);

        mViewNetError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_net_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewNetError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::loadData, 1000);
        });

        mViewLoading = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_loading, (ViewGroup) view.getRecycler().getParent(), false);

        mViewDataError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_data_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewDataError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::loadData, 1000);
        });

        mViewNoMoreData = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_no_more_data, (ViewGroup) view.getRecycler().getParent(), false);
    }

    @Override
    public void loadData() {
        if (!view.isNetworkAvailable()) {
            mAdapter.setEmptyView(mViewNetError);
            return;
        }
        mAdapter.setEmptyView(mViewLoading);
        setRefreshing(true);
        onRefresh();
    }

    @Override
    public void getPostData(OnPostEvent event) {
        if (TextUtils.equals(event.getResult(), Constant.OK)) {
            LoggerUtil.e("刷新动作: " + event.getAction());
            if (TextUtils.equals(event.getAction(), Constant.REQUSET_POST_REFRESH)) {
                if (event.getList() == null || event.getList().isEmpty()) {
                    if (isLoadFirst) {
                        mAdapter.setEmptyView(mViewDataError);
                    } else {
                        view.showToast("暂时没有新内容了");
                    }
                } else {
                    mAdapter.setNewData(event.getList());
                    isLoadFirst = false;
                    mTimeLoadMore = event.getList().get(event.getList().size() - 1).getCreatedAt();
                }
                setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
            } else if (TextUtils.equals(event.getAction(), Constant.REQUSET_POST_LOAD_MORE)) {
                if (event.getList() == null || event.getList().isEmpty()) {
                    mAdapter.loadMoreEnd();
                } else {
                    mTimeLoadMore = event.getList().get(event.getList().size() - 1).getCreatedAt();
                    mAdapter.addData(event.getList());
                    mAdapter.loadMoreComplete();
                }
            }
        } else {
            if (TextUtils.equals(event.getAction(), Constant.REQUSET_POST_REFRESH)) {
                if (isLoadFirst) {
                    mAdapter.setEmptyView(mViewDataError);
                }
            } else if (TextUtils.equals(event.getAction(), Constant.REQUSET_POST_LOAD_MORE)) {
                mAdapter.loadMoreFail();
            }
            setRefreshing(false);
            mAdapter.setEnableLoadMore(true);
            view.showToast(event.getResult());
        }
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);
        mTimeRefresh = getDateToString(System.currentTimeMillis());
        model.queryPost(Constant.REQUSET_POST_REFRESH, mTimeRefresh);
    }

    @Override
    public void onLoadMoreRequested() {
        model.queryPost(Constant.REQUSET_POST_LOAD_MORE, mTimeLoadMore);
    }

    private void setRefreshing(boolean refreshing) {
        view.getSwipeRefresh().post(() -> view.getSwipeRefresh().setRefreshing(refreshing));
    }

    private String getDateToString(long milSecond) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(milSecond);
        return format.format(date);
    }
}
