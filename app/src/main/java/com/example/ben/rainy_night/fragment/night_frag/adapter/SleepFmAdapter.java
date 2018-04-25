package com.example.ben.rainy_night.fragment.night_frag.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.okgo.entity.SleepFmEntity;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.tvNumber.setText(String.valueOf(position + 1));
    }

    @Override
    protected void convert(ViewHolder holder, SleepFmEntity.DataBean.ListBeanX item) {
        holder.tvName.setText(item.getMediaName().trim());
        holder.tvDate.setText(item.getCreateTime().substring(5, 10));
        holder.tvCount.setText(String.valueOf(item.getList().get(0).getCumulativeNum()));
        holder.tvTime.setText(RxTimeTool.formatTime((long) (item.getList().get(0).getDuration() * 1000)));
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
