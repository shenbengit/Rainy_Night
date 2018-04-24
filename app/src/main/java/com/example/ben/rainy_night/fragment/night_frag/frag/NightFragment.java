package com.example.ben.rainy_night.fragment.night_frag.frag;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.night_frag.adapter.SleepFmFragmentAdapter;
import com.example.ben.rainy_night.util.Constant;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 */
public class NightFragment extends BaseFragment {


    @BindView(R.id.tv_night_music_name)
    TextView tvNightMusicName;
    @BindView(R.id.civ_night_cover)
    CircleImageView civNightCover;
    @BindView(R.id.ib_night_music_play)
    ImageButton ibNightMusicPlay;
    @BindView(R.id.ib_night_music_previous)
    ImageButton ibNightMusicPrevious;
    @BindView(R.id.ib_night_music_next)
    ImageButton ibNightMusicNext;
    @BindView(R.id.tv_night_now_time)
    TextView tvNightNowTime;
    @BindView(R.id.sb_night_progress)
    SeekBar sbNightProgress;
    @BindView(R.id.tv_night_all_time)
    TextView tvNightAllTime;
    @BindView(R.id.tab_night)
    TabLayout tabNight;
    @BindView(R.id.vp_night)
    ViewPager vpNight;
    @BindView(R.id.iv_toolbar_cover)
    ImageView ivToolbarCover;
    @BindView(R.id.id_toolbar_music_name)
    TextView idToolbarMusicName;
    @BindView(R.id.ib_toolbar_play)
    ImageButton ibToolbarPlay;
    @BindView(R.id.ib_toolbar_next)
    ImageButton ibToolbarNext;

    @OnClick({R.id.ib_night_music_play, R.id.ib_night_music_previous, R.id.ib_night_music_next, R.id.ib_toolbar_play, R.id.ib_toolbar_next})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_night_music_play:
                if (isPlaying) {
                    ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_play);
                    ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_play);
                    isPlaying = false;
                } else {
                    ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_pause);
                    ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_pause);
                    isPlaying = true;
                }
                break;
            case R.id.ib_night_music_previous:

                break;
            case R.id.ib_night_music_next:

                break;
            case R.id.ib_toolbar_play:
                if (isPlaying) {
                    ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_play);
                    ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_play);
                    isPlaying = false;
                } else {
                    ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_pause);
                    ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_pause);
                    isPlaying = true;
                }
                break;
            case R.id.ib_toolbar_next:

                break;
            default:
                break;
        }
    }

    public static NightFragment newInstance() {
        return new NightFragment();
    }

    private boolean isPlaying = false;

    @Override
    public int getLayout() {
        return R.layout.fragment_night;
    }

    @Override
    public void setPresenter() {

    }

    @Override
    public void initView() {
        ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_play);
        ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_play);
        tabNight.addTab(tabNight.newTab());
        tabNight.addTab(tabNight.newTab());
        tabNight.addTab(tabNight.newTab());
        tabNight.addTab(tabNight.newTab());
    }

    @Override
    public void initData() {

    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        vpNight.setAdapter(new SleepFmFragmentAdapter(getChildFragmentManager(),
                Constant.DOLPHIN_HYPNOSIS_CACHE, Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE,
                Constant.DOLPHIN_NICE_PEOPLE_CACHE, Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE));
        tabNight.setupWithViewPager(vpNight);
    }
}
