package com.example.ben.rainy_night.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.fragment.event.OnMusicPlayerEvent;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.HttpProxyUtil;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLDecoder;

import static com.vondear.rxtools.view.progressing.animation.AnimationUtils.stop;

/**
 * @author Ben
 * @date 2018/4/3
 */

public class MusicPlayerService extends Service {

    private HttpProxyCacheServer mServer;
    private SimpleExoPlayer mPlayer;
    private Handler mHandler;

    /**
     * 音乐数据
     */
    private MusicEntity mEntity;
    /**
     * 当前播放位置
     */
    private int mPosition = 0;
    /**
     * 循环模式
     */
    private String mCycle = Constant.SINGLE_CYCLE;
    /**
     * 定时时间 ，仅针对伴我睡有效
     * 对于其他不需要定时器的地方传: -1
     * 单位分钟
     */
    private int mTime = 30;

    @Override
    public void onCreate() {
        super.onCreate();
        mServer = HttpProxyUtil.getProxy(getApplicationContext());
        EventBus.getDefault().register(this);

//        // 1. Create a default TrackSelector
//        mHandler = new Handler();
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//        // 2. Create the player
//        mPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void setMusicPlayer(OnMusicPlayerEvent event) {
        LoggerUtil.e("音乐动作：" + event.getAction());
        switch (event.getAction()) {
            case Constant.MUSIC_START:
                if (event.getData() instanceof MusicEntity) {
                    mEntity = (MusicEntity) event.getData();
                }
                mPosition = event.getPosition();
                mCycle = event.getCycleMode();
                mTime = event.getTime();
                start();
                break;
            case Constant.MUSIC_RESUME:
                resume();
                break;
            case Constant.MUSIC_PAUSE:
                pause();
                break;
            case Constant.MUSIC_STOP:
                stop();
                break;
            case Constant.MUSIC_PREVIOUS:
                previous();
                break;
            case Constant.MUSIC_NEXT:
                next();
                break;
            case Constant.MUSIC_SET_CYCLE_MODE:
                if (!TextUtils.isEmpty(event.getCycleMode())) {
                    mCycle = event.getCycleMode();
                    setCycleMode();
                }
                break;
            case Constant.MUSIC_SET_TIME:
                mTime = event.getTime();
                setTime();
                break;
            default:
                break;
        }
    }

    /**
     * 开始播放
     */
    private void start() {

    }

    /**
     * 继续播放
     */
    private void resume() {

    }

    /**
     * 暂停
     */
    private void pause() {

    }

    /**
     * 播放上一个
     */
    private void previous() {

    }

    /**
     * 播放下一个
     */
    private void next() {

    }

    /**
     * 设置循环模式
     */
    private void setCycleMode() {

    }

    /**
     * 设置定时时间
     */
    private void setTime() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 获取代理地址
     *
     * @param videoUrl 音乐地址
     * @return 音乐地址
     */
    private String getProxyUrl(String videoUrl) {
        return mServer.getProxyUrl(URLDecoder.decode(videoUrl));
    }

    private DataSource.Factory buildDataSourceFactory(Context context, DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.getString(R.string.app_name)), bandwidthMeter);
    }
}
