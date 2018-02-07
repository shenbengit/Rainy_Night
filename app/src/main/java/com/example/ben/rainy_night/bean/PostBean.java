package com.example.ben.rainy_night.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 创建帖子表
 *
 * @author Ben
 * @date 2018/2/7
 */

public class PostBean extends BmobObject {
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 帖子作者
     */
    private UserBean user;
    /**
     * 喜欢帖子的人
     */
    private BmobRelation likes;

    public PostBean() {

    }

    public PostBean(String content, UserBean user, BmobRelation likes) {
        this.content = content;
        this.user = user;
        this.likes = likes;
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

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "PostBean{" +
                "content='" + content + '\'' +
                ", user=" + user +
                ", likes=" + likes +
                '}';
    }
}
