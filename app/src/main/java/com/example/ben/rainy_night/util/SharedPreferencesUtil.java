package com.example.ben.rainy_night.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.logging.Logger;

/**
 * 封装SharedPreferences
 *
 * @author Ben
 * @date 2018/1/3
 */

public class SharedPreferencesUtil {
    private volatile static SharedPreferencesUtil mUtil;

    private static final String FILENAME = "Rainy_Night";
    private SharedPreferences preferences;
    private Editor editor;

    public SharedPreferencesUtil(Context context) {
        preferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        if (mUtil == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (mUtil == null) {
                    mUtil = new SharedPreferencesUtil(context);
                }
            }
        }
        return mUtil;
    }


    /**
     * 向SP存入指定key对应的数据
     * 其中value可以是String、boolean、float、int、long等各种基本类型的值
     *
     * @param keyName
     * @param value
     */
    public void putValue(String keyName, Object value) {
        if (value instanceof String) {
            editor.putString(keyName, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(keyName, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(keyName, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(keyName, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(keyName, (Long) value);
        } else {
            editor.putString(keyName, value.toString());
        }
        editor.apply();
    }

    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     *
     * @param keyName      键
     * @param defaultValue 默认值
     * @return
     */
    public Object getValue(String keyName, Object defaultValue) {
        if (defaultValue instanceof String) {
            return preferences.getString(keyName, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return preferences.getInt(keyName, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return preferences.getBoolean(keyName, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return preferences.getFloat(keyName, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return preferences.getLong(keyName, (Long) defaultValue);
        }
        return null;
    }

    /**
     * 清空SP里所以数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 删除SP里指定key对应的数据项
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 判断SP是否包含特定key的数据
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return preferences.contains(key);
    }
}
