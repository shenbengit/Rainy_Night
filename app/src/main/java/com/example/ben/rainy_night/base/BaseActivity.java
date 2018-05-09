package com.example.ben.rainy_night.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.example.ben.rainy_night.App;
import com.squareup.leakcanary.RefWatcher;

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
        setContentView(getLayout());
        ButterKnife.bind(this);
        setPresenter();
        initView();
        initData();
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
