package com.example.ben.rainy_night.player.callback;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.util.Constant;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

/**
 * @author Ben
 * @date 2018/4/12
 */

public class MusicPlayerImpl implements MusicPlayerCallback, Player.EventListener {

    private Context mContext;
    /**
     * 音乐数据
     */
    private MusicEntity mEntity;
    /**
     * 当前播放位置
     */
    private int mCurrentMediaId = 0;
    /**
     * 循环模式
     */
    private String mCycle = Constant.SINGLE_CYCLE;
    /**
     * 定时时间 ，仅针对伴我睡有效
     * 对于其他不需要定时器的地方传: -1
     * 单位分钟
     */
    private int mTime = 30;
    /**
     * 是否是单曲循环
     */
    private boolean isLooping;

    /**
     * 播放器
     */
    private SimpleExoPlayer mExoPlayer;
    private DataSource.Factory mediaDataSourceFactory;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private String userAgent;

    private MusicPlayerImpl() {

    }

    public static MusicPlayerImpl getInstance() {
        return new MusicPlayerImpl();
    }

    @Override
    public void start(Context context, Object data, int position, String cycleMode, int time) {
        this.mContext = context.getApplicationContext();
        if (data instanceof MusicEntity) {
            this.mEntity = (MusicEntity) data;
        }
        this.mCurrentMediaId = position;
        this.mCycle = cycleMode;
        this.mTime = time;
        if (TextUtils.equals(Constant.SINGLE_CYCLE, cycleMode)) {
            isLooping = true;
        }

        if (mExoPlayer == null) {
            userAgent = Util.getUserAgent(mContext, "ExoPlayer");
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(mContext),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mExoPlayer.addListener(this);
        }

    }

    @Override
    public void pause() {
        if (mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer.removeListener(this);
            mExoPlayer = null;
        }
    }

    @Override
    public void startPrevious() {

    }

    @Override
    public void startNext() {

    }

    @Override
    public void seekTo(long position) {
        if (mExoPlayer != null) {
            mExoPlayer.seekTo(position);
        }
    }

    @Override
    public void setState(int state) {

    }

    @Override
    public int getState() {
        //STATE_IDLE      没有任何媒体播放。
        //STATE_BUFFERING 无法立即从当前位置进行播放
        //STATE_READY     可以从当前位置立即进行播放。 如果  {@link #getPlayWhenReady（）}为true，立即播放，否则暂停。
        //STATE_ENDED     已经完成播放媒体。
        int state = Constant.STATE_NONE;
        if (mExoPlayer == null) {
            state = Constant.STATE_NONE;
        } else {
            switch (mExoPlayer.getPlaybackState()) {
                case Player.STATE_IDLE:
                    state = Constant.STATE_IDLE;
                    break;
                case Player.STATE_BUFFERING:
                    state = Constant.STATE_BUFFERING;
                    break;
                case Player.STATE_READY:
                    state = mExoPlayer.getPlayWhenReady() ? Constant.STATE_PLAYING : Constant.STATE_PAUSED;
                    break;
                case Player.STATE_ENDED:
                    state = Constant.STATE_ENDED;
                    break;
                default:
                    state=Constant.STATE_NONE;
                    break;
            }
        }
        return state;
    }

    @Override
    public void setLooping(boolean looping) {

    }

    @Override
    public void setLoopList(Object object) {

    }

    @Override
    public boolean isPlaying() {
        return mExoPlayer != null && mExoPlayer.getPlayWhenReady();
    }

    @Override
    public long getCurrentStreamPosition() {
        return mExoPlayer != null ? mExoPlayer.getCurrentPosition() : 0;
    }

    @Override
    public void setmCurrentMediaId(int mediaId) {
        this.mCurrentMediaId = mediaId;
    }

    @Override
    public int getmCurrentMediaId() {
        return mCurrentMediaId;
    }

    @Override
    public int getDuration() {
        return mExoPlayer != null ? (int) mExoPlayer.getDuration() : 0;
    }

    @Override
    public void setRemainTime(int time) {

    }

    @Override
    public void setCallback(Callback callback) {

    }

    /**
     * 构建不同的MediaSource
     */
    private MediaSource buildMediaSource(
            Uri uri,
            String overrideExtension,
            @Nullable Handler handler,
            @Nullable MediaSourceEventListener listener) {
        @C.ContentType int type = getMediaType(overrideExtension, uri);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                        buildDataSourceFactory(false))
                        .createMediaSource(uri, handler, listener);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory),
                        buildDataSourceFactory(false))
                        .createMediaSource(uri, handler, listener);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(mediaDataSourceFactory)
                        .createMediaSource(uri, handler, listener);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                        .createMediaSource(uri, handler, listener);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    /**
     * 获取播放类型
     */
    private int getMediaType(String overrideExtension, Uri uri) {
        @C.ContentType int type = TextUtils.isEmpty(overrideExtension) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension);
        return type;
    }

    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    private DataSource.Factory buildDataSourceFactory(TransferListener<? super DataSource> listener) {
        return new DefaultDataSourceFactory(mContext, listener, buildHttpDataSourceFactory(listener));
    }

    private HttpDataSource.Factory buildHttpDataSourceFactory(TransferListener<? super DataSource> listener) {
        return new DefaultHttpDataSourceFactory(userAgent, listener);
    }

    /*
    Player.EventListener 接口回掉
     */

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
