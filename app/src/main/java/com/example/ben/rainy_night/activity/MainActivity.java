package com.example.ben.rainy_night.activity;

import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseActivity;
import com.example.ben.rainy_night.event.OnActivityResultEvent;
import com.example.ben.rainy_night.fragment.main_frag.frag.MainFragment;
import com.example.ben.rainy_night.fragment.main_frag.frag.SplashFragment;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;
import com.gyf.barlibrary.OnKeyboardListener;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * @author Ben
 */
public class MainActivity extends BaseActivity {

    private ImmersionBar mImmersionBar;
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";

    @Override
    public void setPresenter() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

        //解决华为emui3.0与3.1手机手动隐藏底部导航栏时，导航栏背景色未被隐藏的问题
        if (OSUtils.isEMUI3_1()) {
            //第一种
            getContentResolver().registerContentObserver(Settings.System.
                    getUriFor(NAVIGATIONBAR_IS_MIN), true, mNavigationStatusObserver);
            //第二种,禁止对导航栏的设置
            //mImmersionBar.navigationBarEnable(false).init();
        }
    }

    @Override
    public void initData() {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.frame_main, SplashFragment.newInstance());
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
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
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    NAVIGATIONBAR_IS_MIN, 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                mImmersionBar.transparentNavigationBar().init();
            } else {
                //导航键显示了
                //隐藏前导航栏的颜色
                mImmersionBar.navigationBarColor(android.R.color.transparent)
                        .fullScreen(true)
                        .init();
            }
        }
    };

}
