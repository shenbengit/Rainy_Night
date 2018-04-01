package com.example.ben.rainy_night.fragment.home_frag.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.fragment.home_frag.listener.ListDownloadListener;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.NetWorkUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ben
 * @date 2018/3/28
 */

public class SleepMusicListAdapter extends BaseQuickAdapter<MusicEntity.DataBean, SleepMusicListAdapter.ViewHolder> {

    private RxDialogSureCancel mDialog;

    private List<Progress> mProgressList;

    public SleepMusicListAdapter(@Nullable List<MusicEntity.DataBean> data) {
        super(R.layout.item_sleep_music_list, data);
        mProgressList = new ArrayList<>();
    }

    @Override
    protected void convert(SleepMusicListAdapter.ViewHolder holder, MusicEntity.DataBean item) {
        holder.tvName.setText(item.getSceneName());
        GlideApp.with(mContext)
                .load(item.getCoverUrl())
                .placeholder(R.mipmap.ic_music_loading)
                .error(R.mipmap.ic_music_loading)
                .into(holder.ivCover);
        if (item.getSceneType() == Constant.HAITUN_NATURAL_MUSIC) {
            holder.pbProgress.setVisibility(View.VISIBLE);
        } else if (item.getSceneType() == Constant.HAITUN_LIGHT_MUSIC) {
            holder.pbProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (OkDownload.getInstance().getTask(createTag(mData.get(position).getVideoUrl())) == null) {
            holder.pbProgress.setProgress(0);
        }
        /*
         *先判断文件是否下载，是直接跳转，否下载
         */
        final boolean[] isExisted = {false};
        mProgressList.clear();
        mProgressList.addAll(DownloadManager.getInstance().getFinished());

        holder.view.setOnClickListener(v -> {
            if (mProgressList != null) {
                for (Progress progress : mProgressList) {
                    if (TextUtils.equals(progress.url, mData.get(position).getVideoUrl())) {
                        holder.pbProgress.setProgress(10000);
                        isExisted[0] = true;
                    }
                }
                if (isExisted[0]) {
                    ToastUtil.show(mContext, mData.get(position).getSceneName());
                    return;
                }
            }
            itemOnClick(holder, mData.get(position));

        });
    }

    /**
     * item 点击事件
     */
    private void itemOnClick(ViewHolder holder, MusicEntity.DataBean bean) {
        if (NetWorkUtil.getInstance().getAPNType(mContext.getApplicationContext()) == 0) {
            ToastUtil.show(mContext, "当前网络不可用");
            return;
        }
        if (NetWorkUtil.getInstance().getAPNType(mContext.getApplicationContext()) != 1) {
            boolean isMobileAvailable = (boolean) SharedPreferencesUtil
                    .getInstance(mContext.getApplicationContext()).getValue(Constant.CAN_MOBILE_DOWNLOAD, false);
            if (isMobileAvailable) {
                downloadVideo(holder, bean);
                return;
            }

            if (mDialog == null) {
                mDialog = new RxDialogSureCancel(mContext);
                mDialog.getLogoView().setBackground(null);
                mDialog.getTitleView().setText(null);
                mDialog.getContentView().setText("确定要用移动流量下载吗？");
            }
            mDialog.show();
            mDialog.setSureListener(v1 -> {
                SharedPreferencesUtil.getInstance(mContext.getApplicationContext())
                        .putValue(Constant.CAN_MOBILE_DOWNLOAD, true);
                downloadVideo(holder, bean);
                mDialog.cancel();
            });
            mDialog.setCancelListener(v12 -> mDialog.cancel());
        } else {
            downloadVideo(holder, bean);
        }
    }

    /**
     * 下载视频文件
     */
    private void downloadVideo(ViewHolder holder, MusicEntity.DataBean bean) {
        GetRequest<File> request = OkGo.<File>get(bean.getVideoUrl())
                .headers("aaa", "111")
                .params("bbb", "222");
        holder.setTag(createTag(bean.getVideoUrl()));
        OkDownload.request(createTag(bean.getVideoUrl()), request)
                .extra1(bean)
                .priority(bean.getPriority())
                .save()
                .register(new ListDownloadListener(createTag(bean.getVideoUrl()), holder))
                .start();
    }

    /**
     * 创建tag
     *
     * @param tag
     * @return
     */
    private String createTag(String tag) {
        return "natural_music_" + tag;
    }

    /**
     * 取消网络请求
     */
    public void unRegister() {
        Map<String, DownloadTask> taskMap = OkDownload.getInstance().getTaskMap();
        for (DownloadTask task : taskMap.values()) {
            task.unRegister(task.progress.tag);
        }
    }

    /**
     * 自定义ViewHolder 继承 BaseViewHolder
     */
    public class ViewHolder extends BaseViewHolder {

        private View view;
        private TextView tvName;
        private ImageView ivCover;
        public ProgressBar pbProgress;

        private DownloadTask task;
        private String tag;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            tvName = view.findViewById(R.id.tv_item_sleep_music_list);
            ivCover = view.findViewById(R.id.iv_item_sleep_music_list);
            pbProgress = view.findViewById(R.id.pb_item_sleep_music_list);
        }

        public DownloadTask getTask() {
            return task;
        }

        public void setTask(DownloadTask task) {
            this.task = task;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
