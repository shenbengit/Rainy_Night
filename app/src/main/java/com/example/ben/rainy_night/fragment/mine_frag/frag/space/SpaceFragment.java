package com.example.ben.rainy_night.fragment.mine_frag.frag.space;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.ben.rainy_night.GlideApp;
import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.base.BaseFragment;
import com.example.ben.rainy_night.fragment.mine_frag.contract.SpaceContract;
import com.example.ben.rainy_night.fragment.mine_frag.frag.login_register.LoginFragment;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

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
//                if (mUserEntity == null) {
//                    start(LoginFragment.newInstance());
//                } else {
//                    start(PostStoryFragment.newInstance());
//                }
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

    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

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
}
