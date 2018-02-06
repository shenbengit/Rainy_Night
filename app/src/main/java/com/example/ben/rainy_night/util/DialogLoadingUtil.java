package com.example.ben.rainy_night.util;

import android.content.Context;

import com.vondear.rxtools.view.dialog.RxDialogLoading;

/**
 * 封装网络加载时Dialog
 *
 * @author Ben
 * @date 2018/1/22
 */

public class DialogLoadingUtil {

    private RxDialogLoading mDialogLoading = null;

    private volatile static DialogLoadingUtil mUtil;

    private DialogLoadingUtil(Context context) {
        mDialogLoading = new RxDialogLoading(context);
        mDialogLoading.setCanceledOnTouchOutside(false);
        mDialogLoading.setCancelable(true);
    }

    public static DialogLoadingUtil getInstance(Context context) {
        if (mUtil == null) {
            synchronized (DialogLoadingUtil.class) {
                if (mUtil == null) {
                    mUtil = new DialogLoadingUtil(context);
                }
            }
        }
        return mUtil;
    }

    public void show() {
        if (mDialogLoading != null && !mDialogLoading.isShowing()) {
            mDialogLoading.show();
        }
    }

    public boolean isShowing() {
        if (mDialogLoading != null) {
            if (mDialogLoading.isShowing()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void cancel() {
        if (mDialogLoading != null && mDialogLoading.isShowing()) {
            mDialogLoading.cancel();
        }
    }

}
