package com.example.ben.rainy_night.fragment.night_frag.frag;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.night_frag.contract.SleepFmContract;
import com.example.ben.rainy_night.fragment.night_frag.presenter.SleepFmPresenter;

import butterknife.BindView;

/**
 * @author Ben
 * @date 2018/4/24
 */

public class SleepFmFragment extends BaseFragment<SleepFmContract.Presenter> implements SleepFmContract.View {

    private static final String TITLE = "title";

    @BindView(R.id.recy_sleep_fm_list)
    RecyclerView recySleepFmList;

    public static SleepFmFragment newInstance(String title) {
        SleepFmFragment fmFragment = new SleepFmFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        fmFragment.setArguments(bundle);
        return fmFragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_fm;
    }

    @Override
    protected void setPresenter() {
        presenter = new SleepFmPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

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
    public void showToast(String text) {
        toastShow(text);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void cancelDialog() {

    }
}
