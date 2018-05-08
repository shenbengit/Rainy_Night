package com.example.ben.rainy_night.event;

import com.example.ben.rainy_night.http.bmob.entity.CommentEntity;

import java.util.List;

/**
 * @author Ben
 * @date 2018/5/8
 */

public class OnPostCommentEvent {
    /**
     * 动作：增删查
     */
    private String action;
    /**
     * 查询评论某条帖子的集合
     */
    private List<CommentEntity> commentList;
    /**
     * 执行动作的结果
     */
    private String result;

    public OnPostCommentEvent(String action, String result) {
        this.action = action;
        this.result = result;
    }

    public OnPostCommentEvent(String action, List<CommentEntity> commentList, String result) {
        this.action = action;
        this.commentList = commentList;
        this.result = result;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<CommentEntity> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentEntity> commentList) {
        this.commentList = commentList;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
