package com.example.ben.rainy_night.fragment.night_frag.frag;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.night_frag.adapter.SleepFmFragmentAdapter;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzx.musiclibrary.manager.TimerTaskManager;
import com.vondear.rxtools.RxTimeTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
                    SharedPreferencesUtil.getInstance(_mActivity).putValue(Constant.CYCLE_MODE, Constant.LIST_CYCLE);
                    setPlayMode(false);
                } else {
                    isSingleCycle = true;
                    SharedPreferencesUtil.getInstance(_mActivity).putValue(Constant.CYCLE_MODE, Constant.SINGLE_CYCLE);
                    setPlayMode(true);
                }
                break;
            case R.id.ib_remain_time:
                mPositionTime++;
                if (mPositionTime == mRemainTimes.length) {
                    mPositionTime = 0;
                }
                if (MusicActionManager.getInstance().isPlaying()) {
                    setRemainBg(mPositionTime);
                } else {
                    setRemainBg(mPositionTime);
                }
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
    private String[] mTitles = new String[]{Constant.DOLPHIN_HYPNOSIS_CACHE, Constant.DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE,
            Constant.DOLPHIN_NICE_PEOPLE_CACHE, Constant.DOLPHIN_SAY_GOOG_NIGHT_CACHE};

    @Override
    public int getLayout() {
        return R.layout.fragment_night;
    }

    @Override
    public void setPresenter() {

    }

    @Override
    public void initView() {
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
        switch (String.valueOf(SharedPreferencesUtil.getInstance(_mActivity).getValue(Constant.CYCLE_MODE, Constant.SINGLE_CYCLE))) {
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
                setRemainBg(mPositionTime);
                break;
            case Constant.REMAIN_TIME_10:
                mPositionTime = 1;
                setRemainBg(mPositionTime);
                break;
            case Constant.REMAIN_TIME_20:
                mPositionTime = 2;
                setRemainBg(mPositionTime);
                break;
            case Constant.REMAIN_TIME_30:
                mPositionTime = 3;
                setRemainBg(mPositionTime);
                break;
            default:
                mPositionTime = 0;
                setRemainBg(mPositionTime);
                break;
        }
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
        } else {
            ibCycleMode.setBackgroundResource(R.mipmap.ic_list_cycle);
            MusicActionManager.getInstance().setPlayMode(Constant.PLAY_IN_LIST_LOOP);
        }
    }

    /**
     * 定时背景
     *
     * @param position 位置
     */
    private void setRemainBg(int position) {
        ibRemainTime.setBackgroundResource(mRemainTimes[position]);
    }

    /**
     * 定时功能
     *
     * @param position 位置
     */
    private void setRemainTime(int position) {
        long remainTime;
        switch (position) {
            case 0:
                remainTime = (long) Constant.REMAIN_TIME_ZZ;
                break;
            case 1:
                remainTime = (long) Constant.REMAIN_TIME_10;
                break;
            case 2:
                remainTime = (long) Constant.REMAIN_TIME_20;
                break;
            case 3:
                remainTime = (long) Constant.REMAIN_TIME_30;
                break;
            default:
                remainTime = (long) Constant.REMAIN_TIME_ZZ;
                break;
        }
        MusicActionManager.getInstance().setRemainTime(remainTime);
    }

    @Override
    public void onMusicSwitch(SongInfo music) {
        if (TextUtils.equals(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        if (TextUtils.equals(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, MusicActionManager.getInstance().getCurrentMusicType())) {
            return;
        }
        tvNightMusicName.setText(music.getSongName());
        tvNightNowTime.setText("00:00");
        tvNightAllTime.setText(RxTimeTool.formatTime((music.getDuration() * 1000)));
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
