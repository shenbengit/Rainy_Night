package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.mine_frag.adapter.PostStoryAdapter;
import com.example.ben.rainy_night.fragment.mine_frag.contract.PostStoryContract;
import com.example.ben.rainy_night.fragment.mine_frag.model.PostModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.PostModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.PictureSelectorUtil;
import com.example.ben.rainy_night.widget.EnlargePictureDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * @author Ben
 * @date 2018/2/22
 */

public class PostStoryPresenterImpl implements PostStoryContract.Presenter {
    private PostStoryContract.View view;
    private PostModel model;

    private PostStoryAdapter mAdapter;
    /**
     * 选择帖子照片的路径集合
     */
    private List<String> mPictures;

    public PostStoryPresenterImpl(PostStoryContract.View view) {
        this.view = view;
        model = new PostModelImpl();
    }

    /**
     * 初始化GridView
     */
    @Override
    public void initGridView() {
        mPictures = new ArrayList<>();
        mAdapter = new PostStoryAdapter(view.getFragAct());
        view.getGridView().setAdapter(mAdapter);
        view.getGridView().setOnItemClickListener((parent, v, position, id) -> {
            if (position == parent.getChildCount() - 1) {
                if (mPictures.size() == Constant.MAX_PICTURES) {
                    showEnlargePictureDialog(position);
                } else {
                    PictureSelectorUtil.initMultiConfig(view.getFragAct(),
                            Constant.MAX_PICTURES - mPictures.size());
                }
            } else {
                showEnlargePictureDialog(position);
            }
        });
    }

    private void showEnlargePictureDialog(int position) {
        EnlargePictureDialog mDialog = new EnlargePictureDialog(view.getFragAct());
        mDialog.setIsDeleteListener(position1 -> {
            mPictures.remove(position1);
            mAdapter.setData(mPictures);
        });
        mDialog.setImageList(mPictures, position, true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    /**
     * 选择照片返回的数据
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                //被压缩后的图片路径
                //压缩后的图片路径
                //把图片添加到将要上传的图片数组中
                PictureSelector.obtainMultipleResult(data).stream().filter(LocalMedia::isCompressed).forEach(localMedia -> {
                    //压缩后的图片路径
                    String compressPath = localMedia.getCompressPath();
                    //把图片添加到将要上传的图片数组中
                    mPictures.add(compressPath);
                });
                mAdapter.setData(mPictures);
            }
        }
    }

    /**
     * 发表帖子
     */
    @Override
    public void publishPost() {
        String content = view.getEditText().getText().toString().trim();

        if (!view.isNetworkAvailable()) {
            view.showToast("当前网络不可用");
            return;
        }

        UserEntity bean = BmobUser.getCurrentUser(UserEntity.class);
        view.showDialog();
        if (mPictures.size() != 0) {
            String[] paths = mPictures.toArray(new String[mPictures.size()]);
            model.publishPostWithPicture(paths, content, bean);
        } else {
            model.publishPost(content, bean);
        }
    }

    /**
     * 发表帖子是否成功
     *
     * @param message
     */
    @Override
    public void publishPostIsSuccess(String message) {
        view.cancelDialog();
        if (TextUtils.equals(message, Constant.OK)) {
            view.showToast("发表成功");
        } else {
            view.showToast(message);
        }
    }
}
