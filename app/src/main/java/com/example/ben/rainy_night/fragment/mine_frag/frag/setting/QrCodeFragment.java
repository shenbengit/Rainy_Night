package com.example.ben.rainy_night.fragment.mine_frag.frag.setting;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.vondear.rxtools.view.RxQRCode;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Ben
 * @date 2018/5/2
 */

public class QrCodeFragment extends BaseFragment {

    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.iv_qr_head)
    CircleImageView ivQrHead;
    @BindView(R.id.tv_qr_nick_name)
    TextView tvQrNickName;
    @BindView(R.id.iv_qr_sex)
    ImageView ivQrSex;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;

    public static QrCodeFragment newInstance() {
        return new QrCodeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_qr_code;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected void initView() {
        baseToolbar.setTitle(getString(R.string.qr_business_card));
        initToolbarNav(baseToolbar);
    }

    @Override
    protected void initData() {
        if (mUserEntity != null) {
            if (mUserEntity.getHeadimg() != null) {
                GlideApp.with(_mActivity)
                        .load(mUserEntity.getHeadimg().getFileUrl())
                        .placeholder(R.mipmap.ic_head)
                        .error(R.mipmap.ic_head)
                        .into(ivQrHead);
            } else {
                ivQrHead.setImageResource(R.mipmap.ic_head);
            }
            tvQrNickName.setText(mUserEntity.getNickName());
            if (TextUtils.equals(getString(R.string.sex_boy), mUserEntity.getSex())) {
                ivQrSex.setBackgroundResource(R.mipmap.ic_boy);
            } else {
                ivQrSex.setBackgroundResource(R.mipmap.ic_girl);
            }
            RxQRCode.builder(mUserEntity.getMobilePhoneNumber()).
                    backColor(getResources().getColor(R.color.colorWhite)).
                    codeColor(getResources().getColor(R.color.colorBlack)).
                    codeSide(600).
                    into(ivQrCode);
        }
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }
}
