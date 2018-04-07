package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.danikula.videocache.HttpProxyCacheServer;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicVideoContract;
import com.example.ben.rainy_night.fragment.home_frag.presenter.SleepMusicVideoPresenter;
import com.example.ben.rainy_night.manager.MusicPlayManager;
import com.example.ben.rainy_night.util.HttpProxyUtil;
import com.example.ben.rainy_night.widget.FullScreenVideoView;
import com.vondear.rxtools.view.RxSeekBar;

import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ben
 * @date 2018/4/3
 */

public class SleepMusicVideoFragment extends BaseBackFragment<SleepMusicVideoContract.Presenter> implements SleepMusicVideoContract.View {

    private static final String VIDEO_URL = "video_url";
    private static final String VIDEO_PICTURE_URL = "video_picture_url";
    private static final String AUDIO_URL = "audio_url";

    @BindView(R.id.video)
    FullScreenVideoView video;
    @BindView(R.id.iv_sleep_music_video_picture)
    ImageView ivSleepMusicVideoPicture;
    @BindView(R.id.ib_music_previous)
    ImageButton ibMusicPrevious;
    @BindView(R.id.ib_music_isPlay)
    ImageButton ibMusicIsPlay;
    @BindView(R.id.ib_music_next)
    ImageButton ibMusicNext;
    @BindView(R.id.rsb_music_time)
    RxSeekBar rsbMusicTime;
    @BindView(R.id.linear_music)
    LinearLayout linearMusic;

    @OnClick({R.id.ib_music_previous, R.id.ib_music_isPlay, R.id.ib_music_next})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_music_previous:

                break;
            case R.id.ib_music_isPlay:
                if (isPlaying) {
                    ibMusicIsPlay.setBackgroundResource(R.mipmap.music_pause);
                    MusicPlayManager.gerInstance().pause();
                    isPlaying = false;
                } else {
                    ibMusicIsPlay.setBackgroundResource(R.mipmap.music_start1);
                    MusicPlayManager.gerInstance().resume();
                    isPlaying = true;
                }
                break;
            case R.id.ib_music_next:

                break;
            default:
                break;
        }
    }

    private String mVideoUrl;
    private String mVideoPictureUrl;
    private String mAudioUrl;

    private boolean isPlaying = true;

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

        MusicPlayManager.gerInstance().start(mAudioUrl);
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
    public FullScreenVideoView getVideoView() {
        return video;
    }

    @Override
    public ImageView getImageView() {
        return ivSleepMusicVideoPicture;
    }
}
