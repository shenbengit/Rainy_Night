package com.example.ben.rainy_night.fragment.mine_frag.frag.space;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.event.OnPostCommentEvent;
import com.example.ben.rainy_night.event.OnPostLikesEvent;
import com.example.ben.rainy_night.fragment.mine_frag.contract.PostDetailContract;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.PostDetailPresenterImpl;
import com.example.ben.rainy_night.http.bmob.entity.PostEntity;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.LoggerUtil;
import com.example.ben.rainy_night.widget.EnlargePictureDialog;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.vondear.rxtools.view.dialog.RxDialogSure;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/5/8
 */

public class PostDetailFragment extends BaseFragment<PostDetailContract.Presenter> implements PostDetailContract.View {

    private static final String ENTITY = "entity";
    @BindView(R.id.base_toolbar)
    Toolbar baseToolbar;
    @BindView(R.id.civ_post_detail_head)
    CircleImageView civPostDetailHead;
    @BindView(R.id.tv_post_detail_nick)
    TextView tvPostDetailNick;
    @BindView(R.id.tv_post_detail_time)
    TextView tvPostDetailTime;
    @BindView(R.id.tv_post_detail_content)
    TextView tvPostDetailContent;
    @BindView(R.id.nglv_post_detail_images)
    NineGridImageView<BmobFile> nglvPostDetailImages;
    @BindView(R.id.cb_post_detail_likes)
    CheckBox cbPostDetailLikes;
    @BindView(R.id.cb_post_detail_comment)
    CheckBox cbPostDetailComment;
    @BindView(R.id.tv_post_detail_comment_list)
    TextView tvPostDetailCommentList;
    @BindView(R.id.linear_no_comment)
    LinearLayout linearNoComment;
    @BindView(R.id.recy_post_detail)
    RecyclerView recyPostDetail;
    @BindView(R.id.nsv_post_detail)
    NestedScrollView nsvPostDetail;
    @BindView(R.id.srl_post_detail)
    SwipeRefreshLayout srlPostDetail;
    @BindView(R.id.et_post_detail_comment)
    EditText etPostDetailComment;

    @OnCheckedChanged({R.id.cb_post_detail_likes, R.id.cb_post_detail_comment})
    public void viewOnCheckChanged(CompoundButton button, boolean isChecked) {
        if (!button.isPressed()) {
            return;
        }
        switch (button.getId()) {
            case R.id.cb_post_detail_likes:
                if (mUserEntity == null) {
                    cbPostDetailLikes.setChecked(false);
                    showLoginDialog();
                    return;
                }
                presenter.setPostLikes(isChecked);
                break;
            case R.id.cb_post_detail_comment:
                if (mUserEntity == null) {
                    showLoginDialog();
                    return;
                }
                showSoftInput(etPostDetailComment);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.btn_post_detail_send})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_post_detail_send:
                if (mUserEntity == null) {
                    showLoginDialog();
                    return;
                }
                if (TextUtils.isEmpty(etPostDetailComment.getText().toString().trim())) {
                    showLoginDialog();
                    return;
                }
                presenter.addPostComment(etPostDetailComment.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private RxDialogSure mDialog;

    private NineGridImageViewAdapter<BmobFile> mAdapter = new NineGridImageViewAdapter<BmobFile>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, BmobFile s) {
            GlideApp.with(context)
                    .load(s.getFileUrl())
                    .placeholder(R.mipmap.ic_transparent_picture)
                    .error(R.mipmap.ic_transparent_picture)
                    .into(imageView);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<BmobFile> list) {
            super.onItemImageClick(context, imageView, index, list);
            EnlargePictureDialog mDialog = new EnlargePictureDialog(context);
            mDialog.setImageList(list, index, false);
            mDialog.show();
        }
    };

    public static PostDetailFragment newInstance(PostEntity entity) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    private PostEntity mEntity;
    private boolean isFirstIn = true;
    private boolean isLoginUser = true;
    private boolean isUserDataChanged = false;

    @Override
    protected int getLayout() {
        return R.layout.fragment_post_detail;
    }

    @Override
    protected void setPresenter() {
        presenter = new PostDetailPresenterImpl(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        srlPostDetail.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        baseToolbar.setTitle("详情");
        initToolbarNav(baseToolbar);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mEntity = (PostEntity) bundle.getSerializable(ENTITY);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mUserEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (mUserEntity == null) {
            isLoginUser = false;
        }
        if (!isLoginUser && mUserEntity != null) {
            isUserDataChanged = true;
        }
        presenter.getCurrentUser(mUserEntity);
        if (!isFirstIn) {
            loadPostDetail();
            presenter.loadCommentData();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        loadPostDetail();
        isFirstIn = false;
        assert mEntity != null;
        presenter.init(mEntity.getObjectId());
        presenter.loadCommentData();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    /**
     * 帖子详情
     */
    private void loadPostDetail() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String mDate = format.format(date);
        if (mEntity != null) {
            GlideApp.with(_mActivity)
                    .load(mEntity.getUser().getHeadimg().getFileUrl())
                    .placeholder(R.mipmap.ic_head)
                    .error(R.mipmap.ic_head)
                    .into(civPostDetailHead);
            if (mUserEntity != null && TextUtils.equals(mUserEntity.getObjectId(), mEntity.getUser().getObjectId())) {
                tvPostDetailNick.setText(R.string.mine);
            } else {
                tvPostDetailNick.setText(mEntity.getUser().getNickName());
            }
            if (TextUtils.equals(mDate, mEntity.getCreatedAt().substring(0, 10))) {
                tvPostDetailTime.setText(getString(R.string.today) + "\t" + mEntity.getCreatedAt().substring(11, 16));
            } else {
                tvPostDetailTime.setText(mEntity.getCreatedAt().substring(5, 16));
            }
            tvPostDetailContent.setText(mEntity.getContent());
            nglvPostDetailImages.setAdapter(mAdapter);
            nglvPostDetailImages.setImagesData(mEntity.getPictures());
        }
    }

    private void showLoginDialog() {
        if (mDialog == null) {
            mDialog = new RxDialogSure(_mActivity);
            mDialog.setTitle("提示");
            mDialog.setContent("请先登录");
            mDialog.setSureListener(v -> {
                start(LoginFragment.newInstance());
                mDialog.cancel();
            });
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getPostComment(OnPostCommentEvent event) {
        presenter.getPostComment(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getPostLikes(OnPostLikesEvent event) {
        presenter.getPostLikes(event);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (isUserDataChanged) {
            Bundle bundle = new Bundle();
            bundle.putString("用户登录", "用户登录");
            setFragmentResult(RESULT_OK, bundle);
        }

        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();

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
    public SwipeRefreshLayout getSwipeRefresh() {
        return srlPostDetail;
    }

    @Override
    public RecyclerView getRecy() {
        return recyPostDetail;
    }

    @Override
    public CheckBox getCheckLikes() {
        return cbPostDetailLikes;
    }

    @Override
    public CheckBox getCheckComment() {
        return cbPostDetailComment;
    }

    @Override
    public TextView getTextCommentList() {
        return tvPostDetailCommentList;
    }

    @Override
    public LinearLayout getLinearNoComment() {
        return linearNoComment;
    }

    @Override
    public EditText getPostComment() {
        return etPostDetailComment;
    }

    @Override
    public void startBrotherFragment(ISupportFragment fragment) {
        start(fragment);
    }
}
