package com.example.ben.rainy_night.http.bmob.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 睡眠音乐
 *
 * @author Ben
 * @date 2018/4/22
 */

public class SleepMusicEntity extends BmobObject implements Serializable{
    private static final long serialVersionUID = 4160780923373888455L;
    /**
     * 标题
     */
    String title;
    /**
     * 音乐json
     */
    String json;

    public SleepMusicEntity() {
    }

    public SleepMusicEntity(String title, String json) {
        this.title = title;
        this.json = json;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return "SleepMusicEntity{" +
                "title='" + title + '\'' +
                ", json='" + json + '\'' +
                '}';
    }
}
