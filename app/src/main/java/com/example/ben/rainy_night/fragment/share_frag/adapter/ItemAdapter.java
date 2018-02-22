package com.example.ben.rainy_night.fragment.share_frag.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.R;

/**
 *
 * @author Ben
 * @date 2018/2/10
 */

public class ItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ItemAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text, item);
    }
}
