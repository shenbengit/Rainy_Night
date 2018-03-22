package com.example.ben.rainy_night.fragment.mine_frag.model;

import com.example.ben.rainy_night.http.bmob.entity.UserEntity;
import com.example.ben.rainy_night.http.bmob.PostBmob;

/**
 * @author Ben
 * @date 2018/2/23
 */

public class PostModelImpl implements PostModel {


    /**
     * 发表帖子，不包含图片
     *
     * @param content 帖子内容
     * @param user    帖子作者
     */
    @Override
    public void publishPost(String content, UserEntity user) {
        PostBmob.getInstance().publishPost(content, user);
    }

    /**
     * 发表帖子，包含图片
     *
     * @param paths   帖子图片路径
     * @param content 帖子内容
     * @param user    帖子作者
     */
    @Override
    public void publishPostWithPicture(String[] paths, String content, UserEntity user) {
        PostBmob.getInstance().publishPostWithPicture(paths, content, user);
    }

    /**
     * 查询帖子
     */
    @Override
    public void queryPost() {
        PostBmob.getInstance().queryPost();
    }
}
