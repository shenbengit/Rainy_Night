package com.example.ben.rainy_night.fragment.home_frag.frag.music;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.home_frag.contract.SleepMusicVideoContract;
import com.example.ben.rainy_night.fragment.home_frag.presenter.SleepMusicVideoPresenter;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.lzx.musiclibrary.aidl.model.SongInfo;
import com.lzx.musiclibrary.manager.MusicManager;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.db.CacheManager;
import com.vondear.rxtools.view.RxSeekBar;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author Ben
 * @date 2018/4/3
 */

public class SleepMusicVideoFragment extends BaseFragment<SleepMusicVideoContract.Presenter> implements SleepMusicVideoContract.View {

    private static final String POSITION = "position";

    @BindView(R.id.video)
    VideoView video;
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
    @BindView(R.id.frame_sleep_music_video)
    FrameLayout frameSleepMusicVideo;

    @OnClick({R.id.ib_sleep_video_back, R.id.ib_music_previous, R.id.ib_music_isPlay, R.id.ib_music_next})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_sleep_video_back:
                _mActivity.onBackPressed();
                break;
            case R.id.ib_music_previous:
                mTimer.cancel();
                MusicActionManager.getInstance().startPrevious(Constant.DOLPHIN_NATURAL_MUSIC_CACHE);
                presenter.startPrevious();
                mTimer.start();
                break;
            case R.id.ib_music_isPlay:
                mTimer.cancel();
                if (!isPlaying) {
                    ibMusicIsPlay.setBackgroundResource(R.mipmap.music_pause);
                    MusicActionManager.getInstance().resume(Constant.DOLPHIN_NATURAL_MUSIC_CACHE);
                    presenter.resumeVideo();
                    isPlaying = true;
                } else {
                    ibMusicIsPlay.setBackgroundResource(R.mipmap.music_start);
                    MusicActionManager.getInstance().pause(Constant.DOLPHIN_NATURAL_MUSIC_CACHE);
                    presenter.pauseVideo();
                    isPlaying = false;
                }
                mTimer.start();
                break;
            case R.id.ib_music_next:
                mTimer.cancel();
                MusicActionManager.getInstance().startNext(Constant.DOLPHIN_NATURAL_MUSIC_CACHE);
                presenter.startNext();
                mTimer.start();
                break;
            default:
                break;
        }
    }

    @OnTouch({R.id.frame_sleep_music_video, R.id.rsb_music_time})
    public boolean viewOnTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.frame_sleep_music_video:
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
                        mHandler.sendEmptyMessage(2);
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

    public static SleepMusicVideoFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        SleepMusicVideoFragment fragment = new SleepMusicVideoFragment();
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private MusicEntity mEntity;
    private int mPosition;
    private CountDownTimer mTimer;
    private boolean isPlaying = true;
    private long mCurrentTime = -1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    linearMusic.setVisibility(View.GONE);
                    break;
                case 2:
                    float[] values = rsbMusicTime.getCurrentRange();
                    if (mCurrentTime == (long) values[0]) {
                        return;
                    }
                    mCurrentTime = (long) values[0];
                    if (mCurrentTime != -1) {
                        //设置定时时间
                        MusicActionManager.getInstance().setRemainTime(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, mCurrentTime);
                    }
                    break;
                default:
                    break;
            }
        }
    };

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
            mPosition = bundle.getInt(POSITION);
        }
        mTimer = new CountDownTimer(3 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                mHandler.sendEmptyMessage(1);
            }
        };
        mTimer.start();
        rsbMusicTime.setValue(30);
    }

    @Override
    protected void initData() {
        MusicActionManager.getInstance().start(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, mPosition, Constant.PLAY_IN_SINGLE_LOOP, 30);
        CacheEntity<MusicEntity> cache = CacheManager.getInstance().get(Constant.DOLPHIN_NATURAL_MUSIC_CACHE, MusicEntity.class);
        if (cache == null) {
            toastShow("暂无数据");
            return;
        }
        mEntity = cache.getData();
        presenter.initProxy(mPosition);
        presenter.startVideo();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        presenter.resumeVideo();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        presenter.pauseVideo();
        mHandler.removeMessages(1);
        mHandler.removeMessages(2);
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onDestroyView() {
        presenter.stopVideo();
        super.onDestroyView();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        MusicActionManager.getInstance().stop(Constant.DOLPHIN_NATURAL_MUSIC_CACHE);
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return true;
    }

    @Override
    public boolean isNetworkAvailable() {
        return isNetAvailable();
    }

    @Override
    public void showToast(String text) {
        toastShow(text);
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
    public VideoView getVideoView() {
        return video;
    }

    @Override
    public LinearLayout getLinear() {
        return linearMusic;
    }

    @Override
    public MusicEntity getEntity() {
        return mEntity;
    }
}
