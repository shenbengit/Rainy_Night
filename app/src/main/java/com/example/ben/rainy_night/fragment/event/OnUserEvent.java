package com.example.ben.rainy_night.fragment.event;

import com.example.ben.rainy_night.bean.UserBean;

/**
 *
 * @author Ben
 * @date 2018/2/5
 */

public class OnUserEvent {
    private String message;
    private UserBean bean;

    public OnUserEvent(String message, UserBean bean) {
        this.message = message;
        this.bean = bean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
                "message='" + message + '\'' +
                ", bean=" + bean +
                '}';
    }
}
