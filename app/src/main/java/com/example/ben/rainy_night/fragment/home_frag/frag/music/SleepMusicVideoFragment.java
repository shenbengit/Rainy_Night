package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicVideoContract;
import com.example.ben.rainy_night.fragment.home_frag.presenter.SleepMusicVideoPresenter;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.widget.FullScreenVideoView;

import butterknife.BindView;

/**
 * @author Ben
 * @date 2018/4/3
 */

public class SleepMusicVideoFragment extends BaseBackFragment<SleepMusicVideoContract.Presenter> implements SleepMusicVideoContract.View {

    private static final String VIDEO_URL = "video_url";
    private static final String VIDEO_PICTURE_URL = "video_picture_url";
    private static final String AUDIO_URL = "audio_url";

    @BindView(R.id.video_view)
    SurfaceView videoView;
    @BindView(R.id.iv_sleep_music_video_picture)
    ImageView ivSleepMusicVideoPicture;

    private String mVideoUrl;
    private String mVideoPictureUrl;
    private String mAudioUrl;

    public static SleepMusicVideoFragment newInstance(String videoUrl, String videoPictureUrl, String audioUrl) {
        Bundle bundle = new Bundle();
        SleepMusicVideoFragment fragment = new SleepMusicVideoFragment();
        bundle.putString(VIDEO_URL, videoUrl);
        bundle.putString(VIDEO_PICTURE_URL, videoPictureUrl);
        bundle.putString(AUDIO_URL, audioUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_music_video;
    }

    @Override
    protected void setPresenter() {
        presenter = new SleepMusicVideoPresenter(this);
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mVideoPictureUrl = bundle.getString(VIDEO_PICTURE_URL);
            mVideoUrl = bundle.getString(VIDEO_URL);
            mAudioUrl = bundle.getString(AUDIO_URL);
        }
    }

    @Override
    protected void initData() {
        GlideApp.with(_mActivity).load(mVideoPictureUrl).into(ivSleepMusicVideoPicture);

        presenter.initProxy(mVideoUrl);
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return true;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        presenter.startVideo();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        new Handler().postDelayed(() -> presenter.pauseVideo(),500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LoggerUtil.e("此Fragment销毁");
        presenter.stopVideo();
    }

    @Override
    public boolean isNetworkAvailable() {
        return isNetAvailable();
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
    public Context getCon() {
        return _mActivity;
    }

    @Override
    public SurfaceView getVideoView() {
        return videoView;
    }

    @Override
    public ImageView getImageView() {
        return ivSleepMusicVideoPicture;
    }

}
