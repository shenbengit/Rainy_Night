package com.example.ben.rainy_night.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ben.rainy_night.App;
import com.example.ben.rainy_night.fragment.event.OnActivityResultEvent;
import com.example.ben.rainy_night.util.DialogLoadingUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.squareup.leakcanary.RefWatcher;

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
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
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

}
