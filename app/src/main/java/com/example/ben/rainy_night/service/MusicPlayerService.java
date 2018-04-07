package com.example.ben.rainy_night.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.ben.rainy_night.util.HttpProxyUtil;

/**
 * @author Ben
 * @date 2018/4/3
 */

public class MusicPlayerService extends Service {

    private MediaPlayer mPlayer;
    private HttpProxyCacheServer mServer;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MediaPlayer();
        mServer = HttpProxyUtil.getProxy(getApplicationContext());

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
