package com.example.ben.rainy_night.fragment.mine_frag.model;

import com.example.ben.rainy_night.bean.UserBean;

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
    void publishPost(String content, UserBean user);

    /**
     * 发表帖子，包含图片
     *
     * @param paths   帖子图片路径
     * @param content 帖子内容
     * @param user    帖子作者
     */
    void publishPostWithPicture(String[] paths, String content, UserBean user);
}
