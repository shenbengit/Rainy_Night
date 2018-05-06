package com.example.ben.rainy_night.fragment.mine_frag.model;

import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

/**
 * @author Ben
 * @date 2018/2/23
 */

public interface PostModel {
    /**
     * 发表帖子，不包含图片
     *
     * @param content 帖子内容
     * @param user    帖子作者
     */
    void publishPost(String content, UserEntity user);

    /**
     * 发表帖子，包含图片
     *
     * @param paths   帖子图片路径
     * @param content 帖子内容
     * @param user    帖子作者
     */
    void publishPostWithPicture(String[] paths, String content, UserEntity user);

    /**
     * 查询帖子
     *
     * @param action    用来区别下拉刷新、上拉加载
     * @param createdAt 根据创建时间查询
     */
    void queryPost(String action, String createdAt);
}
