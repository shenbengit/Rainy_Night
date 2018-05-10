package com.example.ben.rainy_night.http.bmob.entity;

import cn.bmob.v3.BmobObject;

/**
 * 创建帖子评论表
 *
 * @author Ben
 * @date 2018/2/7
 */

public class CommentEntity extends BmobObject {
    private static final long serialVersionUID = -2315610793486760531L;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论作者
     */
    private UserEntity user;
    /**
     * 评论对应的帖子
     */
    private PostEntity post;

    public CommentEntity() {
    }

    public CommentEntity(String content, UserEntity user, PostEntity post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "content='" + content + '\'' +
                ", user=" + user +
                ", post=" + post +
                '}';
    }
}
