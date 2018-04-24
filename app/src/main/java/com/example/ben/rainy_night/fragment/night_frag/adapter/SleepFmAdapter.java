package com.example.ben.rainy_night.fragment.night_frag.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.http.okgo.entity.SleepFmEntity;

import java.util.List;

/**
 *
 * @author Ben
 * @date 2018/4/24
 */

public class SleepFmAdapter extends BaseQuickAdapter<SleepFmEntity.DataBean, BaseViewHolder> {

    public SleepFmAdapter(@LayoutRes int layoutResId, @Nullable List<SleepFmEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SleepFmEntity.DataBean item) {

    }
}
