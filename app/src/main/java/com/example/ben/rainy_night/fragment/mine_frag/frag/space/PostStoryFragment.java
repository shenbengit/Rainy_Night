package com.example.ben.rainy_night.fragment.mine_frag.frag.space;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseBackFragment;
import com.example.ben.rainy_night.fragment.event.OnActivityResultEvent;
import com.example.ben.rainy_night.fragment.event.OnPostEvent;
import com.example.ben.rainy_night.fragment.mine_frag.contract.PostStoryContract;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.PostStoryPresenterImpl;
import com.example.ben.rainy_night.util.DialogLoadingUtil;
import com.example.ben.rainy_night.widget.PostStoryGridView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Ben
 * @date 2018/2/22
 */

public class PostStoryFragment extends BaseBackFragment<PostStoryContract.Presenter> implements PostStoryContract.View {

    @BindView(R.id.tv_publish_post)
    TextView tvPublishPost;
    @BindView(R.id.post_toolbar)
    Toolbar postToolbar;
    @BindView(R.id.et_post_story)
    EditText etPostStory;
    @BindView(R.id.gv_add_picture)
    PostStoryGridView gvAddPicture;

    @OnClick({R.id.tv_publish_post})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_publish_post:
                if (TextUtils.isEmpty(etPostStory.getText().toString().trim())) {
                    toastShow("请输入内容");
                    return;
                }
                presenter.publishPost();
                break;
            default:
                break;
        }
    }

    public static PostStoryFragment newInstance() {
        return new PostStoryFragment();
    }

    private DialogLoadingUtil mDialog;

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.post_story_fragment;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {
        presenter = new PostStoryPresenterImpl(this);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        mDialog = new DialogLoadingUtil(_mActivity);

        postToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        postToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());

        etPostStory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    if (tvPublishPost.isEnabled()) {
                        tvPublishPost.setEnabled(false);
                    }
                    tvPublishPost.setTextColor(_mActivity.getResources().getColor(R.color.colorBackGrey));
                } else {
                    if (!tvPublishPost.isEnabled()) {
                        tvPublishPost.setEnabled(true);
                    }
                    tvPublishPost.setTextColor(_mActivity.getResources().getColor(R.color.colorWhite));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

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

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        presenter.initGridView();
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

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void publishPostIsSuccess(OnPostEvent event) {
        presenter.publishPostIsSuccess(event.getResult());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mDialog.cancel();
        hideSoftInput();
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
     * @return GridView
     */
    @Override
    public GridView getGridView() {
        return gvAddPicture;
    }

    /**
     * @return EditText
     */
    @Override
    public EditText getEditText() {
        return etPostStory;
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
