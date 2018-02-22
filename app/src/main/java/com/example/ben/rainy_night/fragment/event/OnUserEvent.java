package com.example.ben.rainy_night.fragment.event;

import com.example.ben.rainy_night.bean.UserBean;

/**
 * @author Ben
 * @date 2018/2/5
 */

public class OnUserEvent {
    /**
     * 判断用于哪个界面发生的请求，避免EventBus消息每个界面都能接收到
     */
    private String request;
    /**
     * 判断请求结果是否成功
     */
    private String result;

    /**
     * UserBean实体
     */
    private UserBean bean;

    public OnUserEvent() {
    }

    public OnUserEvent(String request, String result, UserBean bean) {
        this.request = request;
        this.result = result;
        this.bean = bean;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public UserBean getBean() {
        return bean;
    }

    public void setBean(UserBean bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "OnUserEvent{" +
                "result='" + result + '\'' +
                ", request='" + request + '\'' +
                ", bean=" + bean +
                '}';
    }
}
