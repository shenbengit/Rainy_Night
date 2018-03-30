package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicAudioContract;
import com.example.ben.rainy_night.fragment.home_frag.presenter.SleepMusicAudioPresenter;

import butterknife.BindView;

/**
 * @author Ben
 * @date 2018/3/29
 */

public class SleepMusicAudioFragment extends BaseBackFragment<SleepMusicAudioContract.Presenter> implements SleepMusicAudioContract.View {

    private static final String VIDEO_URL = "video_url";
    private static final String AUDIO_URL = "audio_url";
    @BindView(R.id.video_music)
    VideoView videoMusic;

    private String mVideoUrl;
    private String mAudioUrl;

    public static SleepMusicAudioFragment newInstance(String videoUrl, String audioUrl) {
        Bundle bundle = new Bundle();
        SleepMusicAudioFragment fragment = new SleepMusicAudioFragment();
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            mVideoUrl = bundle.getString(VIDEO_URL);
            mAudioUrl = bundle.getString(AUDIO_URL);
        }
    }

    @Override
    protected void initData() {
        presenter.initProxy(mVideoUrl,mAudioUrl);
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        presenter.startAudio();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return true;
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
