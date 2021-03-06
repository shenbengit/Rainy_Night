package com.example.ben.rainy_night.fragment.main_frag.frag;

import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.customer.BottomBar;
import com.example.ben.rainy_night.customer.BottomBarTab;
import com.example.ben.rainy_night.fragment.main_frag.contract.MainContract;
import com.example.ben.rainy_night.fragment.main_frag.presenter.MainPresenterImpl;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Ben
 */
public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View {

    @BindView(R.id.container_activity_main)
    FrameLayout containerActivityMain;
    @BindView(R.id.img_space)
    ImageView imgSpace;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;


    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void setPresenter() {
        presenter = new MainPresenterImpl(this);
    }

    @Override
    public void initView() {
        mBottomBar.addItem(new BottomBarTab(_mActivity, R.mipmap.ic_home, "睡觉"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_night, "助眠"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_share, "分享"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_mine, "我的"));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            //BottomBar按键选择的时候
            @Override
            public void onTabSelected(int position, int prePosition) {
                presenter.showHideFragment(position, prePosition);
            }

            //BottomBar按键未选择的时候
            @Override
            public void onTabUnselected(int position) {

            }

            //BottomBar按键重新选择的时候
            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
            }
        });
    }

    @Override
    public void initData() {
        presenter.loadRootFragment();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(ISupportFragment targetFragment) {
        start(targetFragment);
    }

    /**
     * start other BrotherFragment
     * 回掉传值
     */
    public void startBrotherFragmentForResult(ISupportFragment targetFragment, int requestCode) {
        startForResult(targetFragment, requestCode);
    }


    @Override
    public SupportFragment findChildFrag(Class fragmentClass) {
        return (SupportFragment) findFragment(fragmentClass);
    }

    @Override
    public void loadMultipleRootFrag(int containerId, int showPosition, ISupportFragment... toFragments) {
        loadMultipleRootFragment(containerId, showPosition, toFragments);
    }

    @Override
    public void showHideFrag(ISupportFragment showFragment, ISupportFragment hideFragment) {
        showHideFragment(showFragment, hideFragment);
    }

    /**
     * 按返回键返回主页面，不退出程序
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        return true;
    }
}
