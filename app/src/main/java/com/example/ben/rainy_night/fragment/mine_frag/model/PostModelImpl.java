package com.example.ben.rainy_night.fragment.mine_frag.model;

import com.example.ben.rainy_night.http.bmob.PostBmob;
import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

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
     *
     * @param action    用来区别下拉刷新、上拉加载
     * @param createdAt 根据创建时间查询
     */
    @Override
    public void queryPost(String action, String createdAt) {
        PostBmob.getInstance().queryPost(action, createdAt);
    }

    /**
     * 给某条帖子添加评论
     *
     * @param objectId 帖子的objectId
     * @param content  评论帖子的内容
     */
    @Override
    public void addPostComment(String objectId, String content) {
        PostBmob.getInstance().addPostComment(objectId, content);
    }

    /**
     * 删除帖子的评论
     *
     * @param objectId 评论的objectId
     */
    @Override
    public void removePostComment(String objectId) {
        PostBmob.getInstance().removePostComment(objectId);
    }

    /**
     * 查询某条帖子的所有评论
     *
     * @param objectId 帖子的objectId
     */
    @Override
    public void queryPostComment(String objectId) {
        PostBmob.getInstance().queryPostComment(objectId);
    }

    /**
     * 给某条帖子“点赞”
     *
     * @param objectId 帖子的objectId
     */
    @Override
    public void addPostLikes(String objectId) {
        PostBmob.getInstance().addPostLikes(objectId);
    }

    /**
     * 给某条帖子取消“取消点赞”
     *
     * @param objectId 帖子的objectId
     */
    @Override
    public void removePostLikes(String objectId) {
        PostBmob.getInstance().removePostLikes(objectId);
    }

    /**
     * 查询喜欢某条帖子的人数
     *
     * @param objectId 帖子的objectId
     */
    @Override
    public void queryPostLikes(String objectId) {
        PostBmob.getInstance().queryPostLikes(objectId);
    }
}
