package com.example.ben.rainy_night.fragment.night_frag.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.fragment.night_frag.adapter.SleepFmAdapter;
import com.example.ben.rainy_night.fragment.night_frag.contract.SleepFmContract;
import com.example.ben.rainy_night.http.okgo.callback.JsonCallBack;
import com.example.ben.rainy_night.http.okgo.entity.SleepFmEntity;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/4/24
 */

public class SleepFmPresenterImpl implements SleepFmContract.Presenter, OnPlayerEventListener {
    private SleepFmContract.View view;

    private SleepFmAdapter mAdapter;
    private List<SleepFmEntity.DataBean.ListBeanX> mLists;
    private View mViewNetError;
    private View mViewDataError;
    private View mViewLoading;
    private View mViewNoMoreData;

    private int mAlbumsId;
    private String mCacheKey;

    private int[] mPageRows = new int[]{ 87, 27,33, 99};
    private int mPageRow;

    private int mFirstItemPosition;
    private int mLastItemPosition;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!mLists.isEmpty()) {
                        mAdapter.setNewData(mLists);
                        mAdapter.addFooterView(mViewNoMoreData);
                    } else {
                        mAdapter.setEmptyView(mViewDataError);
                    }
                    break;
                case 2:
                    if (!mLists.isEmpty()) {
                        mAdapter.setNewData(mLists);
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

    public SleepFmPresenterImpl(SleepFmContract.View view) {
        this.view = view;
        MusicActionManager.getInstance().addPlayerEventListener(this);
    }

    /**
     * 初始化
     *
     * @param albumsId
     */
    @Override
    public void init(int albumsId) {
        mAlbumsId = albumsId;
        switch (mAlbumsId) {
            case Constant.DOLPHIN_HYPNOSIS:
                mPageRow = mPageRows[0];
                mCacheKey = Constant.DOLPHIN_HYPNOSIS_CACHE;
                break;
            case Constant.DOLPHIN_BEFORE_SLEEP_AND_READ:
                mPageRow = mPageRows[1];
                mCacheKey = Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE;
                break;
            case Constant.DOLPHIN_NICE_PEOPLE:
                mPageRow = mPageRows[2];
                mCacheKey = Constant.DOLPHIN_NICE_PEOPLE_CACHE;
                break;
            case Constant.DOLPHIN_SAY_GOOG_NIGHT:
                mPageRow = mPageRows[3];
                mCacheKey = Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE;
                break;
            default:
                mCacheKey = null;
                break;
        }

        mLists = new ArrayList<>();
        mAdapter = new SleepFmAdapter(mLists);
        LinearLayoutManager manager = new LinearLayoutManager(view.getCon(), LinearLayoutManager.VERTICAL, false);
        view.getRecycler().setItemAnimator(new DefaultItemAnimator());
        view.getRecycler().setLayoutManager(manager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        view.getRecycler().setAdapter(mAdapter);

        mViewNetError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_net_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewNetError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::getAlbumsMediaList, 1000);
        });

        mViewLoading = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_loading, (ViewGroup) view.getRecycler().getParent(), false);

        mViewDataError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_data_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewDataError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::getAlbumsMediaList, 1000);
        });

        mViewNoMoreData = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_no_more_data, (ViewGroup) view.getRecycler().getParent(), false);

        view.getRecycler().setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取第一个可见view的位置
                    mFirstItemPosition = linearManager.findFirstVisibleItemPosition();
                    //获取最后一个可见view的位置
                    mLastItemPosition = linearManager.findLastVisibleItemPosition();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view1, position) -> MusicActionManager.getInstance().start(mCacheKey, position));
    }

    /**
     * 获取FM集合
     */
    @Override
    public void getAlbumsMediaList() {
        if (!view.isNetworkAvailable()) {
            mAdapter.setEmptyView(mViewNetError);
            return;
        }
        if (TextUtils.isEmpty(mCacheKey)) {
            mAdapter.setEmptyView(mViewDataError);
            return;
        }
        mAdapter.setEmptyView(mViewLoading);
        OkGo.<SleepFmEntity>get(Constant.DOLPHIN_BASEURL + Constant.DOLPHIN_ALBUMS_MEDIA_LIST)
                .params("albumsId", mAlbumsId)
                .params("appId", "30639")
                .params("pageIndex", "1")
                .params("pageRows", mPageRow)
                .params("timestamp", System.currentTimeMillis())
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .cacheKey(mCacheKey)
                .execute(new JsonCallBack<SleepFmEntity>(SleepFmEntity.class) {
                    @Override
                    public void onSuccess(Response<SleepFmEntity> response) {
                        if (response.body().getCode() == Constant.REQUEST_SUCCESS) {
                            mLists.clear();
                            mLists.addAll(response.body().getData().getList());
                            mHandler.sendEmptyMessage(1);
                            MusicActionManager.getInstance().setData(mCacheKey);
                        } else {
                            mAdapter.setEmptyView(mViewDataError);
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<SleepFmEntity> response) {
                        super.onCacheSuccess(response);
                        if (response.body().getCode() == Constant.REQUEST_SUCCESS) {
                            mLists.clear();
                            mLists.addAll(response.body().getData().getList());
                            mHandler.sendEmptyMessage(2);
                            MusicActionManager.getInstance().setData(mCacheKey);
                        } else {
                            mAdapter.setEmptyView(mViewDataError);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        MusicActionManager.getInstance().removePlayerEventListener(this);
    }

    @Override
    public void onMusicSwitch(SongInfo music) {
        if (view.isVisibleToUser()) {
            if (MusicActionManager.getInstance().getCurrPlayingIndex() < mFirstItemPosition
                    || MusicActionManager.getInstance().getCurrPlayingIndex() > mLastItemPosition) {
                view.getRecycler().smoothScrollToPosition(MusicActionManager.getInstance().getCurrPlayingIndex());
            }
        }
    }

    @Override
    public void onPlayerStart() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPlayerPause() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPlayCompletion() {
//        LoggerUtil.e(view.getCon().getClass().getName() + ",当前歌曲播放完成");
    }

    @Override
    public void onError(String errorMsg) {
        view.showToast(errorMsg);
        LoggerUtil.e("SleepFmPresenterImpl,音乐播放错误: " + errorMsg);
    }

    @Override
    public void onAsyncLoading(boolean isFinishLoading) {

    }
}
