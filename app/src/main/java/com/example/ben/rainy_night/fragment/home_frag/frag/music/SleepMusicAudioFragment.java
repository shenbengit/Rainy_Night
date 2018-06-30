package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.GsonUtil;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.vondear.rxtools.view.RxSeekBar;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author Ben
 * @date 2018/3/29
 */

public class SleepMusicAudioFragment extends BaseFragment implements OnPlayerEventListener {

    private static final String POSITION = "position";

    @BindView(R.id.frame_sleep_music_audio)
    FrameLayout frameSleepMusicAudio;
    @BindView(R.id.hsv_audio_picture)
    HorizontalScrollView hsvAudioPicture;
    @BindView(R.id.iv_sleep_music_audio_picture)
    ImageView ivSleepMusicAudioPicture;
    @BindView(R.id.linear_music)
    LinearLayout linearMusic;
    @BindView(R.id.ib_music_previous)
    ImageButton ibMusicPrevious;
    @BindView(R.id.ib_music_isPlay)
    ImageButton ibMusicIsPlay;
    @BindView(R.id.ib_music_next)
    ImageButton ibMusicNext;
    @BindView(R.id.rsb_music_time)
    RxSeekBar rsbMusicTime;

    @OnClick({R.id.ib_sleep_audio_back, R.id.ib_music_previous, R.id.ib_music_isPlay, R.id.ib_music_next})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_sleep_audio_back:
                _mActivity.onBackPressed();
                break;
            case R.id.ib_music_previous:
                mTimer.cancel();
                MusicActionManager.getInstance().startPrevious();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    mPosition=MusicActionManager.getInstance().getCurrPlayingIndex();
                    loadPicture();
                },500);
                mTimer.start();
                break;
            case R.id.ib_music_isPlay:
                mTimer.cancel();
                if (!isPlaying) {
                    ibMusicIsPlay.setBackgroundResource(R.mipmap.music_pause);
                    MusicActionManager.getInstance().resume();
                    mHandler.sendEmptyMessage(1);
                    isPlaying = true;
                } else {
                    ibMusicIsPlay.setBackgroundResource(R.mipmap.music_start);
                    MusicActionManager.getInstance().pause();
                    mHandler.removeMessages(1);
                    isPlaying = false;
                }
                mTimer.start();
                break;
            case R.id.ib_music_next:
                mTimer.cancel();
                MusicActionManager.getInstance().startNext();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    mPosition=MusicActionManager.getInstance().getCurrPlayingIndex();
                    loadPicture();
                },500);
                mTimer.start();
                break;
            default:
                break;
        }
    }

    @OnTouch({R.id.frame_sleep_music_audio, R.id.rsb_music_time})
    public boolean viewOnTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.frame_sleep_music_audio:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTimer.cancel();
                        linearMusic.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        mTimer.start();
                        break;
                    default:
                        break;
                }
                break;
            case R.id.rsb_music_time:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTimer.cancel();
                        linearMusic.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessage(3);
                        mTimer.start();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return false;
    }

    public static SleepMusicAudioFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        SleepMusicAudioFragment fragment = new SleepMusicAudioFragment();
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private MusicEntity mEntity;
    private int mPosition;
    /**
     * 图片宽度
     */
    private int mImageWidth;
    /**
     * 屏幕宽度
     */
    private int mWindowWidth;
    /**
     * 实际滑动宽度 mScrollWidth = mImageWidth - mWindowWidth;
     */
    private int mScrollWidth;
    /**
     * 每次滑动间隔
     */
    private int mScroll;

    /**
     * 判断是否向右滑倒底部 是则向左滑动
     */
    private boolean isFocusDown;
    /**
     * 是否正在滚动
     */
    private boolean isScrolled;
    /**
     * 是否正在播放
     */
    private boolean isPlaying;
    /**
     * 对用户是否可见
     */
    private boolean isVisible;

    /**
     * 隐藏音乐播放功能布局
     */
    private CountDownTimer mTimer;
    /**
     * 设置音乐定时播放功能
     */
    private CountDownTimer mRemainTimer;

    private long mRemainTime = -1;
    private int index;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!isFocusDown) {
                        mScroll++;
                        hsvAudioPicture.scrollTo(mScroll, 1);
                        if (mScroll == mScrollWidth) {
                            isFocusDown = true;
                        }
                    } else {
                        mScroll--;
                        hsvAudioPicture.scrollTo(mScroll, 1);
                        if (mScroll == 0) {
                            isFocusDown = false;
                        }
                    }
                    mHandler.sendEmptyMessageDelayed(1, 100);
                    break;
                case 2:
                    linearMusic.setVisibility(View.GONE);
                    break;
                case 3:
                    float[] values = rsbMusicTime.getCurrentRange();
                    if (mRemainTime == (long) values[0]) {
                        return;
                    }
                    mRemainTime = (long) values[0];
                    if (mRemainTime != -1) {
                        //设置定时时间
                        if (mRemainTimer != null) {
                            mRemainTimer.cancel();
                            mRemainTimer = null;
                        }
                        mRemainTimer = new CountDownTimer(mRemainTime * 60 * 1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                index++;
                                if (index == 60) {
                                    mRemainTime--;
                                    if (isVisible) {
                                        rsbMusicTime.setValue(mRemainTime);
                                    }
                                    index = 0;
                                }
                                if (mRemainTime < 0) {
                                    mRemainTime = 0;
                                }
                            }

                            @Override
                            public void onFinish() {
                                mRemainTime = 0;
                                if (isVisible) {
                                    rsbMusicTime.setValue(0);
                                }
                                MusicActionManager.getInstance().pause();
                            }
                        };
                        mRemainTimer.start();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.fragment_sleep_music_audio;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected void initView() {
        //获取屏幕宽度
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        mWindowWidth = dm.widthPixels;

        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(POSITION);
        }

        mTimer = new CountDownTimer(3 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                mHandler.sendEmptyMessage(2);
            }
        };
        rsbMusicTime.setValue(30);
        mHandler.sendEmptyMessage(3);
        MusicActionManager.getInstance().addPlayerEventListener(this);
    }

    @Override
    protected void initData() {
        mEntity = GsonUtil.fromJson(String.valueOf(SharedPreferencesUtil.getInstance(_mActivity.getApplicationContext()).getValue(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, "")), MusicEntity.class);
        MusicActionManager.getInstance().start(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, mPosition, Constant.PLAY_IN_SINGLE_LOOP);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        isVisible = true;
        if (!isScrolled) {
            loadPicture();
        } else {
            mHandler.sendEmptyMessage(1);
        }
        rsbMusicTime.setValue(mRemainTime);
        mTimer.start();
    }

    /**
     * 加载图片
     */
    private void loadPicture() {
        GlideApp.with(_mActivity).load(mEntity.getData().get(mPosition).getAudioPictureUrl()).into(ivSleepMusicAudioPicture);
        new Handler().postDelayed(() -> {
            //图片宽度
            mImageWidth = ivSleepMusicAudioPicture.getWidth();
            if (mImageWidth != 0) {
                //平移长度
                mScrollWidth = mImageWidth - mWindowWidth;
                if (mScrollWidth > 0) {
                    mScroll = 0;
                    mHandler.sendEmptyMessage(1);
                    isScrolled = true;
                }
            }
        }, 1500);
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        isVisible = false;
        mHandler.removeMessages(1);
        mHandler.removeMessages(2);
        mHandler.removeMessages(3);
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mRemainTimer != null) {
            mRemainTimer.cancel();
            mRemainTimer = null;
        }
        MusicActionManager.getInstance().stop();
        MusicActionManager.getInstance().removePlayerEventListener(this);

    }

    @Override
    protected boolean isTransparentStatusBar() {
        return true;
    }

    @Override
    public void onMusicSwitch(SongInfo music) {
        if (MusicActionManager.getInstance().getCurrPlayingIndex() != mPosition) {
            mPosition = MusicActionManager.getInstance().getCurrPlayingIndex();
            isScrolled = false;
        }
    }

    @Override
    public void onPlayerStart() {

    }

    @Override
    public void onPlayerPause() {

    }

    @Override
    public void onPlayCompletion() {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onAsyncLoading(boolean isFinishLoading) {

    }
}
