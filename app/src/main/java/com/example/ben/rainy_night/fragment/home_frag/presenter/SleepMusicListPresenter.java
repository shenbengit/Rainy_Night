package com.example.ben.rainy_night.fragment.home_frag.presenter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.fragment.home_frag.adapter.SleepMusicListAdapter;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicListContract;
import com.example.ben.rainy_night.fragment.home_frag.frag.music.SleepMusicAudioFragment;
import com.example.ben.rainy_night.http.okgo.callback.JsonCallBack;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.util.Constant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/3/28
 */

public class SleepMusicListPresenter implements SleepMusicListContract.Presenter {

    private SleepMusicListContract.View view;
    private SleepMusicListAdapter mAdapter;
    private List<MusicEntity.DataBean> mLists;
    private View mViewNetError;
    private View mViewDataError;
    private View mViewLoading;
    /**
     * 音乐类型
     */
    private String mSceneType;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mAdapter.setNewData(mLists);
                    break;
                case 2:
                    mAdapter.setNewData(mLists);
                    break;
                default:
                    break;
            }
        }
    };

    public SleepMusicListPresenter(SleepMusicListContract.View view) {
        this.view = view;
    }

    @Override
    public void initRecyclerView(String sceneType) {
        mSceneType = sceneType;

        mLists = new ArrayList<>();
        mAdapter = new SleepMusicListAdapter(mLists);
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
            new Handler().postDelayed(this::getMusic, 1000);
        });

        mViewLoading = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_loading, (ViewGroup) view.getRecycler().getParent(), false);

        mViewDataError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.item_data_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewDataError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler().postDelayed(this::getMusic, 1000);
        });

        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            if (mLists.isEmpty()) {
                return;
            }

            if (TextUtils.equals(sceneType, String.valueOf(Constant.HAITUN_NATURAL_MUSIC))) {
                view.startBrotherFragment(SleepMusicAudioFragment.newInstance(mLists.get(position).getVideoPictureUrl(),
                        mLists.get(position).getVideoUrl(), mLists.get(position).getAudioUrl()));
            } else if (TextUtils.equals(sceneType, String.valueOf(Constant.HAITUN_LIGHT_MUSIC))) {
                view.startBrotherFragment(SleepMusicAudioFragment.newInstance(mLists.get(position).getAudioPictureUrl(),
                        null, mLists.get(position).getAudioUrl()));
            }

        });
    }

    @Override
    public void getMusic() {
        if (!view.isNetworkAvailable()) {
            mAdapter.setEmptyView(mViewNetError);
            return;
        }
        if (TextUtils.isEmpty(mSceneType)) {
            mAdapter.setEmptyView(mViewDataError);
            return;
        }
        OkGo.<MusicEntity>get(Constant.HAITUN_BASEURL + Constant.HAITUN_MUSIC)
                .params("sceneType", mSceneType)
                .params("timestamp", System.currentTimeMillis())
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .cacheKey("dolphin_music" + mSceneType)
                .execute(new JsonCallBack<MusicEntity>(MusicEntity.class) {
                    @Override
                    public void onSuccess(Response<MusicEntity> response) {
                        if (response.body().getCode() == 0) {
                            mLists = response.body().getData();
                            mHandler.sendEmptyMessage(1);
                        } else {
                            mAdapter.setEmptyView(mViewDataError);
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<MusicEntity> response) {
                        super.onCacheSuccess(response);
                        if (response.body().getCode() == 0) {
                            mLists = response.body().getData();
                            mHandler.sendEmptyMessage(2);
                        } else {
                            mAdapter.setEmptyView(mViewDataError);
                        }
                    }
                });
    }
}
