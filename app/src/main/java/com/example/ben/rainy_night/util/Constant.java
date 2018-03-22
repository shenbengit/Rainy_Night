package com.example.ben.rainy_night.util;

/**
 * 常量
 *
 * @author Ben
 * @date 2018/2/23
 */

public class Constant {
    public static final int REQUEST_IMAGE = 188;
    public static final int MAX_PICTURES = 6;
    public static final int REQUEST_POP = 99;

    /**
     * 海豚睡眠 api接口
     * baseUrl
     */
    public static final String BASEURL_HAITUN = "https://api.clife.cn/";

    public static final int GET_MUSIC_OK = 0;
    public static final int HAITUN_NATURAL_MUSIC = 1;
    public static final int HAITUN_LIGHT_MUSIC = 2;

    public static final String HAITUN_MUSIC = "v1/app/csleep/scene/getSceneList?appId=30639&sceneIds=";
    public static final String HAITUN_SHARE = "v1/app/csleep/operate/getPictureList?appId=30639&sleepRegion=1";

    /**
     * EventBus 避免消息错乱
     */
    public static final String OK = "ok";
    public static final String REQUEST_LOGIN = "login";
    public static final String REQUEST_LOGIN_MINE = "login_mine";
    public static final String REQUEST_REGISTER = "register";
    public static final String REQUEST_EMAIL = "email";
    public static final String REQUEST_MINE = "mine";
    public static final String REQUEST_PERSONAL = "personal";
    public static final String REQUEST_NICK_NAME = "nick_name";
    public static final String REQUEST_CHANGE_PASSWORD = "change_password";
    public static final String ADD_PICTURE = "add_picture";

}
