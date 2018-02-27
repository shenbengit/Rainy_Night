package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.adapter.PostStoryAdapter;
import com.example.ben.rainy_night.fragment.mine_frag.model.PostModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.PostModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IPostStoryView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.example.ben.rainy_night.util.PictureSelectorUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.example.ben.rainy_night.view.EnlargePictureDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/2/22
 */

public class PostStoryPresenterImpl implements PostStoryPresenter {
    private IPostStoryView view;
    private PostModel model;

    private PostStoryAdapter mAdapter;
    /**
     * 选择帖子照片的路径集合
     */
    private List<String> mPictures;

    private List<LocalMedia> mMedias;

    public PostStoryPresenterImpl(IPostStoryView view) {
        this.view = view;
        model = new PostModelImpl();
        mPictures = new ArrayList<String>();
        mMedias = new ArrayList<>();
    }

    /**
     * 初始化GridView
     */
    @Override
    public void initGridView() {
        mAdapter = new PostStoryAdapter(view.getFragAct());
        view.getGridView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    if (mPictures.size() == ConstantUtil.MAX_PICTURES) {
                        showEnlargePictureDialog(position);
                    } else {
                        PictureSelectorUtil.initMultiConfig(view.getFragAct(),
                                ConstantUtil.MAX_PICTURES - mPictures.size());
                    }
                } else {
                    showEnlargePictureDialog(position);
                }
            }
        });
        view.getGridView().setAdapter(mAdapter);
    }

    private void showEnlargePictureDialog(int position) {
        EnlargePictureDialog mDialog = new EnlargePictureDialog(view.getFragAct());
        mDialog.isCanDeletePicture(true);
        mDialog.setIsDeleteListener(new EnlargePictureDialog.OnDeletePictureOnClickListener() {
            @Override
            public void isDeleteListener(int position) {
                mMedias.remove(position);
                mPictures.remove(position);
                mAdapter.setData(mPictures);
            }
        });
        mDialog.setImageList(mMedias, position);
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
        if (requestCode == ConstantUtil.REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                mMedias.addAll(PictureSelector.obtainMultipleResult(data));
                for (LocalMedia localMedia : PictureSelector.obtainMultipleResult(data)) {
                    //被压缩后的图片路径
                    if (localMedia.isCompressed()) {
                        //压缩后的图片路径
                        String compressPath = localMedia.getCompressPath();
                        //把图片添加到将要上传的图片数组中
                        mPictures.add(compressPath);
                    }
                }
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
        String user_objectId = (String) view.getSpValue(SharedPreferencesUtil.USER_OBJECT_ID, "");
        UserBean bean = new UserBean();
        bean.setObjectId(user_objectId);
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
        if (TextUtils.equals(message, ConstantUtil.OK)) {
            view.showToast("发表成功");
        } else {
            view.showToast(message);
        }
    }
}
