package com.example.ben.rainy_night;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.example.ben.rainy_night.http.okgo.factory.OkGoFactory;
import com.example.ben.rainy_night.receiver.MyPlayerReceiver;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.lzx.musiclibrary.cache.CacheConfig;
import com.lzx.musiclibrary.cache.CacheUtils;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzx.musiclibrary.notification.NotificationCreater;
import com.lzx.musiclibrary.utils.BaseUtil;
import com.lzy.okserver.OkDownload;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.vondear.rxtools.RxTool;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import me.yokeyword.fragmentation.Fragmentation;

/**
 * @author Ben
 * @date 2018/1/3
 */

public class App extends Application {
    private RefWatcher refWatcher;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Fragmentation初始化
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出  默认NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                // 开发环境：true时，遇到异常："Can not perform this action after onSaveInstanceState!"时，抛出，并Crash;
                // 生产环境：false时，不抛出，不会Crash，会捕获，可以在handleException()里监听到
                // 实际场景建议.debug(BuildConfig.DEBUG)
                .debug(true)
                // 生产环境时，捕获上述异常（避免crash），会捕获
                // 建议在回调处上传下面异常到崩溃监控服务器
                .handleException(e -> {
                    LoggerUtil.e("Fragmentation异常: " + e.getMessage());
                })
                .install();

        //OkGo初始化
        OkGoFactory.init(this);

        //Bmob初始化
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId("066dc84797cf2e222e1d914eb7d1a297")
                .setConnectTimeout(10)
                .build();
        Bmob.initialize(config);

        //RxTool初始化
        RxTool.init(this);

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                //（可选）是否显示线程信息。 默认值为true
                .showThreadInfo(true)
                //（可选）要显示的方法行数。 默认2
                .methodCount(2)
                //（可选）隐藏内部方法调用到偏移量。 默认5
                .methodOffset(7)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);

        //设置OkDownload下载路径及最大一起下载数
        OkDownload.getInstance().setFolder(Environment.getExternalStorageDirectory().getPath() + "/RainyNight/Cache/");
        OkDownload.getInstance().getThreadPool().setCorePoolSize(5);

        //初始化音乐播放器
        if (!BaseUtil.getCurProcessName(this).contains(":musicLibrary")) {
            //通知栏配置
            NotificationCreater creater = new NotificationCreater.Builder()
                    .setTargetClass("com.example.ben.rainy_night.activity.MainActivity")
                    .setCloseIntent(getPendingIntent(Constant.ACTION_CLOSE))
                    .setFavoriteIntent(getPendingIntent(Constant.ACTION_FAVORITE))
                    .setLyricsIntent(getPendingIntent(Constant.ACTION_LYRICS))
                    .setCreateSystemNotification(true)
                    .build();

            //边播边存配置
            CacheConfig cacheConfig = new CacheConfig.Builder()
                    .setOpenCacheWhenPlaying(true)
                    .setCachePath(CacheUtils.getStorageDirectoryPath() + "/RainyNight/Music/")
                    .build();

            MusicManager.get()
                    .setContext(this)
                    .setNotificationCreater(creater)
                    .setCacheConfig(cacheConfig)
                    .init();
        }
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

    private PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(action);
        intent.setClass(this, MyPlayerReceiver.class);
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }
}
