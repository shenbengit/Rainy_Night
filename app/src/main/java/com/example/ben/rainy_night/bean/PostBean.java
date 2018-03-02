package com.example.ben.rainy_night.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
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

    /**
     * 帖子里面的图片
     */
    private List<BmobFile> pictures;

    public PostBean() {

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

    public List<BmobFile> getPictures() {
        return pictures;
    }

    public void setPictures(List<BmobFile> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "PostBean{" +
                "content='" + content + '\'' +
                ", user=" + user +
                ", likes=" + likes +
                ", pictures=" + pictures +
                '}';
    }
}
