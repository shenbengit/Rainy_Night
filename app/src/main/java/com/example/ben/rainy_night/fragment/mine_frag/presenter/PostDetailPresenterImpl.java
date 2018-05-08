package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import com.example.ben.rainy_night.fragment.mine_frag.contract.PostDetailContract;

/**
 *
 * @author Ben
 * @date 2018/5/8
 */

public class PostDetailPresenterImpl implements PostDetailContract.Presenter {

    private PostDetailContract.View view;

    public PostDetailPresenterImpl(PostDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {

    }
}
