package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.lzx.musiclibrary.aidl.listener.OnPlayerEventListener;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.db.CacheManager;
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
                mPosition--;
                loadPicture();
                MusicActionManager.getInstance().startPrevious();
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
                mPosition++;
                loadPicture();
                MusicActionManager.getInstance().startNext();
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

    private boolean isPlaying;
    private CountDownTimer mTimer;

    private long mCurrentTime = -1;

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
                    if (mCurrentTime == (long) values[0]) {
                        return;
                    }
                    mCurrentTime = (long) values[0];
                    if (mCurrentTime != -1) {
                        //设置定时时间
                        MusicActionManager.getInstance().setRemainTime(mCurrentTime);
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
        mTimer.start();
        rsbMusicTime.setValue(30);
        MusicActionManager.getInstance().addPlayerEventListener(this);
    }

    @Override
    protected void initData() {
        CacheEntity<MusicEntity> cache = CacheManager.getInstance()
                .get(Constant.DOLPHIN_MUSIC_CACHE + Constant.DOLPHIN_LIGHT_MUSIC, MusicEntity.class);
        if (cache == null) {
            toastShow("暂无数据");
            return;
        }
        mEntity = cache.getData();
        MusicActionManager.getInstance().start(Constant.DOLPHIN_LIGHT_MUSIC_CACHE, mPosition, Constant.PLAY_IN_SINGLE_LOOP, 30);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (!isScrolled) {
            loadPicture();
        } else {
            mHandler.sendEmptyMessage(1);
        }
    }

    /**
     * 加载图片
     */
    private void loadPicture() {
        GlideApp.with(_mActivity).load(mEntity.getData().get(mPosition).getAudioPictureUrl()).into(ivSleepMusicAudioPicture);
        new Handler().postDelayed(() -> {
            mImageWidth = ivSleepMusicAudioPicture.getWidth();
            if (mImageWidth != 0) {
                mScrollWidth = mImageWidth - mWindowWidth;
                if (mScrollWidth > 0) {
                    mHandler.sendEmptyMessage(1);
                    isScrolled = true;
                }
            }
        }, 1500);
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
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
