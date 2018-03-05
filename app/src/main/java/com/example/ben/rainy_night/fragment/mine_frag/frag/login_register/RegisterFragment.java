package com.example.ben.rainy_night.fragment.mine_frag.frag.login_register;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.viewlib.PowerfulEditText;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.RegisterPresenter;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.RegisterPresenterImpl;
import com.example.ben.rainy_night.fragment.mine_frag.view.IRegisterView;
import com.example.ben.rainy_night.util.ConstantUtil;
import com.vondear.rxtools.view.RxCaptcha;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vondear.rxtools.view.RxCaptcha.TYPE.NUMBER;

/**
 * @author Ben
 */
public class RegisterFragment extends BaseBackFragment<RegisterPresenter> implements IRegisterView {

    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.pet_register_phone)
    PowerfulEditText petRegisterPhone;
    @BindView(R.id.pet_register_name)
    PowerfulEditText petRegisterName;
    @BindView(R.id.pet_register_password)
    PowerfulEditText petRegisterPassword;
    @BindView(R.id.pet_register_pictureCode)
    PowerfulEditText petRegisterPictureCode;
    @BindView(R.id.iv_pictureCode)
    ImageView ivPictureCode;
    @BindView(R.id.pet_register_phoneCode)
    PowerfulEditText petRegisterPhoneCode;
    @BindView(R.id.tv_phoneCode)
    TextView tvPhoneCode;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @OnClick({R.id.iv_pictureCode, R.id.tv_phoneCode, R.id.btn_register})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pictureCode:
                getPictureCode();
                break;
            case R.id.tv_phoneCode:
                if (TextUtils.isEmpty(petRegisterPhone.getText().toString().trim()) ||
                        TextUtils.isEmpty(petRegisterName.getText().toString().trim()) ||
                        TextUtils.isEmpty(petRegisterPassword.getText().toString().trim()) ||
                        TextUtils.isEmpty(pictureCode)) {
                    toastShow("以上关键字不能为空!");
                    return;
                }
                presenter.sendPhoneCode();
                break;
            case R.id.btn_register:
                if (TextUtils.isEmpty(petRegisterPhone.getText().toString().trim()) ||
                        TextUtils.isEmpty(petRegisterName.getText().toString().trim()) ||
                        TextUtils.isEmpty(petRegisterPassword.getText().toString().trim()) ||
                        TextUtils.isEmpty(pictureCode) ||
                        TextUtils.isEmpty(petRegisterPhoneCode.getText().toString().trim())) {
                    toastShow("以上关键字不能为空!");
                    return;
                }
                presenter.registerUser();
                break;
            default:
                break;
        }
    }

    private static final int PHONE_LENGTH = 11;
    private String pictureCode = "";

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    public void setPresenter() {
        presenter = new RegisterPresenterImpl(this);
    }

    /**
     * 返回界面layout
     *
     * @return
     */
    @Override
    public int getLayout() {
        return R.layout.register_fragment;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        baseToolbar.setTitle(R.string.register);
        initToolbarNav(baseToolbar);
        getPictureCode();
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    /**
     * 获取图片验证码
     */
    private void getPictureCode() {
        RxCaptcha.build()
                .backColor(0xffffff)
                .codeLength(4)
                .fontSize(60)
                .lineNumber(0)
                .size(210, 100)
                .type(NUMBER)
                .into(ivPictureCode);
        pictureCode = RxCaptcha.build().getCode();
    }

    /**
     * 是否透明化状态栏
     *
     * @return
     */
    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void isRegisterSuccess(OnUserEvent event) {
        if (TextUtils.equals(event.getRequest(), ConstantUtil.REQUEST_REGISTER)) {
            presenter.isRegisterSuccess(event.getResult(), event.getBean());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        hideSoftInput();
        presenter.cancel();
    }

    /**
     * @return 手机号
     */
    @Override
    public PowerfulEditText getEditPhone() {
        return petRegisterPhone;
    }

    /**
     * @return 用户名
     */
    @Override
    public PowerfulEditText getEditName() {
        return petRegisterName;
    }

    /**
     * @return 密码
     */
    @Override
    public PowerfulEditText getEditPassWord() {
        return petRegisterPassword;
    }

    /**
     * @return 图片验证码
     */
    @Override
    public PowerfulEditText getEditPictureCode() {
        return petRegisterPictureCode;
    }

    /**
     * @return 手机验证码
     */
    @Override
    public PowerfulEditText getEditPhoneCode() {
        return petRegisterPhoneCode;
    }

    /**
     * 发送手机验证码
     *
     * @return
     */
    @Override
    public TextView getTextPhoneCode() {
        return tvPhoneCode;
    }

    /**
     * 图片验证码
     *
     * @return
     */
    @Override
    public String getStringPictureCode() {
        return pictureCode;
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    @Override
    public void showToast(String text) {
        toastShow(text);
    }

    /**
     * 显示网络加载Dialog
     */
    @Override
    public void showDialog() {
        dialogShow();
    }

    /**
     * @return 网络加载Dialog是否正在显示
     */
    @Override
    public boolean dialogIsShowing() {
        return dialogIsShow();
    }

    /**
     * 关闭网络加载Dialog
     */
    @Override
    public void cancelDialog() {
        dialogCancel();
    }

    /**
     * 使用SharedPreferences存储信息
     *
     * @param keyName 键
     * @param value   值
     */
    @Override
    public void putSpValue(String keyName, Object value) {
        putSharedPreferences(keyName, value);
    }

    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     *
     * @param keyName      键
     * @param defaultValue 默认值
     * @return
     */
    @Override
    public Object getSpValue(String keyName, Object defaultValue) {
        return getSharedPreferences(keyName, defaultValue);
    }
}
