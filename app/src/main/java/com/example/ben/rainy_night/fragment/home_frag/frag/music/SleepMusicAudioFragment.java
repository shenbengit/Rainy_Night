package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;

import butterknife.BindView;

/**
 * @author Ben
 * @date 2018/3/29
 */

public class SleepMusicAudioFragment extends BaseBackFragment{

    private static final String AUDIO_PICTURE_URL = "audio_picture_url";
    private static final String AUDIO_URL = "audio_url";

    @BindView(R.id.iv_sleep_music_audio_picture)
    ImageView ivSleepMusicAudioPicture;

    private String mAudioUrl;
    private String mAudioPictureUrl;

    public static SleepMusicAudioFragment newInstance(String audioUrl, String audioPictureUrl) {
        Bundle bundle = new Bundle();
        SleepMusicAudioFragment fragment = new SleepMusicAudioFragment();
        bundle.putString(AUDIO_URL, audioUrl);
        bundle.putString(AUDIO_PICTURE_URL, audioPictureUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_music_audio;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mAudioPictureUrl = bundle.getString(AUDIO_PICTURE_URL);
            mAudioUrl = bundle.getString(AUDIO_URL);
        }
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(mAudioPictureUrl)) {
            GlideApp.with(_mActivity).load(mAudioPictureUrl).into(ivSleepMusicAudioPicture);
        }

    }

    @Override
    protected boolean isTransparentStatusBar() {
        return true;
    }
}
