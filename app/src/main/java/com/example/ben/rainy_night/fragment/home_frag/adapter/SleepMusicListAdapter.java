package com.example.ben.rainy_night.fragment.home_frag.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;

import java.util.List;

/**
 *
 * @author Ben
 * @date 2018/3/28
 */

public class SleepMusicListAdapter extends BaseQuickAdapter<MusicEntity.DataBean,BaseViewHolder>{

    public SleepMusicListAdapter(@Nullable List<MusicEntity.DataBean> data) {
        super(R.layout.item_sleep_music_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicEntity.DataBean item) {
        helper.setText(R.id.tv_item_sleep_music_list,item.getSceneName());
        GlideApp.with(mContext).load(item.getCoverUrl()).into((ImageView) helper.getView(R.id.iv_item_sleep_music_list));
    }
}
