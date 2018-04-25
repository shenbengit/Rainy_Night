package com.example.ben.rainy_night.fragment.home_frag.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import java.util.List;

/**
 * @author Ben
 * @date 2018/3/28
 */

public class SleepMusicListAdapter extends BaseQuickAdapter<MusicEntity.DataBean, SleepMusicListAdapter.ViewHolder> {


    public SleepMusicListAdapter(@Nullable List<MusicEntity.DataBean> data) {
        super(R.layout.item_sleep_music_list, data);
    }

    @Override
    protected void convert(SleepMusicListAdapter.ViewHolder holder, MusicEntity.DataBean item) {
        holder.tvName.setText(item.getSceneName());
        GlideApp.with(mContext)
                .load(item.getCoverUrl())
                .placeholder(R.mipmap.ic_music_loading)
                .error(R.mipmap.ic_music_loading)
                .into(holder.ivCover);
    }

    /**
     * 自定义ViewHolder 继承 BaseViewHolder
     */
    class ViewHolder extends BaseViewHolder {

        private TextView tvName;
        private ImageView ivCover;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_item_sleep_music_list);
            ivCover = view.findViewById(R.id.iv_item_sleep_music_list);
        }
    }
}
