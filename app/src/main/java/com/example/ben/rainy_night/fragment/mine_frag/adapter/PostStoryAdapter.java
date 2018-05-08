package com.example.ben.rainy_night.fragment.mine_frag.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.util.Constant;

import java.util.List;

/**
 * @author Ben
 * @date 2018/2/22
 */
public class PostStoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PostStoryAdapter(@Nullable List<String> data) {
        super(R.layout.item_post_story, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (!TextUtils.equals(Constant.ADD_POST_PICTURE, item)) {
            GlideApp.with(mContext)
                    .load(item)
                    .error(R.mipmap.img_picture_load_failed)
                    .into((ImageView) helper.getView(R.id.iv_item_post_story));
        } else {
            helper.setImageResource(R.id.iv_item_post_story, R.mipmap.img_add_picture);
        }

    }
}
