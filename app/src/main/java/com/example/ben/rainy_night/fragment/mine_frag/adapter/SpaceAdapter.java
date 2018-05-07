package com.example.ben.rainy_night.fragment.mine_frag.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.bmob.entity.PostEntity;
import com.example.ben.rainy_night.widget.EnlargePictureDialog;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 * @date 2018/3/2
 */

public class SpaceAdapter extends BaseQuickAdapter<PostEntity, SpaceAdapter.ViewHolder> {

    public SpaceAdapter(@Nullable List<PostEntity> data) {
        super(R.layout.item_recycler_space, data);
    }

    @Override
    protected void convert(SpaceAdapter.ViewHolder holder, PostEntity item) {
        GlideApp.with(mContext)
                .load(item.getUser().getHeadimg().getFileUrl())
                .placeholder(R.mipmap.ic_head)
                .error(R.mipmap.ic_head)
                .into(holder.civHead);
        holder.tvNick.setText(item.getUser().getNickName());
        holder.tvTime.setText(item.getCreatedAt());
        holder.tvContent.setText(item.getContent());

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

    /**
     * 自定义ViewHolder 继承 BaseViewHolder
     */
    class ViewHolder extends BaseViewHolder {

        private CircleImageView civHead;
        private TextView tvNick;
        private TextView tvTime;
        private TextView tvContent;
        private NineGridImageView<String> nglvImages;

        private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                GlideApp.with(context).load(s).into(imageView);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<String> list) {
                super.onItemImageClick(context, index, list);
                EnlargePictureDialog mDialog = new EnlargePictureDialog(context);
                mDialog.setImageList(list,index,false);
                mDialog.show();
            }
        };

        public ViewHolder(View view) {
            super(view);
            civHead = view.findViewById(R.id.civ_item_space_head);
            tvNick = view.findViewById(R.id.tv_item_space_nick);
            tvTime = view.findViewById(R.id.tv_item_space_time);
            tvContent = view.findViewById(R.id.tv_item_space_content);
            nglvImages = view.findViewById(R.id.nglv_images);

            nglvImages.setAdapter(mAdapter);
        }

        public void bind(){

        }
    }

}
