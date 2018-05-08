package com.example.ben.rainy_night.fragment.mine_frag.frag.space;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.mine_frag.contract.PostDetailContract;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.PostDetailPresenterImpl;
import com.example.ben.rainy_night.http.bmob.entity.PostEntity;
import com.example.ben.rainy_night.widget.EnlargePictureDialog;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 * @date 2018/5/8
 */

public class PostDetailFragment extends BaseFragment<PostDetailContract.Presenter> implements PostDetailContract.View {

    private static final String ENTITY = "entity";
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.civ_post_detail_head)
    CircleImageView civPostDetailHead;
    @BindView(R.id.tv_post_detail_nick)
    TextView tvPostDetailNick;
    @BindView(R.id.tv_post_detail_time)
    TextView tvPostDetailTime;
    @BindView(R.id.tv_post_detail_content)
    TextView tvPostDetailContent;
    @BindView(R.id.nglv_post_detail_images)
    NineGridImageView<BmobFile> nglvPostDetailImages;
    @BindView(R.id.tv_post_detail_likes)
    TextView tvPostDetailLikes;
    @BindView(R.id.tv_post_detail_comment)
    TextView tvPostDetailComment;
    @BindView(R.id.recy_post_detail)
    RecyclerView recyPostDetail;
    @BindView(R.id.nsv_post_detail)
    NestedScrollView nsvPostDetail;
    @BindView(R.id.srl_post_detail)
    SwipeRefreshLayout srlPostDetail;

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


    public static PostDetailFragment newInstance(PostEntity entity) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    private PostEntity mEntity;

    @Override
    protected int getLayout() {
        return R.layout.fragment_post_detail;
    }

    @Override
    protected void setPresenter() {
        presenter = new PostDetailPresenterImpl(this);
    }

    @Override
    protected void initView() {
        baseToolbar.setTitle("详情");
        initToolbarNav(baseToolbar);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mEntity = (PostEntity) bundle.getSerializable(ENTITY);
        }

        presenter.init();
    }

    @Override
    protected void initData() {
        if (mEntity != null) {
            GlideApp.with(_mActivity)
                    .load(mEntity.getUser().getHeadimg().getFileUrl())
                    .placeholder(R.mipmap.ic_head)
                    .error(R.mipmap.ic_head)
                    .into(civPostDetailHead);
            tvPostDetailNick.setText(mEntity.getUser().getNickName());
            tvPostDetailTime.setText(mEntity.getCreatedAt());
            tvPostDetailContent.setText(mEntity.getContent());
            nglvPostDetailImages.setAdapter(mAdapter);
            nglvPostDetailImages.setImagesData(mEntity.getPictures());
        }


    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public boolean isNetworkAvailable() {
        return isNetAvailable();
    }

    @Override
    public void showToast(String text) {
        toastShow(text);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void cancelDialog() {

    }
}
