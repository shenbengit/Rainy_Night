package com.example.ben.rainy_night.fragment.night_frag.presenter;

import com.example.ben.rainy_night.fragment.night_frag.contract.SleepFmContract;
import com.example.ben.rainy_night.http.okgo.callback.JsonCallBack;
import com.example.ben.rainy_night.http.okgo.entity.SleepFmEntity;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

/**
 * @author Ben
 * @date 2018/4/24
 */

public class SleepFmPresenter implements SleepFmContract.Presenter {
    private SleepFmContract.View view;

    private int mAlbumsId;
    private String mCacheKey;
    private int mPageIndex = 1;

    public SleepFmPresenter(SleepFmContract.View view) {
        this.view = view;
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
                mCacheKey = Constant.DOLPHIN_HYPNOSIS_CACHE;
                break;
            case Constant.DOLPHIN_BEFORE_SLEEP_AND_READ:
                mCacheKey = Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE;
                break;
            case Constant.DOLPHIN_NICE_PEOPLE:
                mCacheKey = Constant.DOLPHIN_NICE_PEOPLE_CACHE;
                break;
            case Constant.DOLPHIN_SAY_GOOG_NIGHT:
                mCacheKey = Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE;
                break;
            default:
                mCacheKey = "";
                break;
        }


    }

    /**
     * 获取FM集合
     */
    @Override
    public void getAlbumsMediaList() {
        OkGo.<SleepFmEntity>get(Constant.DOLPHIN_BASEURL + Constant.DOLPHIN_ALBUMS_MEDIA_LIST)
                .params("albumsId", mAlbumsId)
                .params("appId", "30639")
                .params("pageIndex", mPageIndex)
                .params("pageRows", "20")
                .params("timestamp", System.currentTimeMillis())
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .cacheKey(mCacheKey + String.valueOf(mPageIndex))
                .execute(new JsonCallBack<SleepFmEntity>(SleepFmEntity.class) {
                    @Override
                    public void onSuccess(Response<SleepFmEntity> response) {
                        if (response.body().getCode() == Constant.REQUEST_SUCCESS) {
                            MusicActionManager.getInstance().setData(mCacheKey + String.valueOf(mPageIndex));
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<SleepFmEntity> response) {
                        super.onCacheSuccess(response);
                        if (response.body().getCode() == Constant.REQUEST_SUCCESS) {
                            MusicActionManager.getInstance().setData(mCacheKey + String.valueOf(mPageIndex));
                        }
                    }
                });
    }
}
