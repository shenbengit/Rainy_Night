package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import com.example.ben.rainy_night.fragment.mine_frag.contract.SettingContract;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.http.bmob.model.UserModel;
import com.example.ben.rainy_night.http.bmob.model.UserModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.manager.MusicActionManager;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.FileUtil;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;

import cn.bmob.v3.BmobUser;

/**
 * @author Ben
 * @date 2018/3/7
 */

public class SettingPresenterImpl implements SettingContract.Presenter {

    private SettingContract.View view;
    private UserModel model;
    private RxDialogSureCancel mDialog;
    /**
     * 缓存大小，单位:B
     */
    private long mCacheSize = 0;
    private FileUtil mUtil;

    public SettingPresenterImpl(SettingContract.View view) {
        this.view = view;
        model = new UserModelImpl();
        mUtil = new FileUtil();
    }

    @Override
    public void init() {
        mCacheSize = MusicActionManager.getInstance().getCachedFileSize() + mUtil.getFileSize(Constant.VIDEO_CACHE_PATH);
        LoggerUtil.e("音乐缓存大小："+MusicActionManager.getInstance().getCachedFileSize()+",视频缓存大小："+mUtil.getFileSize(Constant.VIDEO_CACHE_PATH));
        view.getTextCached().setText(Formatter.formatFileSize(view.getAct(),mCacheSize));
    }

    /**
     * 关于我们
     */
    @Override
    public void aboutUs() {
        view.showToast("功能暂未开放");
    }

    /**
     * 清除缓存
     */
    @Override
    public void clearCache() {
        if (mDialog == null) {
            mDialog = new RxDialogSureCancel(view.getAct());
            mDialog.setTitle("清除缓存");
            mDialog.setContent("是否清楚缓存");
            mDialog.getLogoView().setVisibility(View.GONE);
            mDialog.setSureListener(v -> {
                MusicActionManager.getInstance().clearCachedFile();
                mUtil.deleteFile(Constant.VIDEO_CACHE_PATH);
                mDialog.cancel();
                view.getTextCached().setText("0.00 B");
                LoggerUtil.e("音乐缓存大小："+MusicActionManager.getInstance().getCachedFileSize()+",视频缓存大小："+mUtil.getFileSize(Constant.VIDEO_CACHE_PATH));
            });
            mDialog.setCancelListener(v -> mDialog.cancel());
        }

        mDialog.show();

    }

    /**
     * 退出登录
     */
    @Override
    public void loginOut() {
        final RxDialogSureCancel dialog = new RxDialogSureCancel(view.getAct());
        dialog.getSureView().setOnClickListener(v -> {
            BmobUser.logOut();
            UserEntity bean = BmobUser.getCurrentUser(UserEntity.class);
            if (bean == null) {
                dialog.cancel();
                view.showToast("用户已退出登陆");
                view.clearSP();
                view.startWithPopToFragment(LoginFragment.newInstance());
            }
        });
        dialog.getCancelView().setOnClickListener(v -> dialog.cancel());

        dialog.show();
    }
}
