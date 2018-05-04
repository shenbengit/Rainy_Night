package com.example.ben.rainy_night.fragment.mine_frag.frag.setting;

import android.content.Context;
import android.view.View;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.event.OnActivityResultEvent;
import com.example.ben.rainy_night.fragment.mine_frag.contract.ScanerCodeContract;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.ScanerCodePresenterImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * @author Ben
 * @date 2018/5/3
 */

public class ScanerCodeFragment extends BaseFragment<ScanerCodeContract.Presenter> implements ScanerCodeContract.View {

    @BindView(R.id.zxingview)
    QRCodeView zxingview;

    @OnClick({R.id.iv_scaner_code_back, R.id.iv_scaner_code_light, R.id.iv_scaner_code_picture})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scaner_code_back:
                _mActivity.onBackPressed();
                break;
            case R.id.iv_scaner_code_light:
                if (isLisht) {
                    zxingview.closeFlashlight();
                    isLisht = false;
                } else {
                    zxingview.openFlashlight();
                    isLisht = true;
                }
                break;
            case R.id.iv_scaner_code_picture:
                presenter.goToAlbum();
                break;
            default:
                break;
        }
    }

    public static ScanerCodeFragment newInstance() {
        return new ScanerCodeFragment();
    }

    private boolean isLisht = false;

    @Override
    protected int getLayout() {
        return R.layout.fragment_scaner_code;
    }

    @Override
    protected void setPresenter() {
        presenter = new ScanerCodePresenterImpl(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        presenter.init();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.startScan();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onResult(OnActivityResultEvent event) {
        presenter.onActivityResult(event.getRequestCode(), event.getResultCode(), event.getData());
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
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
    public QRCodeView getQrCodeView() {
        return zxingview;
    }
}
