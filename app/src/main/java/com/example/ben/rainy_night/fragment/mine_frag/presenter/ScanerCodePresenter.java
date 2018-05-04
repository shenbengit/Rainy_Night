package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.ben.rainy_night.fragment.mine_frag.contract.ScanerCodeContract;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxQrBarTool;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogSure;

import java.io.IOException;

import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * @author Ben
 * @date 2018/5/3
 */

public class ScanerCodePresenter implements ScanerCodeContract.Presenter, QRCodeView.Delegate {
    private ScanerCodeContract.View view;
    /**
     * 扫描结果显示框
     */
    private RxDialogSure mDialog;

    public ScanerCodePresenter(ScanerCodeContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {
        //请求Camera权限 与 文件读写 权限
        if (ContextCompat.checkSelfPermission(view.getCon(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(view.getCon(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) view.getCon(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        view.getQrCodeView().setDelegate(this);
        mDialog = new RxDialogSure(view.getCon());
    }

    @Override
    public void startScan() {
        view.getQrCodeView().startCamera();
        view.getQrCodeView().showScanRect();
        view.getQrCodeView().startSpot();
    }

    @Override
    public void goToAlbum() {
        RxPhotoTool.openLocalImage((Activity) view.getCon());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RxPhotoTool.GET_IMAGE_FROM_PHONE && resultCode == Activity.RESULT_OK) {
            ContentResolver resolver = view.getCon().getContentResolver();
            Uri uri = data.getData();
            if (uri != null) {
                try {
                    // 使用ContentProvider通过URI获取原始图片
                    Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, uri);
                    // 开始对图像资源解码
                    Result result = RxQrBarTool.decodeFromPhoto(photo);
                    if (result != null) {
                        //类型
                        BarcodeFormat type = result.getBarcodeFormat();
                        //内容
                        String realContent = result.getText();
                        mDialog.setTitle("扫描结果");
                        mDialog.setContent(realContent);
                        mDialog.setSureListener(v -> mDialog.cancel());
                        if (!mDialog.isShowing()) {
                            mDialog.show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                RxToast.error(view.getCon(), "选取照片失败!");
            }
        }
    }


    @Override
    public void onStop() {
        view.getQrCodeView().stopCamera();
    }

    @Override
    public void onDestroyView() {
        view.getQrCodeView().onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        LoggerUtil.e(result);
        Vibrator vibrator = (Vibrator) view.getCon().getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(200);
        mDialog.setTitle("扫描结果");
        mDialog.setContent(result);
        mDialog.setSureListener(v -> {
            view.getQrCodeView().startSpot();
            mDialog.cancel();
        });
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        RxToast.error(view.getCon(), "打开相机出错!");
        LoggerUtil.e("打开相机出错!");
    }
}
