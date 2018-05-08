package com.example.ben.rainy_night.event;

import android.content.Intent;

/**
 *
 * @author Ben
 * @date 2018/1/25
 */

public class OnActivityResultEvent {
    private int requestCode;
    private int resultCode;
    private Intent data;

    public OnActivityResultEvent() {
    }

    public OnActivityResultEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }
}
