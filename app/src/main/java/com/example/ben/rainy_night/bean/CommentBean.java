package com.example.ben.rainy_night.bean;

import cn.bmob.v3.BmobObject;

/**
 * 创建帖子评论表
 *
 * @author Ben
 * @date 2018/2/7
 */

public class CommentBean extends BmobObject {
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论作者
     */
    private UserBean user;
    /**
     * 评论对应的帖子
     */
    private PostBean post;

    public CommentBean() {
    }

    public CommentBean(String content, UserBean user, PostBean post) {
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public PostBean getPost() {
        return post;
    }

    public void setPost(PostBean post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "content='" + content + '\'' +
                ", user=" + user +
                ", post=" + post +
                '}';
    }
}
