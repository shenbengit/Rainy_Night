package com.example.ben.rainy_night.fragment.home_frag.presenter;

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
import com.example.ben.rainy_night.fragment.home_frag.adapter.SleepMusicListAdapter;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepLightMusicContract;
import com.example.ben.rainy_night.fragment.home_frag.frag.music.SleepMusicAudioFragment;
import com.example.ben.rainy_night.http.bmob.entity.SleepMusicEntity;
import com.example.ben.rainy_night.http.bmob.model.SleepMusicModel;
import com.example.ben.rainy_night.http.bmob.model.SleepMusicModelImpl;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/3/28
 */

public class SleepLightMusicPresenterImpl implements SleepLightMusicContract.Presenter {

    private SleepLightMusicContract.View view;
    private SleepMusicModel model;
    private SleepMusicListAdapter mAdapter;
    private List<MusicEntity.DataBean> mLists;
    private View mViewNetError;
    private View mViewDataError;
    private View mViewLoading;
    private View mViewNoMoreData;

    /**
     * 音乐类型
     */
    private String mSceneType;

    public SleepLightMusicPresenterImpl(SleepLightMusicContract.View view) {
        this.view = view;
        model = new SleepMusicModelImpl();
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
                .inflate(R.layout.layout_net_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewNetError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::requsetMusic, 1000);
        });

        mViewLoading = LayoutInflater.from(view.getCon())
                .inflate(R.layout.layout_loading, (ViewGroup) view.getRecycler().getParent(), false);

        mViewDataError = LayoutInflater.from(view.getCon())
                .inflate(R.layout.layout_data_error, (ViewGroup) view.getRecycler().getParent(), false);
        mViewDataError.setOnClickListener(v -> {
            mAdapter.setEmptyView(mViewLoading);
            new Handler(Looper.getMainLooper()).postDelayed(this::requsetMusic, 1000);
        });

        mViewNoMoreData = LayoutInflater.from(view.getCon())
                .inflate(R.layout.layout_no_more_data, (ViewGroup) view.getRecycler().getParent(), false);

        mAdapter.setOnItemClickListener((adapter, v, position) -> {
            view.startBrotherFragment(SleepMusicAudioFragment.newInstance(position));
        });
    }

    @Override
    public void requsetMusic() {
        if (!view.isNetworkAvailable()) {
            mAdapter.setEmptyView(mViewNetError);
            return;
        }
        if (TextUtils.isEmpty(mSceneType)) {
            mAdapter.setEmptyView(mViewDataError);
            return;
        }
        mAdapter.setEmptyView(mViewLoading);
        model.querySleepMusic(mSceneType);
    }

    @Override
    public void getLightMusicData(String result, SleepMusicEntity entity) {
        if (TextUtils.equals(result, Constant.OK)) {
            if (TextUtils.equals(entity.getTitle(), mSceneType)) {
                MusicEntity bean = GsonUtil.fromJson(entity.getJson(), MusicEntity.class);
                if (bean != null) {
                    mLists.clear();
                    mLists.addAll(bean.getData());
                    if (!mLists.isEmpty()) {
                        mAdapter.setNewData(mLists);
                        MusicActionManager.getInstance().setData(mSceneType,bean);
                        mAdapter.addFooterView(mViewNoMoreData);
                    } else {
                        mAdapter.setEmptyView(mViewDataError);
                    }
                } else {
                    mAdapter.setEmptyView(mViewDataError);
                }
            }
        } else {
            view.showToast(result);
            mAdapter.setEmptyView(mViewDataError);
        }
    }
}
