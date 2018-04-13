package com.example.ben.rainy_night.util;

/**
 * 常量
 *
 * @author Ben
 * @date 2018/2/23
 */

public class Constant {

    /**
     * 音乐播放相关
     */
    public static final String ACTION_CLOSE = "com.lzx.nicemusic.android.Action_CLOSE";
    public static final String ACTION_FAVORITE = "com.lzx.nicemusic.android.Action_FAVORITE";
    public static final String ACTION_LYRICS = "com.lzx.nicemusic.android.Action_Lyrics";


    public static final int REQUEST_IMAGE = 188;
    public static final int MAX_PICTURES = 6;
    public static final int REQUEST_POP = 99;

    /**
     * 海豚睡眠 api接口
     * baseUrl
     */
    public static final String DOLPHIN_BASEURL = "https://api.clife.cn/";

    public static final int GET_MUSIC_OK = 0;
    public static final int DOLPHIN_NATURAL_MUSIC = 1;
    public static final int DOLPHIN_LIGHT_MUSIC = 2;

    public static final String DOLPHIN_MUSIC = "v1/app/csleep/scene/getSceneList?appId=30639&sceneIds=";
    public static final String DOLPHIN_SHARE = "/v1/app/csleep/operate/getPictureList?appId=30639&sleepRegion=1";

    /**
     * cache 数据缓存
     */

    public static final String DOLPHIN_MUSIC_CACHE = "dolphin_music";

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

    /**
     * 循环模式
     */
    public static final String SINGLE_CYCLE = "single_cycle";
    public static final String LIST_CYCLE = "list_cycle";

    /**
     * 音乐动作
     */
    public static final int MUSIC_START = 0x00000001;
    public static final int MUSIC_RESUME = 0x00000002;
    public static final int MUSIC_PAUSE = 0x00000003;
    public static final int MUSIC_STOP = 0x00000004;
    public static final int MUSIC_PREVIOUS = 0x00000005;
    public static final int MUSIC_NEXT = 0x00000006;
    public static final int MUSIC_SET_CYCLE_MODE = 0x00000007;
    public static final int MUSIC_SET_TIME = 0x00000008;

    /**
     * 是否允许使用数据流量下载
     */
    public static final String CAN_MOBILE_DOWNLOAD = "can_mobile_download";

    /**
     * 音乐播放器的状态
     */

    public final static int STATE_IDLE = 1;
    public final static int STATE_BUFFERING = 2;
    public final static int STATE_PLAYING = 3;
    public final static int STATE_PAUSED = 4;
    public final static int STATE_ENDED = 5;
    public final static int STATE_NONE = 6;
    public final static int STATE_ERROR = 7;
}
