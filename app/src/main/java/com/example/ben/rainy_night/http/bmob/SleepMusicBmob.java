package com.example.ben.rainy_night.http.bmob;

import com.example.ben.rainy_night.event.OnSleepMusicEvent;
import com.example.ben.rainy_night.http.bmob.entity.SleepMusicEntity;
import com.example.ben.rainy_night.util.Constant;
import com.example.ben.rainy_night.util.LoggerUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 查询音乐电台相关数据
 *
 * @author Ben
 * @date 2018/5/13
 */

public class SleepMusicBmob {

    private SleepMusicBmob() {
    }

    public static SleepMusicBmob getInstance() {
        return Holder.bmob;
    }

    private static class Holder {
        private static final SleepMusicBmob bmob = new SleepMusicBmob();
    }

    /**
     * 查询睡眠音乐相关数据
     *
     * @param title title
     */
    public void querySleepMusic(String title) {
        BmobQuery<SleepMusicEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("title", title);
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findObjects(new FindListener<SleepMusicEntity>() {
            @Override
            public void done(List<SleepMusicEntity> list, BmobException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
                        EventBus.getDefault().post(new OnSleepMusicEvent(Constant.OK, list.get(0)));
                    } else {
                        EventBus.getDefault().post(new OnSleepMusicEvent("请求成功，但是数据为空", null));
                    }
                } else {
                    EventBus.getDefault().post(new OnSleepMusicEvent(e.getMessage() + ",ErrorCode: " + e.getErrorCode(), null));
                }
            }
        });
    }

}
