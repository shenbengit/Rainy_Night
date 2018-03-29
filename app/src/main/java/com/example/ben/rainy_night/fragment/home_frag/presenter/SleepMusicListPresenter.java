package com.example.ben.rainy_night.fragment.home_frag.presenter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ben.rainy_night.fragment.home_frag.adapter.SleepMusicListAdapter;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicListContract;
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

    /**
     * 获取音乐数据
     *
     * @param sceneType 1: 自然音符 ,2: 轻音乐
     */
    @Override
    public void getMusic(String sceneType) {
        mLists = new ArrayList<>();
        mAdapter = new SleepMusicListAdapter(mLists);
        LinearLayoutManager manager = new LinearLayoutManager(view.getCon(),
                LinearLayoutManager.VERTICAL, false);
        view.getRecycler().setItemAnimator(new DefaultItemAnimator());
        view.getRecycler().setLayoutManager(manager);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        view.getRecycler().setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            view.showToast(mLists.get(position).getSceneName());
        });

        OkGo.<MusicEntity>get(Constant.HAITUN_BASEURL + Constant.HAITUN_MUSIC)
                .params("sceneType", sceneType)
                .params("timestamp", System.currentTimeMillis())
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .cacheKey("dolphin_music" + sceneType)
                .execute(new JsonCallBack<MusicEntity>(MusicEntity.class) {
                    @Override
                    public void onSuccess(Response<MusicEntity> response) {
                        if (response.body().getCode() == 0) {
                            mLists = response.body().getData();
                            mHandler.sendEmptyMessage(1);
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<MusicEntity> response) {
                        super.onCacheSuccess(response);
                        if (response.body().getCode() == 0) {
                            mLists = response.body().getData();
                            mHandler.sendEmptyMessage(2);
                        }
                    }
                });
    }
}
