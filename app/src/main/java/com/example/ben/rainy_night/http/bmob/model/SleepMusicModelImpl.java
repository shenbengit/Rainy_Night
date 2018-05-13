package com.example.ben.rainy_night.http.bmob.model;

import com.example.ben.rainy_night.http.bmob.SleepMusicBmob;

/**
 * @author Ben
 * @date 2018/5/13
 */

public class SleepMusicModelImpl implements SleepMusicModel {
    /**
     * 查询睡眠音乐相关数据
     *
     * @param title title
     */
    @Override
    public void querySleepMusic(String title) {
        SleepMusicBmob.getInstance().querySleepMusic(title);
    }
}
