package com.example.ben.rainy_night.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 创建帖子里包含图片表
 *
 * @author Ben
 * @date 2018/2/7
 */

public class PictureBean extends BmobObject {
    /**
     *图片属于的帖子
     */
    private PostBean post;
    /**
     * 图片资源
     */
    private BmobFile picture;

    public PictureBean() {
    }

    public PictureBean(PostBean post, BmobFile picture) {
        this.post = post;
        this.picture = picture;
    }

    public PostBean getPost() {
        return post;
    }

    public void setPost(PostBean post) {
        this.post = post;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "post=" + post +
                ", picture=" + picture +
                '}';
    }
}
