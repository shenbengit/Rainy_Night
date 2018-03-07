package com.example.ben.rainy_night.fragment.mine_frag.frag.space;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.bean.UserBean;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.flyco.roundview.RoundTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 * @date 2018/2/9
 */

public class SpaceFragment extends BaseBackFragment {

    @BindView(R.id.parallax_space)
    ImageView parallaxSpace;
    @BindView(R.id.toolbar_space)
    Toolbar toolbarSpace;
    @BindView(R.id.buttonBarLayout_space)
    ButtonBarLayout buttonBarLayoutSpace;
    @BindView(R.id.refreshLayout_space)
    SmartRefreshLayout refreshLayoutSpace;
    @BindView(R.id.scrollView_space)
    NestedScrollView scrollViewSpace;
    @BindView(R.id.rtv_post_story)
    RoundTextView rtvPostStory;
    @BindView(R.id.recy_space)
    RecyclerView recySpace;
    @BindView(R.id.tv_space_nickName)
    TextView tvSpaceNickName;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.civ_space_head)
    CircleImageView civSpaceHead;
    @BindView(R.id.collapse)
    CollapsingToolbarLayout collapse;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.civ_space_toolbar_head)
    CircleImageView civSpaceToolbarHead;
    @BindView(R.id.tv_space_toolbar_nickName)
    TextView tvSpaceToolbarNickName;

    @OnClick({R.id.rtv_post_story})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.rtv_post_story:
                start(PostStoryFragment.newInstance());
                break;
            default:
                break;
        }
    }

    private int mOffset = 0;
    private int mScrollY = 0;

    public static SpaceFragment newInstance() {
        SpaceFragment fragment = new SpaceFragment();
        return fragment;
    }

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.space_fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {

    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        refreshLayoutSpace.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });
        refreshLayoutSpace.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
                parallaxSpace.setTranslationY(mOffset - mScrollY);
                buttonBarLayoutSpace.setAlpha(0);
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
                parallaxSpace.setTranslationY(mOffset - mScrollY);
                toolbarSpace.setAlpha(1 - Math.min(percent, 1));
            }
        });
        scrollViewSpace.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(_mActivity, R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBarLayoutSpace.setAlpha(1f * mScrollY / h);
                    toolbarSpace.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    parallaxSpace.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });
        buttonBarLayoutSpace.setAlpha(0);
        toolbarSpace.setBackgroundColor(0);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        UserBean bean = BmobUser.getCurrentUser(UserBean.class);
        LoggerUtil.e("SpaceFragment: " + bean.getObjectId());
    }

    /**
     * 是否透明化状态栏
     *
     * @return
     */
    @Override
    protected boolean isTransparentStatusBar() {
        return true;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (mUserBean == null) {
            tvSpaceNickName.setText("");
            tvSpaceToolbarNickName.setText("");
            civSpaceHead.setImageResource(R.mipmap.ic_head);
            civSpaceToolbarHead.setImageResource(R.mipmap.ic_head);
            return;
        }
        tvSpaceNickName.setText(mUserBean.getNickName());
        tvSpaceToolbarNickName.setText(mUserBean.getNickName());
        if (mUserBean.getHeadimg() != null) {
            GlideApp.with(_mActivity)
                    .load(mUserBean.getHeadimg().getFileUrl())
                    .placeholder(R.mipmap.ic_head)
                    .error(R.mipmap.ic_head)
                    .into(civSpaceHead);
            GlideApp.with(_mActivity)
                    .load(mUserBean.getHeadimg().getFileUrl())
                    .placeholder(R.mipmap.ic_head)
                    .error(R.mipmap.ic_head)
                    .into(civSpaceToolbarHead);
        }


    }
}
