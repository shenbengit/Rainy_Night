package com.example.ben.rainy_night.util;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

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
                .maxCacheSize(10)
                .maxCacheSize(1024 * 1024 * 50)
                .build();
    }
}
