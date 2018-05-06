package com.example.ben.rainy_night.fragment.mine_frag.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.bmob.entity.PostEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/3/2
 */

public class SpaceAdapter extends BaseQuickAdapter<PostEntity, BaseViewHolder> {

    public SpaceAdapter(@Nullable List<PostEntity> data) {
        super(R.layout.item_recycler_space, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostEntity item) {
        helper.setText(R.id.tv_item_space_nick, item.getUser().getNickName())
                .setText(R.id.tv_item_space_time, item.getCreatedAt())
                .setText(R.id.tv_item_space_content, item.getContent());

        GlideApp.with(mContext)
                .load(item.getUser().getHeadimg().getFileUrl())
                .placeholder(R.mipmap.ic_head)
                .error(R.mipmap.ic_head)
                .into((ImageView) helper.getView(R.id.civ_item_space_head));

        JSONArray array = new JSONArray(item.getPictures());
        JSONObject object;
        List<String> mUrls = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                object = array.getJSONObject(i);
                String filename = object.getString("filename");
                String url = object.getString("url");
                mUrls.add(url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
