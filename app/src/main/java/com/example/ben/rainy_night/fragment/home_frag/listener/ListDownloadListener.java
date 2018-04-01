package com.example.ben.rainy_night.fragment.home_frag.listener;

import android.text.TextUtils;

import com.example.ben.rainy_night.fragment.home_frag.adapter.SleepMusicListAdapter.ViewHolder;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.download.DownloadListener;

import java.io.File;

/**
 * @author Ben
 * @date 2018/4/1
 */

public class ListDownloadListener extends DownloadListener {

    private ViewHolder holder;

    public ListDownloadListener(Object tag, ViewHolder holder) {
        super(tag);
        this.holder = holder;
    }

    /**
     * 成功添加任务的回调
     *
     * @param progress
     */
    @Override
    public void onStart(Progress progress) {

    }

    /**
     * 下载进行时回调
     *
     * @param progress
     */
    @Override
    public void onProgress(Progress progress) {
        if (TextUtils.equals((String) tag, holder.getTag())) {
            holder.pbProgress.setProgress((int) (progress.fraction * 10000));
        }
    }

    /**
     * 下载出错时回调
     *
     * @param progress
     */
    @Override
    public void onError(Progress progress) {
        Throwable throwable = progress.exception;
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }

    /**
     * 下载完成时回调
     *
     * @param file
     * @param progress
     */
    @Override
    public void onFinish(File file, Progress progress) {
        LoggerUtil.e("海豚自然音符下载: " + progress.fileName);
    }

    /**
     * 被移除时回调
     *
     * @param progress
     */
    @Override
    public void onRemove(Progress progress) {

    }
}
