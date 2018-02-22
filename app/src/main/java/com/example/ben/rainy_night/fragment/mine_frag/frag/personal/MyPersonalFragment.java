package com.example.ben.rainy_night.fragment.mine_frag.frag.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.event.OnActivityResultEvent;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.MyPersonalPresentImpl;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.MyPersonalPresenter;
import com.example.ben.rainy_night.fragment.mine_frag.view.IMyPersonalView;
import com.example.ben.rainy_night.util.SharedPreferencesUtil;
import com.vondear.rxtools.RxTimeTool;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

/**
 * @author Ben
 */
public class MyPersonalFragment extends BaseBackFragment<MyPersonalPresenter> implements IMyPersonalView {

    @BindView(R.id.base_toolbar)
    Toolbar mToolBar;
    @BindView(R.id.iv_person_head)
    CircleImageView ivPersonHead;
    @BindView(R.id.rela_person_head)
    RelativeLayout relaPersonHead;
    @BindView(R.id.tv_person_petName)
    TextView tvPersonPetName;
    @BindView(R.id.rela_person_petName)
    RelativeLayout relaPersonPetName;
    @BindView(R.id.tv_person_sex)
    TextView tvPersonSex;
    @BindView(R.id.rela_person_sex)
    RelativeLayout relaPersonSex;
    @BindView(R.id.tv_person_birth)
    TextView tvPersonBirth;
    @BindView(R.id.rela_person_birth)
    RelativeLayout relaPersonBirth;
    @BindView(R.id.tv_person_email)
    TextView tvPersonEmail;
    @BindView(R.id.rela_person_email)
    RelativeLayout relaPersonEmail;
    @BindView(R.id.btn_personal_save)
    Button btnPersonalSave;

    @OnClick({R.id.rela_person_head, R.id.rela_person_petName,
            R.id.rela_person_sex, R.id.rela_person_birth, R.id.rela_person_email, R.id.btn_personal_save})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.rela_person_head:
                selectHeadImage();
                break;
            case R.id.rela_person_petName:
                start(NickNameFragment.newInstance(nickname));
                break;
            case R.id.rela_person_sex:
                if (mPickerSex == null) {
                    selectSex();
                }
                mPickerSex.show();
                break;
            case R.id.rela_person_birth:
                if (mPickerDate == null) {
                    selectDate();
                }
                mPickerDate.show();
                break;
            case R.id.rela_person_email:
                start(EmailFragment.newInstance(email));
                break;
            case R.id.btn_personal_save:
                presenter.updateUser();
                break;
            default:
                break;
        }
    }

    private RxDialogChooseImage mChooseImage = null;
    private OptionsPickerView mPickerSex = null;
    private TimePickerView mPickerDate = null;
    private List<String> lists_sex = null;

    private String objectId = "";
    private String nickname = "";
    private String sex = "";
    private String birthday = "";
    private String email = "";

    private static final String REQUEST_PERSONAL = "personal";

    public static MyPersonalFragment newInstance() {
        Bundle args = new Bundle();
        MyPersonalFragment fragment = new MyPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    public void setPresenter() {
        presenter = new MyPersonalPresentImpl(this);
    }

    /**
     * 返回界面layout
     *
     * @return
     */
    @Override
    public int getLayout() {
        return R.layout.my_personal_fragment;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        mToolBar.setTitle(getString(R.string.personal_information));
        initToolbarNav(mToolBar);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    /**
     * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
     * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
     * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        GlideApp.with(_mActivity)
                .load(getSharedPreferences(SharedPreferencesUtil.USER_HEAD_IMAGE, ""))
                .error(R.mipmap.img_head)
                .into(ivPersonHead);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

        objectId = String.valueOf(getSharedPreferences(SharedPreferencesUtil.USER_OBJECT_ID, ""));
        nickname = String.valueOf(getSharedPreferences(SharedPreferencesUtil.USER_NICK_NAME, ""));
        sex = String.valueOf(getSharedPreferences(SharedPreferencesUtil.USER_SEX, ""));
        birthday = String.valueOf(getSharedPreferences(SharedPreferencesUtil.USER_BIRTHDAY, ""));
        email = String.valueOf(getSharedPreferences(SharedPreferencesUtil.USER_EMAIL, ""));
        if (TextUtils.equals(objectId, "")) {
            return;
        }
        tvPersonPetName.setText(nickname);
        tvPersonSex.setText(sex);
        tvPersonBirth.setText(birthday);
        tvPersonEmail.setText(email);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    /**
     * 获取从activity中onActivityResult()传回来的值
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getResult(OnActivityResultEvent event) {
        presenter.onActivityResult(event.getRequestCode(), event.getResultCode(), event.getData());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isUpdateUserSuccess(OnUserEvent event) {
        if (TextUtils.equals(event.getRequest(), REQUEST_PERSONAL)) {
            presenter.isUpdateUserSuccess(event.getResult());
        }
    }

    /**
     * 选择头像
     */
    private void selectHeadImage() {
        if (mChooseImage == null) {
            mChooseImage = new RxDialogChooseImage(_mActivity, TITLE);
        }
        mChooseImage.show();
    }

    /**
     * 选择日期
     */

    private void selectDate() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1980, 0, 1);
        Calendar endDate = Calendar.getInstance();
        mPickerDate = new TimePickerView.Builder(_mActivity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvPersonBirth.setText(RxTimeTool.simpleDateFormat("yyyy年MM月dd日", date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setOutSideCancelable(true)
                .setRangDate(startDate, endDate)
                .setDate(selectedDate)
                .setBackgroundId(0x00FFFFFF)
                .setDecorView(null)
                .isDialog(true)
                .build();
    }

    /**
     * 选择性别
     */
    private void selectSex() {
        lists_sex = new ArrayList<String>();
        lists_sex.add("男");
        lists_sex.add("女");
        lists_sex.add("保密");
        mPickerSex = new OptionsPickerView.Builder(_mActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvPersonSex.setText(lists_sex.get(options1));
            }
        })
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(21)
                .setTextColorCenter(Color.DKGRAY)
                .isCenterLabel(true)
                .setOutSideCancelable(true)
                .setDecorView(null)
                .isDialog(true)
                .setSelectOptions(0)
                .build();
        mPickerSex.setPicker(lists_sex);
    }


    /**
     * 获取Activity
     *
     * @return
     */
    @Override
    public FragmentActivity getFragAct() {
        return _mActivity;
    }

    /**
     * @return img控件
     */
    @Override
    public CircleImageView getHeadImg() {
        return ivPersonHead;
    }

    /**
     * @return username控件
     */
    @Override
    public TextView getTextUser() {
        return tvPersonPetName;
    }

    /**
     * @return sex控件
     */
    @Override
    public TextView getTextSex() {
        return tvPersonSex;
    }

    /**
     * @return birthday控件
     */
    @Override
    public TextView getTextBirthday() {
        return tvPersonBirth;
    }

    /**
     * @return email控件
     */
    @Override
    public TextView getTextEmail() {
        return tvPersonEmail;
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
