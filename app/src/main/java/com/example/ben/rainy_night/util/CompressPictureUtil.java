package com.example.ben.rainy_night.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.nanchen.compresshelper.CompressHelper;

import java.io.File;

/**
 * 图片压缩工具类
 *
 * @author Ben
 * @date 2018/2/7
 */

public class CompressPictureUtil {

    /**
     * 压缩一张图片
     * @param context
     * @param oldFile 旧文件地址
     * @return
     */
    public static File compress(Context context, File oldFile) {
        //图片压缩
        return new CompressHelper.Builder(context)
                //压缩质量[0-100]
                .setQuality(100)
                //格式
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .build()
                //旧文件地址
                .compressToFile(oldFile);
    }
}
