package com.example.ben.rainy_night.event;

import com.example.ben.rainy_night.http.bmob.entity.UserEntity;

import java.util.List;

/**
 *
 * @author Ben
 * @date 2018/5/8
 */

public class OnPostLikesEvent {
    /**
     * 动作：增删查
     */
    private String action;
    /**
     * 查询喜欢某条帖子的集合
     */
    private List<UserEntity> list;
    /**
     * 执行动作的结果
     */
    private String result;

    public OnPostLikesEvent(String action, String result) {
        this.action = action;
        this.result = result;
    }

    public OnPostLikesEvent(String action, List<UserEntity> list, String result) {
        this.action = action;
        this.list = list;
        this.result = result;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<UserEntity> getList() {
        return list;
    }

    public void setList(List<UserEntity> list) {
        this.list = list;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
