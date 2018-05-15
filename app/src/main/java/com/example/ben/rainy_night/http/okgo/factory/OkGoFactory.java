package com.example.ben.rainy_night.http.okgo.factory;


import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;

/**
 * @author Ben
 * @date 2018/3/18
 */

public class OkGoFactory {

    private CompositeDisposable mDisposable;

    public static OkGoFactory getInstance() {
        return Holder.FACTORY;
    }

    private OkGoFactory() {
        mDisposable = new CompositeDisposable();
    }

    private static final class Holder {
        private static final OkGoFactory FACTORY = new OkGoFactory();
    }

    /**
     * 初始化OkGo
     *
     * @param application
     */
    public void init(Application application) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor("OkGo");
        interceptor.setPrintLevel(HttpLoggingInterceptor.Level.BASIC);
        interceptor.setColorLevel(Level.INFO);

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();

        OkHttpClient mClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cookieJar(new CookieJarImpl(new DBCookieStore(application)))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
                .build();

        OkGo.getInstance().init(application)
                .setOkHttpClient(mClient)
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(3);
    }

    public void addDisposable(Disposable disposable) {
        mDisposable.add(disposable);
    }

    public void dispose() {
        mDisposable.dispose();
    }


}
