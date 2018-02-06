package com.example.ben.rainy_night.util;

import com.orhanobut.logger.Logger;

/**
 * @author Ben
 * @date 2018/2/6
 */

public class LoggerUtil {

    public static void d(String message) {
        Logger.d(message);
    }

    public static void d(Object object) {
        Logger.d(object);
    }

    public static void e(String message) {
        Logger.e(message);
    }

    public static void w(String message) {
        Logger.w(message);
    }

    public static void v(String message) {
        Logger.v(message);
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void wtf(String message) {
        Logger.wtf(message);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

}
