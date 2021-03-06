package com.example.ben.rainy_night.fragment.mine_frag.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.bmob.entity.PostEntity;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.widget.EnlargePictureDialog;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 * @date 2018/3/2
 */

public class SpaceAdapter extends BaseQuickAdapter<PostEntity, SpaceAdapter.ViewHolder> {

    private UserEntity mEntity;
    private String mCurrentUser;
    private String mDate;

    public SpaceAdapter(@Nullable List<PostEntity> data) {
        super(R.layout.item_recycler_space, data);
        mEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (mEntity != null) {
            mCurrentUser = mEntity.getObjectId();
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        mDate = format.format(date);
    }

    /**
     * 判断用户是否登陆
     *
     * @return
     */
    public boolean isLoginUser() {
        return mEntity != null;
    }

    /**
     * 设置登陆用户信息
     */
    public void setCurrentUser() {
        mEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (mEntity != null) {
            mCurrentUser = mEntity.getObjectId();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(SpaceAdapter.ViewHolder holder, PostEntity item) {
        if (item.getUser().getHeadimg() == null
                || TextUtils.isEmpty(item.getUser().getHeadimg().getFileUrl())) {
            holder.setImageResource(R.id.civ_item_space_head, R.mipmap.ic_head);
        } else {
            GlideApp.with(mContext)
                    .load(item.getUser().getHeadimg().getFileUrl())
                    .placeholder(R.mipmap.ic_head)
                    .error(R.mipmap.ic_head)
                    .into(holder.civHead);
        }

        if (mEntity != null && TextUtils.equals(mCurrentUser, item.getUser().getObjectId())) {
            holder.tvNick.setText(R.string.mine);
        } else {
            holder.tvNick.setText(item.getUser().getNickName());
        }
        if (TextUtils.equals(mDate, item.getCreatedAt().substring(0, 10))) {
            holder.tvTime.setText(mContext.getString(R.string.today) + "\t" + item.getCreatedAt().substring(11, 16));
        } else {
            holder.tvTime.setText(item.getCreatedAt().substring(5, 16));
        }
        holder.tvContent.setText(item.getContent());

        if (item.getPictures() == null) {
            holder.nglvImages.setVisibility(View.GONE);
        } else {
            holder.bind(item.getPictures());
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
        private NineGridImageView<BmobFile> nglvImages;

        private NineGridImageViewAdapter<BmobFile> mAdapter = new NineGridImageViewAdapter<BmobFile>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, BmobFile s) {
                GlideApp.with(context)
                        .load(s.getFileUrl())
                        .placeholder(R.mipmap.ic_transparent_picture)
                        .error(R.mipmap.ic_transparent_picture)
                        .into(imageView);
            }

            @Override
            protected void onItemImageClick(Context context, ImageView imageView, int index, List<BmobFile> list) {
                super.onItemImageClick(context, imageView, index, list);
                EnlargePictureDialog mDialog = new EnlargePictureDialog(context);
                mDialog.setImageList(list, index, false);
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
        }

        public void bind(List<BmobFile> list) {
            nglvImages.setAdapter(mAdapter);
            nglvImages.setImagesData(list);
        }
    }

}
