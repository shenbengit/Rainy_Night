package com.example.ben.rainy_night.fragment.mine_frag.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.bmob.entity.CommentEntity;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 * @date 2018/5/10
 */

public class PostCommentAdapter extends BaseQuickAdapter<CommentEntity, PostCommentAdapter.ViewHolder> {

    private UserEntity mEntity;
    private String mCurrentUser;
    private String mDate;

    public PostCommentAdapter(@Nullable List<CommentEntity> data) {
        super(R.layout.item_comment, data);
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


    @Override
    protected void convert(ViewHolder holder, CommentEntity item) {
        GlideApp.with(mContext)
                .load(item.getUser().getHeadimg().getFileUrl())
                .placeholder(R.mipmap.ic_head)
                .error(R.mipmap.ic_head)
                .into(holder.civHead);

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
    }

    /**
     * 自定义ViewHolder 继承 BaseViewHolder
     */
    class ViewHolder extends BaseViewHolder {

        private CircleImageView civHead;
        private TextView tvNick;
        private TextView tvTime;
        private TextView tvContent;

        public ViewHolder(View view) {
            super(view);
            civHead = view.findViewById(R.id.civ_item_comment_head);
            tvNick = view.findViewById(R.id.tv_item_comment_nick);
            tvTime = view.findViewById(R.id.tv_item_comment_time);
            tvContent = view.findViewById(R.id.tv_item_comment_content);
        }
    }
}
