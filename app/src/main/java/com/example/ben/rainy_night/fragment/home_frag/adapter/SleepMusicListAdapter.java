package com.example.ben.rainy_night.fragment.home_frag.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.http.okgo.entity.MusicEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.NetWorkUtil;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.example.ben.rainy_night.util.ToastUtil;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import java.util.List;

/**
 * @author Ben
 * @date 2018/3/28
 */

public class SleepMusicListAdapter extends BaseQuickAdapter<MusicEntity.DataBean, BaseViewHolder> {

    private RxDialogSureCancel mDialog;

    public SleepMusicListAdapter(@Nullable List<MusicEntity.DataBean> data) {
        super(R.layout.item_sleep_music_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicEntity.DataBean item) {
        helper.setText(R.id.tv_item_sleep_music_list, item.getSceneName());
        GlideApp.with(mContext)
                .load(item.getCoverUrl())
                .placeholder(R.mipmap.ic_music_loading)
                .error(R.mipmap.ic_music_loading)
                .into((ImageView) helper.getView(R.id.iv_item_sleep_music_list));
        helper.getConvertView().setOnClickListener(v -> itemOnClick(helper, item));

    }

    /**
     * 音乐列表的点击事件
     */
    private void itemOnClick(BaseViewHolder helper, MusicEntity.DataBean bean) {
        /*
         *先判断文件是否下载，是直接跳转，否下载
         */
        List<Progress> mProgressList = DownloadManager.getInstance().getFinished();
        if (mProgressList != null) {
            List<DownloadTask> mListTask = OkDownload.restore(mProgressList);
            for (DownloadTask task : mListTask) {

            }
        }
        for (Progress info : mProgressList) {

        }


        if (NetWorkUtil.getInstance().getAPNType(mContext.getApplicationContext()) == 0) {
            ToastUtil.show(mContext, "当前网络不可用");
            return;
        }

        boolean isMobileAvailable = (boolean) SharedPreferencesUtil.getInstance(mContext.getApplicationContext())
                .getValue(Constant.CAN_MOBILE_DOWNLOAD, false);
        if (isMobileAvailable) {

        }

        if (NetWorkUtil.getInstance().getAPNType(mContext.getApplicationContext()) != 1) {
            if (mDialog == null) {
                mDialog = new RxDialogSureCancel(mContext);
                mDialog.getLogoView().setBackground(null);
                mDialog.getTitleView().setText("确定要用移动流量下载吗？");
            }
            mDialog.show();

            mDialog.setSureListener(v1 -> {
                SharedPreferencesUtil.getInstance(mContext.getApplicationContext())
                        .putValue(Constant.CAN_MOBILE_DOWNLOAD, true);

            });
            mDialog.setCancelListener(v12 -> mDialog.cancel());
        }

    }

    /**
     * 下载音频文件
     */
    private void downAudio(BaseQuickAdapter adapter, View v, int position) {

    }
}
