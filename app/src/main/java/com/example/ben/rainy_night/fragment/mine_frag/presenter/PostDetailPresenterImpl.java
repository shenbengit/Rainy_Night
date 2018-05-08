package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.example.ben.rainy_night.event.OnPostCommentEvent;
import com.example.ben.rainy_night.event.OnPostLikesEvent;
import com.example.ben.rainy_night.fragment.mine_frag.contract.PostDetailContract;
import com.example.ben.rainy_night.fragment.mine_frag.model.PostModel;
import com.example.ben.rainy_night.fragment.mine_frag.model.PostModelImpl;
import com.example.ben.rainy_night.util.Constant;

/**
 * @author Ben
 * @date 2018/5/8
 */

public class PostDetailPresenterImpl implements PostDetailContract.Presenter {

    private PostDetailContract.View view;
    private PostModel model;

    public PostDetailPresenterImpl(PostDetailContract.View view) {
        this.view = view;
        model = new PostModelImpl();
    }

    @Override
    public void init() {
        LinearLayoutManager manager = new LinearLayoutManager(view.getCon());

        view.getRecy().setNestedScrollingEnabled(false);
        view.getRecy().setLayoutManager(manager);
    }

    @Override
    public void getPostComment(OnPostCommentEvent event) {
        switch (event.getAction()) {
            case Constant.ACTION_ADD:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {

                } else {

                }
                break;
            case Constant.ACTION_REMOVE:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {

                } else {

                }
                break;
            case Constant.ACTION_QUERY:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {

                } else {

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getPostLikes(OnPostLikesEvent event) {
        switch (event.getAction()) {
            case Constant.ACTION_ADD:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {

                } else {

                }
                break;
            case Constant.ACTION_REMOVE:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {

                } else {

                }
                break;
            case Constant.ACTION_QUERY:
                if (TextUtils.equals(event.getResult(), Constant.OK)) {

                } else {

                }
                break;
            default:
                break;
        }
    }
}
