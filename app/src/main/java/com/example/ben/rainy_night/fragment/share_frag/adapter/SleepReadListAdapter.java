package com.example.ben.rainy_night.fragment.share_frag.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.okgo.entity.SleepReadEntity;

import java.util.List;

/**
 * @author Ben
 * @date 2018/4/22
 */

public class SleepReadListAdapter extends BaseQuickAdapter<SleepReadEntity.DataBean, BaseViewHolder> {

    public SleepReadListAdapter(@Nullable List<SleepReadEntity.DataBean> data) {
        super(R.layout.item_sleep_read, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SleepReadEntity.DataBean item) {
        helper.setText(R.id.tv_item_read_title, item.getArticleTitle());
        GlideApp.with(mContext)
                .load(item.getImgUrl())
                .error(R.mipmap.ic_music_loading)
                .into((ImageView) helper.getView(R.id.iv_item_read));
        helper.setText(R.id.tv_item_read_hint, item.getArticleDesc());
    }
}
