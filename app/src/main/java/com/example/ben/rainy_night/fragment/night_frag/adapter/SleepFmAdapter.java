package com.example.ben.rainy_night.fragment.night_frag.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.okgo.entity.SleepFmEntity;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.lzx.musiclibrary.manager.MusicManager;
import com.vondear.rxtools.RxTimeTool;

import java.util.List;

/**
 * @author Ben
 * @date 2018/4/24
 */

public class SleepFmAdapter extends BaseQuickAdapter<SleepFmEntity.DataBean.ListBeanX, SleepFmAdapter.ViewHolder> {

    public SleepFmAdapter(@Nullable List<SleepFmEntity.DataBean.ListBeanX> data) {
        super(R.layout.item_sleep_fm, data);
    }

    @Override
    protected void convert(ViewHolder holder, SleepFmEntity.DataBean.ListBeanX item) {
        AnimationDrawable drawable = (AnimationDrawable) holder.ivAnim.getDrawable();
        holder.tvNumber.setText(String.valueOf(holder.getLayoutPosition() - getHeaderLayoutCount() + 1));
        if (item.getMediaName().contains("【海豚FM说晚安】")) {
            holder.tvName.setText(item.getMediaName().replace("【海豚FM说晚安】", "").trim());
        } else if (item.getMediaName().contains("【耐撕の人】")) {
            holder.tvName.setText(item.getMediaName().replace("【耐撕の人】", "").trim());
        } else {
            holder.tvName.setText(item.getMediaName().trim());
        }
        holder.tvDate.setText(item.getCreateTime().substring(5, 10));
        holder.tvCount.setText(String.valueOf(item.getList().get(0).getCumulativeNum()));
        holder.tvTime.setText(RxTimeTool.formatTime((long) (item.getList().get(0).getDuration() * 1000)));

        if (MusicActionManager.getInstance().isCurrMusicIsPlayingMusic(item.getMediaName())) {
            LoggerUtil.e(MusicManager.get().getCurrPlayingMusic().getSongName());
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.color_fm_playing));
            holder.ivAnim.setVisibility(View.VISIBLE);
            holder.tvNumber.setVisibility(View.GONE);
            if (MusicActionManager.getInstance().isPlaying()) {
                drawable.start();
            } else {
                drawable.stop();
            }
        } else {
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.ivAnim.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.VISIBLE);
            drawable.stop();
        }
    }


    /**
     * 自定义ViewHolder 继承 BaseViewHolder
     */
    class ViewHolder extends BaseViewHolder {

        private ImageView ivAnim;
        private TextView tvNumber;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvCount;
        private TextView tvTime;

        public ViewHolder(View view) {
            super(view);
            ivAnim = view.findViewById(R.id.iv_item_fm_animation);
            tvNumber = view.findViewById(R.id.tv_item_fm_number);
            tvName = view.findViewById(R.id.tv_item_fm_music_name);
            tvDate = view.findViewById(R.id.tv_item_fm_date);
            tvCount = view.findViewById(R.id.tv_item_fm_play_count);
            tvTime = view.findViewById(R.id.tv_item_fm_play_time);
        }
    }
}
