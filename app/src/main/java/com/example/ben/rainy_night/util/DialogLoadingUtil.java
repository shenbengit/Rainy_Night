package com.example.ben.rainy_night.util;

import android.app.Activity;
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

    private  DialogLoadingUtil mUtil;

    public DialogLoadingUtil(Activity activity) {
        mDialogLoading = new RxDialogLoading(activity);
        mDialogLoading.setCanceledOnTouchOutside(false);
        mDialogLoading.setCancelable(true);
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
