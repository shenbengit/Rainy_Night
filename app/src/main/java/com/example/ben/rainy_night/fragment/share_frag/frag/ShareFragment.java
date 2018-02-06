package com.example.ben.rainy_night.fragment.share_frag.frag;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.share_frag.adapter.RecyAdapter;
import com.example.ben.rainy_night.util.ToastUtil;
import com.github.mmin18.widget.RealtimeBlurView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 */
public class ShareFragment extends BaseFragment {

    @BindView(R.id.blurview)
    RealtimeBlurView blurview;
    @BindView(R.id.civ_share_head)
    CircleImageView civShareHead;
    @BindView(R.id.tv_share_name)
    TextView tvShareName;
    @BindView(R.id.tv_share_shuoshuo)
    TextView tvShareShuoshuo;
    @BindView(R.id.tv_share_PersonalSpace)
    TextView tvSharePersonalSpace;
    @BindView(R.id.tv_share_message)
    TextView tvShareMessage;
    @BindView(R.id.profile)
    FrameLayout profile;
    @BindView(R.id.rv_share)
    RecyclerView rvShare;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.refreshLayout_share)
    SmartRefreshLayout refreshLayoutShare;

    private RecyAdapter adapter;
    private List<String> list = new ArrayList<String>();

    private int index = 10;

    public static ShareFragment newInstance() {
        Bundle args = new Bundle();
        ShareFragment fragment = new ShareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 返回界面layout
     */
    @Override
    public int getLayout() {
        return R.layout.share_fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    public void setPresenter() {

    }

    /**
     * 初始化界面
     */
    @Override
    public void initView() {
        adapter = new RecyAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        rvShare.setLayoutManager(manager);
        rvShare.setAdapter(adapter);
        ClassicsHeader header = new ClassicsHeader(_mActivity);
        header.setSpinnerStyle(SpinnerStyle.FixedBehind);

        ClassicsFooter footer = new ClassicsFooter(_mActivity);
        footer.setSpinnerStyle(SpinnerStyle.FixedBehind);

        refreshLayoutShare.setRefreshHeader(header);
        refreshLayoutShare.setHeaderHeight(60);
        refreshLayoutShare.setRefreshFooter(footer);
        refreshLayoutShare.setFooterHeight(60);


        refreshLayoutShare.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                String a = list.get(0);
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(a);
                int num = Integer.parseInt(m.replaceAll("").trim());
                if (success) {
                    for (int i = 0; i < 3; i++) {
                        num++;
                        list.add(0, "第" + num + "个数据");
                    }
                    adapter.addData(list);
                } else {
                    ToastUtil.show(_mActivity, "刷新失败");
                }
            }

            @Override
            public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {
                String a = list.get(list.size() - 1);
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(a);
                int num = Integer.parseInt(m.replaceAll("").trim());
                if (num <= 1) {
                    ToastUtil.show(_mActivity, "暂时没有新的数据了");
                    return;
                }

                for (int i = 0; i < 3; i++) {
                    num--;
                    list.add(list.size(), "第" + num + "个数据");
                    if (num == 0) {
                        return;
                    }
                    adapter.addData(list);
                }
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }

            @Override
            public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        for (int i = 0; i < 20; i++) {
            index++;
            list.add("第" + index + "条数据");
        }
        Collections.reverse(list);
        adapter.addData(list);

    }
}
