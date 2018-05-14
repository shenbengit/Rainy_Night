package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.example.ben.rainy_night.R;
import com.example.ben.rainy_night.event.OnPostCommentEvent;
import com.example.ben.rainy_night.event.OnPostLikesEvent;
import com.example.ben.rainy_night.fragment.mine_frag.adapter.PostCommentAdapter;
import com.example.ben.rainy_night.fragment.mine_frag.contract.PostDetailContract;
import com.example.ben.rainy_night.http.bmob.model.PostModel;
import com.example.ben.rainy_night.http.bmob.model.PostModelImpl;
import com.example.ben.rainy_night.http.bmob.entity.CommentEntity;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben
 * @date 2018/5/8
 */

public class PostDetailPresenterImpl implements PostDetailContract.Presenter, SwipeRefreshLayout.OnRefreshListener {

    private PostDetailContract.View view;
    private PostModel model;
    private PostCommentAdapter mAdapter;
    private List<CommentEntity> mList;
    private String mObjectId;
    private UserEntity mUserEntity;
    private String mComment;

    public PostDetailPresenterImpl(PostDetailContract.View view) {
        this.view = view;
        model = new PostModelImpl();
    }

    @Override
    public void init(String objectId) {
        mObjectId = objectId;
        mList = new ArrayList<>();
        mAdapter = new PostCommentAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(view.getCon());
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        view.getRecy().setLayoutManager(manager);
        view.getRecy().setHasFixedSize(true);
        view.getRecy().setNestedScrollingEnabled(false);
        view.getRecy().setAdapter(mAdapter);
        view.getSwipeRefresh().setOnRefreshListener(this);
    }


    @Override
    public void loadCommentData() {
        if (!TextUtils.isEmpty(isCanQueryData())) {
            view.showToast(isCanQueryData());
            return;
        }
        if (!mAdapter.isLoginUser()) {
            mAdapter.setCurrentUser();
        }
        onRefresh();
    }

    /**
     * 获取当前登陆用户信息
     *
     * @param entity 用户信息
     */
    @Override
    public void getCurrentUser(UserEntity entity) {
        mUserEntity = entity;
    }

    @Override
    public void getPostComment(OnPostCommentEvent event) {
        setRefreshing(false);
        switch (event.getAction()) {
            case Constant.ACTION_ADD:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {
                    view.getPostComment().setText(null);
                    model.queryPostComment(mObjectId);
                } else {
                    view.showToast("添加评论失败");
                }
                break;
            case Constant.ACTION_REMOVE:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {

                } else {
                    view.showToast("删除评论失败");
                }
                break;
            case Constant.ACTION_QUERY:
                mList.clear();
                if (TextUtils.equals(event.getResult(), Constant.OK)) {
                    if (event.getCommentList().isEmpty() || event.getCommentList() == null) {
                        view.getTextCommentList().setText(view.getCon().getString(R.string.excellent_comment) + "(0)");
                        isShowNoCommentLayout(true);
                    } else {
                        isShowNoCommentLayout(false);
                        mAdapter.removeAllHeaderView();
                        mList.addAll(event.getCommentList());
                        view.getCheckComment().setText(event.getCommentList().size() + "");
                        view.getTextCommentList().setText(view.getCon().getString(R.string.excellent_comment)
                                + "(" + event.getCommentList().size() + ")");
                        mAdapter.setNewData(mList);
                    }
                } else {
                    view.getTextCommentList().setText(view.getCon().getString(R.string.excellent_comment));
                    view.showToast("查询评论失败");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getPostLikes(OnPostLikesEvent event) {
        setRefreshing(false);
        LoggerUtil.e("点赞操作： " + event.getAction());
        switch (event.getAction()) {
            case Constant.ACTION_ADD:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {
                    model.queryPostLikes(mObjectId);
                } else {
                    view.getCheckLikes().setChecked(false);
                    view.showToast("点赞失败");
                }
                break;
            case Constant.ACTION_REMOVE:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {
                    model.queryPostLikes(mObjectId);
                } else {
                    view.getCheckLikes().setChecked(true);
                    view.showToast("取消点赞失败");
                }
                break;
            case Constant.ACTION_QUERY:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {
                    //无人点赞
                    if (event.getList().isEmpty() || event.getList() == null) {
                        view.getCheckLikes().setText(0 + "");
                        return;
                    }
                    view.getCheckLikes().setText(event.getList().size() + "");
                    //有人点赞
                    for (UserEntity entity : event.getList()) {
                        //点赞人包含当前登陆用户
                        if (mUserEntity != null && TextUtils.equals(mUserEntity.getObjectId(), entity.getObjectId())) {
                            view.getCheckLikes().setChecked(true);
                            break;
                        }
                    }
                } else {
                    view.showToast("查询点赞人数失败");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void addPostComment(String comment) {
        if (!TextUtils.isEmpty(isCanQueryData())) {
            view.showToast(isCanQueryData());
            return;
        }
        mComment = comment;
        model.addPostComment(mObjectId, comment);
    }

    @Override
    public void setPostLikes(boolean isLikes) {
        LoggerUtil.e("setPostLikes: " + isLikes);
        if (!TextUtils.isEmpty(isCanQueryData())) {
            view.showToast(isCanQueryData());
            return;
        }
        if (isLikes) {
            model.addPostLikes(mObjectId);
        } else {
            model.removePostLikes(mObjectId);
        }
    }

    @Override
    public void onRefresh() {
        if (!TextUtils.isEmpty(isCanQueryData())) {
            view.showToast(isCanQueryData());
            setRefreshing(false);
            return;
        }

        model.queryPostLikes(mObjectId);
        model.queryPostComment(mObjectId);
    }

    /**
     * 设置刷新控件是否正在刷新
     *
     * @param refreshing
     */
    private void setRefreshing(boolean refreshing) {
        view.getSwipeRefresh().post(() -> view.getSwipeRefresh().setRefreshing(refreshing));
    }

    /**
     * 是否可以进行数据请求
     *
     * @return
     */
    private String isCanQueryData() {
        if (!view.isNetworkAvailable()) {
            return "当前网络不可用";
        }
        if (TextUtils.isEmpty(mObjectId)) {
            return "帖子的objectId为null,请稍后重试!";
        }
        return null;
    }

    /**
     * 是否显示无评论布局
     *
     * @param isShow
     */
    private void isShowNoCommentLayout(boolean isShow) {
        if (isShow) {
            view.getLinearNoComment().setVisibility(View.VISIBLE);
            view.getRecy().setVisibility(View.GONE);
        } else {
            view.getLinearNoComment().setVisibility(View.GONE);
            view.getRecy().setVisibility(View.VISIBLE);
        }
    }
}
