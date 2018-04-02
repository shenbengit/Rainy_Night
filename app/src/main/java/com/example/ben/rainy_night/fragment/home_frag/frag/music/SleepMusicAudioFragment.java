package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicAudioContract;
import com.example.ben.rainy_night.fragment.home_frag.presenter.SleepMusicAudioPresenter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

/**
 * @author Ben
 * @date 2018/3/29
 */

public class SleepMusicAudioFragment extends BaseFragment<SleepMusicAudioContract.Presenter> implements SleepMusicAudioContract.View {

    private static final String PICTURE_URL = "picture_url";
    private static final String VIDEO_URL = "video_url";
    private static final String AUDIO_URL = "audio_url";

    @BindView(R.id.video_music)
    VideoView videoMusic;
    @BindView(R.id.iv_sleep_music_picture)
    ImageView ivSleepMusicPicture;

    private String mVideoUrl;
    private String mAudioUrl;
    private String mPictureUrl;

    public static SleepMusicAudioFragment newInstance(String pictureUrl, String videoUrl, String audioUrl) {
        Bundle bundle = new Bundle();
        SleepMusicAudioFragment fragment = new SleepMusicAudioFragment();
        bundle.putString(PICTURE_URL, pictureUrl);
        bundle.putString(VIDEO_URL, videoUrl);
        bundle.putString(AUDIO_URL, audioUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_music_audio;
    }

    @Override
    protected void setPresenter() {
        presenter = new SleepMusicAudioPresenter(this);
    }

    @Override
    protected void initView() {
//        ImmersionBar.with(_mActivity).transparentStatusBar().init();

        Bundle bundle = getArguments();
        if (bundle != null) {
            mPictureUrl = bundle.getString(PICTURE_URL);
            mVideoUrl = bundle.getString(VIDEO_URL);
            mAudioUrl = bundle.getString(AUDIO_URL);
        }
    }

    @Override
    protected void initData() {
        if (TextUtils.isEmpty(mVideoUrl)) {
            ivSleepMusicPicture.setVisibility(View.VISIBLE);
            GlideApp.with(_mActivity).load(mPictureUrl).into(ivSleepMusicPicture);
        } else {

        }
        presenter.initProxy(mVideoUrl, mAudioUrl);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public boolean isNetworkAvailable() {
        return true;
    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void cancelDialog() {

    }

    @Override
    public VideoView getVideo() {
        return videoMusic;
    }

    @Override
    public Context getCon() {
        return _mActivity.getApplicationContext();
    }

}
