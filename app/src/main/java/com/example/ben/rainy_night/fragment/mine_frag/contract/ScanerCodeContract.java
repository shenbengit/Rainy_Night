package com.example.ben.rainy_night.fragment.mine_frag.contract;

import android.content.Context;
import android.content.Intent;

import com.example.ben.rainy_night.base.BasePresenter;
import com.example.ben.rainy_night.base.BaseView;

import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * @author Ben
 * @date 2018/5/3
 */

public interface ScanerCodeContract {

    interface View extends BaseView {
        /**
         * 获取Context
         *
         * @return
         */
        Context getCon();

        /**
         * 获取二维码控件
         *
         * @return
         */
        QRCodeView getQrCodeView();
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化
         */
        void init();

        /**
         * 开始扫描
         */
        void startScan();

        /**
         * 跳转至相册
         */
        void goToAlbum();

        /**
         * 选择照片返回的数据
         *
         * @param requestCode 请求码
         * @param resultCode  结果码
         * @param data        数据
         */
        void onActivityResult(int requestCode, int resultCode, Intent data);

        /**
         * Fragment 生命周期进入onStop()
         */
        void onStop();

        /**
         * Fragment 生命周期进入onDestroyView()
         */
        void onDestroyView();
    }
}
