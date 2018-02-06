package com.example.ben.rainy_night.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ben.rainy_night.fragment.event.OnActivityResultEvent;
import com.example.ben.rainy_night.util.DialogLoadingUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.example.ben.rainy_night.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * 所有activity都应继承此类
 *
 * @author Ben
 * @date 2018/1/3
 */

public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity {
    public T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        setContentView(getLayout());
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 用EventBus向MyPersonalFragment传递onActivityResult()里返回的参数，
         * 因为Fragment里无法执行onActivityResult()方法。
         */
        EventBus.getDefault().post(new OnActivityResultEvent(requestCode, resultCode, data));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogLoadingUtil.getInstance(this).cancel();
    }


    /**
     * 设置presenter
     */
    protected abstract void setPresenter();

    /**
     * 返回界面layout
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 根据传入的class进行activity的跳转
     *
     * @param cls 传入Class
     */
    protected void jumpActivity(Class<? extends AppCompatActivity> cls) {
        if (cls != null) {
            startActivity(new Intent(this, cls));
        }
    }

    /**
     * 根据传入的class进行activity的跳转  可传入参数
     *
     * @param cls    传入class
     * @param bundle 传入参数
     */
    protected void jumpActivity(Class<? extends AppCompatActivity> cls, Bundle bundle) {
        if (cls != null) {
            Intent intent = new Intent(this, cls);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    protected void toastShow(String text) {
        ToastUtil.show(getApplicationContext(), text);
    }

    /**
     * 显示网络加载Dialog
     */
    protected void dialogShow() {
        DialogLoadingUtil.getInstance(this).show();
    }

    /**
     * @return 网络加载Dialog是否正在显示
     */
    protected boolean dialogIsShow() {
        return DialogLoadingUtil.getInstance(this).isShowing();
    }

    /**
     * 关闭网络加载Dialog
     */
    protected void dialogCancel() {
        DialogLoadingUtil.getInstance(this).cancel();
    }

    /**
     * 使用SharedPreferences存储信息
     *
     * @param keyName 键
     * @param value   值
     */
    protected void putSharedPreferences(String keyName, Object value) {
        SharedPreferencesUtil.getInstance(getApplicationContext()).putValue(keyName, value);
    }

    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     *
     * @param keyName      键
     * @param defaultValue 默认值
     * @return
     */
    protected Object getSharedPreferences(String keyName, Object defaultValue) {
        return SharedPreferencesUtil.getInstance(getApplicationContext()).getValue(keyName, defaultValue);
    }

}
