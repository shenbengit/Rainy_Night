package com.example.ben.rainy_night.util;

import android.content.Context;
import android.os.Environment;

import com.danikula.videocache.HttpProxyCacheServer;

import java.io.File;

/**
 * 视频边下边播系统唯一单例
 *
 * @author Ben
 * @date 2018/3/29
 */

public class HttpProxyUtil {

    private static HttpProxyCacheServer sharedProxy;

    private HttpProxyUtil() {
    }

    public static HttpProxyCacheServer getProxy(Context context) {
        return sharedProxy == null ? (sharedProxy = newProxy(context)) : sharedProxy;
    }

    private static HttpProxyCacheServer newProxy(Context context) {
        return new HttpProxyCacheServer.Builder(context)
                .cacheDirectory(getVideoCacheDir(Environment.getExternalStorageDirectory().getPath() + "/RainyNight/Video/"))
                .maxCacheSize(30)
                .maxCacheSize(1024 * 1024 * 100)
                .build();
    }

    public static File getVideoCacheDir(String path) {
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return filePath;
    }
}
