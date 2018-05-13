package com.example.ben.rainy_night.http.bmob.model;

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

    /**
     * 给某条帖子添加评论
     *
     * @param objectId 帖子的objectId
     * @param content  评论帖子的内容
     */
    void addPostComment(String objectId, String content);

    /**
     * 删除帖子的评论
     *
     * @param objectId 评论的objectId
     */
    void removePostComment(String objectId);

    /**
     * 查询某条帖子的所有评论
     *
     * @param objectId 帖子的objectId
     */
    void queryPostComment(String objectId);

    /**
     * 给某条帖子“点赞”
     *
     * @param objectId 帖子的objectId
     */
    void addPostLikes(String objectId);

    /**
     * 给某条帖子取消“取消点赞”
     *
     * @param objectId 帖子的objectId
     */
    void removePostLikes(String objectId);

    /**
     * 查询喜欢某条帖子的人数
     *
     * @param objectId 帖子的objectId
     */
    void queryPostLikes(String objectId);
}
