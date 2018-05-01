package com.example.ben.rainy_night.fragment.night_frag.frag;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.night_frag.adapter.SleepFmFragmentAdapter;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.TimerTaskManager;
import com.vondear.rxtools.RxTimeTool;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 */
public class NightFragment extends BaseFragment implements OnPlayerEventListener {

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
    @BindView(R.id.ib_cycle_mode)
    ImageButton ibCycleMode;
    @BindView(R.id.ib_remain_time)
    ImageButton ibRemainTime;
    @BindView(R.id.toolbar_night)
    Toolbar toolbarNight;
    @BindView(R.id.tab_night)
    TabLayout tabNight;
    @BindView(R.id.vp_night)
    ViewPager vpNight;
    @BindView(R.id.iv_night_music_bg)
    ImageView ivNightMusicBg;
    @BindView(R.id.iv_toolbar_cover)
    ImageView ivToolbarCover;
    @BindView(R.id.tv_toolbar_music_name)
    TextView tvToolbarMusicName;
    @BindView(R.id.ib_toolbar_play)
    ImageButton ibToolbarPlay;
    @BindView(R.id.ib_toolbar_next)
    ImageButton ibToolbarNext;

    @OnClick({R.id.ib_night_music_play, R.id.ib_night_music_previous, R.id.ib_night_music_next,
            R.id.ib_cycle_mode, R.id.ib_remain_time, R.id.ib_toolbar_play, R.id.ib_toolbar_next})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_night_music_play:
                if (isPlaying) {
                    MusicActionManager.getInstance().pause();
                    ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_play);
                    ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_play);
                    isPlaying = false;
                } else {
                    MusicActionManager.getInstance().resume();
                    ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_pause);
                    ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_pause);
                    isPlaying = true;
                }
                break;
            case R.id.ib_night_music_previous:
                MusicActionManager.getInstance().startPrevious();
                break;
            case R.id.ib_night_music_next:
                MusicActionManager.getInstance().startNext();
                break;
            case R.id.ib_cycle_mode:
                if (isSingleCycle) {
                    isSingleCycle = false;
                    setPlayMode(false);
                    toastShow(Constant.LIST_CYCLE);
                } else {
                    isSingleCycle = true;
                    setPlayMode(true);
                    toastShow(Constant.SINGLE_CYCLE);
                }
                break;
            case R.id.ib_remain_time:
                mPositionTime++;
                if (mPositionTime == mRemainTimes.length) {
                    mPositionTime = 0;
                }

                setRemainTime(mPositionTime);
                break;
            case R.id.ib_toolbar_play:
                if (isPlaying) {
                    MusicActionManager.getInstance().pause();
                    ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_play);
                    ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_play);
                    isPlaying = false;
                } else {
                    MusicActionManager.getInstance().resume();
                    ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_pause);
                    ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_pause);
                    isPlaying = true;
                }
                break;
            case R.id.ib_toolbar_next:
                MusicActionManager.getInstance().startNext();
                break;
            default:
                break;
        }
    }

    private TimerTaskManager mManager;

    public static NightFragment newInstance() {
        return new NightFragment();
    }

    private boolean isPlaying = false;
    private boolean isSingleCycle = false;

    private int[] mRemainTimes = new int[]{R.mipmap.ic_time_zz, R.mipmap.ic_time_10, R.mipmap.ic_time_20, R.mipmap.ic_time_30};
    private int mPositionTime;
    private String[] mTitles = new String[]{Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE, Constant.DOLPHIN_NICE_PEOPLE_CACHE,
            Constant.DOLPHIN_HYPNOSIS_CACHE, Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE};

    private AnimationDrawable mDrawable;

    @Override
    public int getLayout() {
        return R.layout.fragment_night;
    }

    @Override
    public void setPresenter() {

    }

    @Override
    public void initView() {
        mDrawable = (AnimationDrawable) ivNightMusicBg.getDrawable();
        tvNightMusicName.setSelected(true);
        tvToolbarMusicName.setSelected(true);
        mManager = new TimerTaskManager();
        MusicActionManager.getInstance().addPlayerEventListener(this);
        ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_play);
        ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_play);
        tabNight.addTab(tabNight.newTab());
        tabNight.addTab(tabNight.newTab());
        tabNight.addTab(tabNight.newTab());
        tabNight.addTab(tabNight.newTab());
    }

    @Override
    public void initData() {
        switch (String.valueOf(SharedPreferencesUtil.getInstance(_mActivity).getValue(Constant.CYCLE_MODE, Constant.LIST_CYCLE))) {
            case Constant.SINGLE_CYCLE:
                isSingleCycle = true;
                setPlayMode(true);
                break;
            case Constant.LIST_CYCLE:
                isSingleCycle = false;
                setPlayMode(false);
                break;
            default:
                isSingleCycle = true;
                setPlayMode(true);
                break;
        }

        switch ((Integer) SharedPreferencesUtil.getInstance(_mActivity).getValue(Constant.REMAIN_TIME, Constant.REMAIN_TIME_ZZ)) {
            case Constant.REMAIN_TIME_ZZ:
                mPositionTime = 0;
                setRemainTime(mPositionTime);
                break;
            case Constant.REMAIN_TIME_10:
                mPositionTime = 1;
                setRemainTime(mPositionTime);
                break;
            case Constant.REMAIN_TIME_20:
                mPositionTime = 2;
                setRemainTime(mPositionTime);
                break;
            case Constant.REMAIN_TIME_30:
                mPositionTime = 3;
                setRemainTime(mPositionTime);
                break;
            default:
                mPositionTime = 0;
                setRemainTime(mPositionTime);
                break;
        }
        sbNightProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MusicActionManager.getInstance().seekTo(seekBar.getProgress());
            }
        });

        mManager.setUpdateProgressTask(this::updateProgress);
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        vpNight.setAdapter(new SleepFmFragmentAdapter(getChildFragmentManager(), mTitles));
        tabNight.setupWithViewPager(vpNight);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mManager.onRemoveUpdateProgressTask();
        MusicActionManager.getInstance().removePlayerEventListener(this);
    }

    /**
     * 设置循环模式
     *
     * @param isSingleCycle 循环模式
     */
    private void setPlayMode(boolean isSingleCycle) {
        if (isSingleCycle) {
            ibCycleMode.setBackgroundResource(R.mipmap.ic_single_cycle);
            MusicActionManager.getInstance().setPlayMode(Constant.PLAY_IN_SINGLE_LOOP);
            SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).putValue(Constant.CYCLE_MODE, Constant.LIST_CYCLE);
        } else {
            ibCycleMode.setBackgroundResource(R.mipmap.ic_list_cycle);
            MusicActionManager.getInstance().setPlayMode(Constant.PLAY_IN_LIST_LOOP);
            SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).putValue(Constant.CYCLE_MODE, Constant.LIST_CYCLE);
        }
    }

    /**
     * 定时功能
     *
     * @param position 位置
     */
    private void setRemainTime(int position) {
        ibRemainTime.setBackgroundResource(mRemainTimes[position]);
        int remainTime;
        switch (position) {
            case 0:
                remainTime = Constant.REMAIN_TIME_ZZ;
                break;
            case 1:
                remainTime = Constant.REMAIN_TIME_10;
                break;
            case 2:
                remainTime = Constant.REMAIN_TIME_20;
                break;
            case 3:
                remainTime = Constant.REMAIN_TIME_30;
                break;
            default:
                remainTime = Constant.REMAIN_TIME_ZZ;
                break;
        }
        SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).putValue(Constant.REMAIN_TIME, remainTime);
        MusicActionManager.getInstance().setRemainTime((long) remainTime);
    }

    /**
     * 更新进度
     */
    private void updateProgress() {
        long progress = MusicActionManager.getInstance().getProgress();
        sbNightProgress.setProgress((int) progress);
        tvNightNowTime.setText(RxTimeTool.formatTime((progress)));
    }

    @Override
    public void onMusicSwitch(SongInfo music) {
        if (TextUtils.equals(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        if (TextUtils.equals(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        sbNightProgress.setMax((int) music.getDuration());
        tvNightMusicName.setText(music.getSongName());
        tvNightNowTime.setText(getString(R.string._00_00));
        tvNightAllTime.setText(RxTimeTool.formatTime((music.getDuration())));
        tvToolbarMusicName.setText(music.getSongName());
    }

    @Override
    public void onPlayerStart() {
        if (TextUtils.equals(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        if (TextUtils.equals(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        mDrawable.start();
        mManager.scheduleSeekBarUpdate();
        ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_pause);
        ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_pause);
    }

    @Override
    public void onPlayerPause() {
        if (TextUtils.equals(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        if (TextUtils.equals(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        mDrawable.stop();
        mManager.stopSeekBarUpdate();
        ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_play);
        ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_play);
    }

    @Override
    public void onPlayCompletion() {
        if (TextUtils.equals(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        if (TextUtils.equals(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        mDrawable.stop();
        sbNightProgress.setProgress(0);
        tvNightNowTime.setText(getString(R.string._00_00));
        ibNightMusicPlay.setBackgroundResource(R.mipmap.ic_night_play);
        ibToolbarPlay.setBackgroundResource(R.mipmap.ic_night_play);

    }

    @Override
    public void onError(String errorMsg) {
        if (TextUtils.equals(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        if (TextUtils.equals(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }

    }

    @Override
    public void onAsyncLoading(boolean isFinishLoading) {

    }
}
