package com.example.ben.rainy_night.fragment.event;

/**
 *
 * @author Ben
 * @date 2018/2/26
 */

public class OnPostEvent {
    private String result;

    public OnPostEvent() {
    }

    public OnPostEvent(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
