package com.example.ben.rainy_night.http.bmob.entity;

import cn.bmob.v3.BmobObject;

/**
 * 睡眠报告
 *
 * @author Ben
 * @date 2018/4/22
 */

public class SleepAnalysisEntity extends BmobObject {
    /**
     * 标题
     */
    String title;
    /**
     * 音乐json
     */
    String json;

    public SleepAnalysisEntity() {
    }

    public SleepAnalysisEntity(String title, String json) {
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
