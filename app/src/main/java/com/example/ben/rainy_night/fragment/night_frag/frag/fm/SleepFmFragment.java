package com.example.ben.rainy_night.fragment.night_frag.frag.fm;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.night_frag.contract.SleepFmContract;
import com.example.ben.rainy_night.fragment.night_frag.presenter.SleepFmPresenterImpl;

import butterknife.BindView;

/**
 * @author Ben
 * @date 2018/4/24
 */

public class SleepFmFragment extends BaseFragment<SleepFmContract.Presenter> implements SleepFmContract.View {

    private static final String ALBUMS_ID = "AlbumsId";

    @BindView(R.id.recy_sleep_fm_list)
    RecyclerView recySleepFmList;

    public static SleepFmFragment newInstance(int albumsId) {
        SleepFmFragment fmFragment = new SleepFmFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ALBUMS_ID, albumsId);
        fmFragment.setArguments(bundle);
        return fmFragment;
    }

    private int mAlbumsId;

    private boolean isVisibleToUser;


    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_fm;
    }

    @Override
    protected void setPresenter() {
        presenter = new SleepFmPresenterImpl(this);
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mAlbumsId = bundle.getInt(ALBUMS_ID);
        }

        presenter.init(mAlbumsId);
    }

    @Override
    protected void initData() {
        presenter.getAlbumsMediaList();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public boolean isNetworkAvailable() {
        return isNetAvailable();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        isVisibleToUser = true;
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        isVisibleToUser = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }

    @Override
    public void showToast(String text) {
        toastShow(text);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void cancelDialog() {

    }

    @Override
    public Context getCon() {
        return _mActivity;
    }

    @Override
    public RecyclerView getRecycler() {
        return recySleepFmList;
    }

    @Override
    public boolean isVisibleToUser() {
        return isVisibleToUser;
    }
}
