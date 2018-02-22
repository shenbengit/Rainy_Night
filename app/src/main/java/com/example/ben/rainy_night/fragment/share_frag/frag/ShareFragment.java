package com.example.ben.rainy_night.fragment.share_frag.frag;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.share_frag.adapter.ItemAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;

/**
 * @author Ben
 */
public class ShareFragment extends BaseFragment{

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recy_share)
    RecyclerView recyShare;

    private ItemAdapter mAdapter;

    private List<String> mItemList = new ArrayList<>();

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
        smartRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        ClassicsHeader header = new ClassicsHeader(_mActivity);
        header.setSpinnerStyle(SpinnerStyle.Translate);
        smartRefreshLayout.setRefreshHeader(header);
//        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(_mActivity).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setDisableContentWhenRefresh(true);

        mAdapter = new ItemAdapter(R.layout.item_recycler_share);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyShare.setLayoutManager(linearLayoutManager);
        recyShare.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        },recyShare);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        for (int i = 0; i <= 15; i++) {
            index++;
            mItemList.add("item" + index);
        }
        Collections.reverse(mItemList);
        mAdapter.setNewData(mItemList);
    }

    private List<String> addData() {
        List<String> data = new ArrayList<>();
        for (int i = mItemList.size() + 1; i <= mItemList.size() + 3; i++) {
            data.add("item" + i);
        }
        Collections.reverse(data);
        return data;
    }
}
