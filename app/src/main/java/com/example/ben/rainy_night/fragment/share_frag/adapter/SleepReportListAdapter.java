package com.example.ben.rainy_night.fragment.share_frag.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.okgo.entity.SleepReportEntity;

import java.util.List;

/**
 * @author Ben
 * @date 2018/4/22
 */

public class SleepReportListAdapter extends BaseQuickAdapter<SleepReportEntity.DataBean, BaseViewHolder> {


    public SleepReportListAdapter(@Nullable List<SleepReportEntity.DataBean> data) {
        super(R.layout.item_sleep_report, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SleepReportEntity.DataBean item) {
        helper.setText(R.id.tv_item_report_title, item.getAnalysisTitle());
        helper.setText(R.id.tv_item_report_tag, item.getKeyWordsList().get(0).getKeywords());
        GlideApp.with(mContext)
                .load(item.getImgUrl())
                .error(R.mipmap.ic_music_loading)
                .into((ImageView) helper.getView(R.id.iv_item_report));
        helper.setText(R.id.tv_item_report_hint, item.getAnalysisDesc());
    }
}
