package com.example.ben.rainy_night;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.example.ben.rainy_night.http.okgo.factory.OkGoFactory;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.lzx.musiclibrary.cache.CacheConfig;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzx.musiclibrary.utils.BaseUtil;
import com.lzy.okserver.OkDownload;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.vondear.rxtools.RxTool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        // 获取当前包名
        String packageName = this.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "cbccf91af7", true, strategy);

        //Fragmentation初始化
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出  默认NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.NONE)
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
        OkGoFactory.getInstance().init(this);

        //Bmob初始化
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId("066dc84797cf2e222e1d914eb7d1a297")
                .setConnectTimeout(10)
                .build();
        Bmob.initialize(config);

        //RxTool初始化
        RxTool.init(this);

        //Logger初始化
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                //（可选）是否显示线程信息。 默认值为true
                .showThreadInfo(true)
                //（可选）要显示的方法行数。 默认2
                .methodCount(2)
                //（可选）隐藏内部方法调用到偏移量。 默认5
                .methodOffset(7)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));


        //设置OkDownload下载路径及最大一起下载数
        OkDownload.getInstance().setFolder(Environment.getExternalStorageDirectory().getPath() + "/RainyNight/Cache/");
        OkDownload.getInstance().getThreadPool().setCorePoolSize(5);

        //初始化音乐播放器
        if (!BaseUtil.getCurProcessName(this).contains(":musicLibrary")) {

            //边播边存配置
            CacheConfig cacheConfig = new CacheConfig.Builder()
                    .setOpenCacheWhenPlaying(true)
                    .setCachePath(Constant.MUSIC_CACHE_PATH)
                    .build();

            MusicManager.get()
                    .setContext(this)
                    .setCacheConfig(cacheConfig)
                    .init();
        }

        MusicActionManager.getInstance().initContext(getApplicationContext());

        //内存泄漏检查
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
