package com.example.ben.rainy_night.fragment.mine_frag.frag.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.event.OnActivityResultEvent;
import com.example.ben.rainy_night.fragment.event.OnUserEvent;
import com.example.ben.rainy_night.fragment.mine_frag.contract.MyPersonalContract;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.MyPersonalPresentImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.DialogLoadingUtil;
import com.vondear.rxtools.RxTimeTool;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

/**
 * @author Ben
 */
public class MyPersonalFragment extends BaseFragment<MyPersonalContract.Presenter> implements MyPersonalContract.View {

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

    private String nickname = "";
    private String email = "";

    public static MyPersonalFragment newInstance() {
        return new MyPersonalFragment();
    }

    private DialogLoadingUtil mDialog;

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
        return R.layout.fragment_my_personal;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        mToolBar.setTitle(getString(R.string.personal_information));
        initToolbarNav(mToolBar);

        mDialog = new DialogLoadingUtil(_mActivity);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

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

    /**
     * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
     * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
     * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (mUserEntity != null) {
            if (mUserEntity.getHeadimg() != null) {
                GlideApp.with(_mActivity)
                        .load(mUserEntity.getHeadimg().getFileUrl())
                        .placeholder(R.mipmap.img_head)
                        .error(R.mipmap.img_head)
                        .into(ivPersonHead);
            } else {
                ivPersonHead.setImageResource(R.mipmap.img_head);
            }
        }

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mUserEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (mUserEntity == null) {
            return;
        }
        nickname = mUserEntity.getNickName();
        email = mUserEntity.getEmail();
        tvPersonPetName.setText(mUserEntity.getNickName());
        tvPersonSex.setText(mUserEntity.getSex());
        tvPersonBirth.setText(mUserEntity.getBirthday());
        tvPersonEmail.setText(mUserEntity.getEmail());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mDialog.cancel();
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
        if (TextUtils.equals(event.getRequest(), Constant.REQUEST_PERSONAL)) {
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
        mPickerDate = new TimePickerView.Builder(_mActivity, (date, v) -> tvPersonBirth.setText(RxTimeTool.simpleDateFormat("yyyy年MM月dd日", date))).setType(new boolean[]{true, true, true, false, false, false})
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
        lists_sex = new ArrayList<>();
        lists_sex.add("男");
        lists_sex.add("女");
        lists_sex.add("保密");
        mPickerSex = new OptionsPickerView.Builder(_mActivity, (options1, options2, options3, v) -> tvPersonSex.setText(lists_sex.get(options1)))
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
     * 当前网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    @Override
    public boolean isNetworkAvailable() {
        return isNetAvailable();
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
        mDialog.show();
    }

    /**
     * 关闭网络加载Dialog
     */
    @Override
    public void cancelDialog() {
        mDialog.cancel();
    }
}
