package com.example.ben.rainy_night.fragment.mine_frag.frag.space;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.event.OnPostEvent;
import com.example.ben.rainy_night.fragment.mine_frag.contract.SpaceContract;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.fragment.mine_frag.presenter.SpacePresenterImpl;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author Ben
 * @date 2018/2/9
 */

public class SpaceFragment extends BaseFragment<SpaceContract.Presenter> implements SpaceContract.View {

    @BindView(R.id.civ_space_head)
    CircleImageView civSpaceHead;
    @BindView(R.id.rv_space)
    RecyclerView rvSpace;
    @BindView(R.id.srl_space)
    SwipeRefreshLayout srlSpace;
    @BindView(R.id.fab_space)
    FloatingActionButton fabSpace;

    @OnClick({R.id.ib_space_back, R.id.civ_space_head, R.id.fab_space})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_space_back:
                _mActivity.onBackPressed();
                break;
            case R.id.civ_space_head:
                if (mUserEntity == null) {
                    start(LoginFragment.newInstance());
                } else {
                    toastShow("个人空间暂未开放");
                }
                break;
            case R.id.fab_space:
                if (mUserEntity == null) {
                    start(LoginFragment.newInstance());
                } else {
                    start(PostStoryFragment.newInstance());
                }
                break;
            default:
                break;
        }
    }

    public static SpaceFragment newInstance() {
        return new SpaceFragment();
    }

    /**
     * @return 返回界面layout
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_space;
    }

    /**
     * 设置presenter
     */
    @Override
    protected void setPresenter() {
        presenter = new SpacePresenterImpl(this);
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        srlSpace.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        presenter.init();
    }

    @Override
    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mUserEntity = BmobUser.getCurrentUser(UserEntity.class);
        if (mUserEntity != null) {
            if (mUserEntity.getHeadimg() != null) {
                GlideApp.with(_mActivity)
                        .load(mUserEntity.getHeadimg().getFileUrl())
                        .placeholder(R.mipmap.ic_head)
                        .error(R.mipmap.ic_head)
                        .into(civSpaceHead);
            } else {
                civSpaceHead.setImageResource(R.mipmap.ic_head);
            }
        } else {
            civSpaceHead.setImageResource(R.mipmap.ic_head);
        }

        presenter.loadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getPostData(OnPostEvent event) {
        presenter.getPostData(event);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
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
        return srlSpace;
    }

    @Override
    public RecyclerView getRecycler() {
        return rvSpace;
    }

    @Override
    public void startBrotherFragment(ISupportFragment fragment) {
        start(fragment);
    }

}
