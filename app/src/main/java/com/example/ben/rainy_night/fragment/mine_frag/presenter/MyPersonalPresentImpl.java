package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.UserModelImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IMyPersonalView;
import com.example.ben.rainy_night.util.CompressPictureUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.nanchen.compresshelper.CompressHelper;
import com.vondear.rxtools.RxPhotoTool;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * @author Ben
 * @date 2018/1/8
 */

public class MyPersonalPresentImpl implements MyPersonalPresenter {

    private IMyPersonalView view;
    private UserModel model;
    private Uri mUri = null;
    private File mFile = null;

    private static final String OK = "ok";

    public MyPersonalPresentImpl(IMyPersonalView view) {
        this.view = view;
        model = new UserModelImpl();
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
        switch (requestCode) {
            //选择相册之后的处理
            case RxPhotoTool.GET_IMAGE_FROM_PHONE:
                if (resultCode == Activity.RESULT_OK) {
                    initUCrop(view.getFragAct(), data.getData());
                    Log.e("uri地址,相册》》》》》》》》》》》》", data.getData().toString());
                }
                break;
            //选择照相机之后的处理
            case RxPhotoTool.GET_IMAGE_BY_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    initUCrop(view.getFragAct(), RxPhotoTool.imageUriFromCamera);
                    Log.e("uri地址,相机》》》》》》》》》》》》", RxPhotoTool.imageUriFromCamera.toString());
                }
                break;
            //普通裁剪后的处理
            case RxPhotoTool.CROP_IMAGE:
                GlideApp.with(view.getFragAct())
                        .load(RxPhotoTool.cropImageUri)
                        .error(R.mipmap.ic_launcher_round)
                        .into(view.getHeadImg());
                break;
            //UCrop裁剪之后的处理
            case UCrop.REQUEST_CROP:
                if (resultCode == Activity.RESULT_OK) {
                    mUri = UCrop.getOutput(data);
                    mFile = roadImageView(mUri);
                }
                break;
            //UCrop裁剪错误之后的处理
            case UCrop.RESULT_ERROR:
                final Throwable cropError = UCrop.getError(data);
                ToastUtil.show(view.getFragAct(), "剪裁失败请重试");
                break;
            default:
                break;
        }
    }

    /**
     * 更新用户信息
     */
    @Override
    public void updateUser() {
        view.showDialog();
        if (mFile != null) {
            updateIncludeImage();
        } else {
            updateInformation(null);
        }
    }

    /**
     * 更新用户信息是否成功
     *
     * @param message 成功：“ok”,失败：“e.getMessage()”
     */
    @Override
    public void isUpdateUserSuccess(String message) {
        if (TextUtils.equals(OK, message)) {
            view.cancelDialog();
            view.putSpValue(SharedPreferencesUtil.USER_SEX, view.getTextSex().getText().toString().trim());
            view.putSpValue(SharedPreferencesUtil.USER_BIRTHDAY, view.getTextBirthday().getText().toString().trim());
            view.showToast("用户资料更新成功");
        } else {
            view.cancelDialog();
            view.showToast(message);
        }
    }

    /**
     * 剪裁图片
     *
     * @param data
     */

    private void initUCrop(Context context, Uri data) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(context.getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(context, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(context, R.color.colorPrimaryDark));
        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        options.setCircleDimmedLayer(true);
        //设置是否展示矩形裁剪框
        options.setShowCropFrame(false);

        //设置竖线的数量
        options.setCropGridColumnCount(0);
        //设置横线的数量
        options.setCropGridRowCount(0);

        UCrop.of(data, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(view.getFragAct());
    }

    /**
     * 从Uri中加载图片 并将其转化成File文件返回
     *
     * @param mUri
     * @return
     */
    private File roadImageView(Uri mUri) {
        GlideApp.with(view.getFragAct())
                .load(mUri)
                .error(R.mipmap.ic_launcher_round)
                .into(view.getHeadImg());
        return new File(RxPhotoTool.getImageAbsolutePath(view.getFragAct(), mUri));
    }

    /**
     * 更新用户资料,包括头像
     */
    private void updateIncludeImage() {
        final BmobFile bmobFile = new BmobFile(CompressPictureUtil.getInstance().compress(view.getFragAct(), mFile));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    updateInformation(bmobFile);
                } else {
                    view.showToast("图片资源上传失败,请稍后重试!");
                }
            }
        });
    }

    private void updateInformation(BmobFile file) {
        UserBean bean = new UserBean();
        bean.setObjectId(String.valueOf(view.getSpValue(SharedPreferencesUtil.USER_OBJECT_ID, "")));
        bean.setHeadimg(file);
        bean.setNickName(view.getTextUser().getText().toString().trim());
        bean.setSex(view.getTextSex().getText().toString().trim());
        bean.setBirthday(view.getTextBirthday().getText().toString().trim());
        bean.setEmail(view.getTextEmail().getText().toString().trim());
        model.updateUser(bean);
    }
}
