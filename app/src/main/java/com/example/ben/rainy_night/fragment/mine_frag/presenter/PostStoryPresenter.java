package com.example.ben.rainy_night.fragment.mine_frag.presenter;

import android.content.Intent;

import com.example.ben.rainy_night.base.BasePresenter;

/**
 * @author Ben
 * @date 2018/2/22
 */

public interface PostStoryPresenter extends BasePresenter {

    /**
     * 初始化GridView
     */
    void initGridView();

    /**
     * 选择照片返回的数据
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * 发表帖子
     */
    void publishPost();

    /**
     * 发表帖子是否成功
     *
     * @param message
     */
    void publishPostIsSuccess(String message);

}
