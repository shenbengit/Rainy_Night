package com.example.ben.rainy_night.event;

import com.example.ben.rainy_night.http.bmob.entity.SleepMusicEntity;

/**
 *
 * @author Ben
 * @date 2018/5/13
 */

public class OnSleepMusicEvent {
    private String result;
    private SleepMusicEntity entity;

    public OnSleepMusicEvent(String result, SleepMusicEntity entity) {
        this.result = result;
        this.entity = entity;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SleepMusicEntity getEntity() {
        return entity;
    }

    public void setEntity(SleepMusicEntity entity) {
        this.entity = entity;
    }
}
