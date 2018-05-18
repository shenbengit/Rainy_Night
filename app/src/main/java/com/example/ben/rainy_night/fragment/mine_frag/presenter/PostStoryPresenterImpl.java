package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.mine_frag.adapter.PostStoryAdapter;
import com.example.ben.rainy_night.fragment.mine_frag.contract.PostStoryContract;
import com.example.ben.rainy_night.http.bmob.model.PostModel;
import com.example.ben.rainy_night.http.bmob.model.PostModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.PictureSelectorUtil;
import com.example.ben.rainy_night.widget.EnlargePictureDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

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
    private EnlargePictureDialog mDialog;

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
        mAdapter = new PostStoryAdapter(mPictures);
        GridLayoutManager manager = new GridLayoutManager(view.getFragAct(), 4);
        view.getRecy().setLayoutManager(manager);
        view.getRecy().setAdapter(mAdapter);

        mPictures.add(Constant.ADD_POST_PICTURE);
        mAdapter.setNewData(mPictures);

        mAdapter.setOnItemClickListener((adapter, v, position) -> {
            if (position == adapter.getData().size() - 1) {
                if (!TextUtils.equals(Constant.ADD_POST_PICTURE, mPictures.get(position))) {
                    showEnlargePictureDialog(position);
                } else {
                    PictureSelectorUtil.initMultiConfig(view.getFragAct(), Constant.MAX_PICTURES - mPictures.size() + 1);
                }
            } else {
                showEnlargePictureDialog(position);
            }
        });
    }

    /**
     * 图片放大Dialog
     *
     * @param position
     */
    private void showEnlargePictureDialog(int position) {
        List<String> list = new ArrayList<>();
        for (String path : mPictures) {
            if (!TextUtils.equals(Constant.ADD_POST_PICTURE, path)) {
                list.add(path);
            }
        }
        if (mDialog == null) {
            mDialog = new EnlargePictureDialog(view.getFragAct());
        }
        mDialog.setIsDeleteListener(position1 -> {
            mPictures.remove(position1);
            if (!TextUtils.equals(mPictures.get(mPictures.size() - 1), Constant.ADD_POST_PICTURE)) {
                mPictures.add(Constant.ADD_POST_PICTURE);
            }
            mAdapter.setNewData(mPictures);
        });
        mDialog.setImageList(list, position, true);
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
                mPictures.remove(mPictures.size() - 1);

                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList == null || selectList.isEmpty()) {
                    view.showToast("图片选取失败，请重试！");
                    return;
                }
                for (LocalMedia media : selectList) {
                    if (media.isCompressed()) {
                        mPictures.add(media.getCompressPath());
                    }
                }
                if (mPictures.size() < Constant.MAX_PICTURES) {
                    mPictures.add(Constant.ADD_POST_PICTURE);
                }
                mAdapter.setNewData(mPictures);
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

        List<String> list = new ArrayList<>();
        for (String path : mPictures) {
            if (!TextUtils.equals(Constant.ADD_POST_PICTURE, path)) {
                list.add(path);
            }
        }
        if (list.isEmpty()) {
            model.publishPost(content, bean);
        } else {
            String[] paths = list.toArray(new String[list.size()]);
            model.publishPostWithPicture(paths, content, bean);
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
            //发表成功后清除预览缓存
            PictureFileUtils.deleteCacheDirFile(view.getFragAct());
            Bundle bundle = new Bundle();
            bundle.putString("发表成功", "发表成功");
            view.setFragResult(-1, bundle);
            view.showToast("发表成功");
            new Handler(Looper.getMainLooper()).postDelayed(() -> view.getFragAct().onBackPressed(),1000);
        } else {
            view.showToast(message);
        }
    }
}
