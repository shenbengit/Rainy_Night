package com.example.ben.rainy_night.fragment.event;

import com.example.ben.rainy_night.http.bmob.entity.PostEntity;

import java.util.List;

/**
 *
 * @author Ben
 * @date 2018/2/26
 */

public class OnPostEvent {
    private String result;
    private String action;
    private List<PostEntity> list;

    public OnPostEvent() {
    }

    public OnPostEvent(String result) {
        this.result = result;
    }

    public OnPostEvent(String result, String action) {
        this.result = result;
        this.action = action;
    }

    public OnPostEvent(String result, String action, List<PostEntity> list) {
        this.result = result;
        this.action = action;
        this.list = list;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<PostEntity> getList() {
        return list;
    }

    public void setList(List<PostEntity> list) {
        this.list = list;
    }
}
