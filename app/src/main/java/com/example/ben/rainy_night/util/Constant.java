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
     * cache 数据缓存||音乐类型
     */

    public static final String DOLPHIN_MUSIC_CACHE = "dolphin_music";

    public static final String DOLPHIN_NATURAL_MUSIC_CACHE = "dolphin_music1";
    public static final String DOLPHIN_LIGHT_MUSIC_CACHE = "dolphin_music2";

    public static final String DOLPHIN_BEFORE_SLEEP_AND_READ = "睡前伴读";
    public static final String DOLPHIN_HYPNOSIS = "催眠ASMR";
    public static final String DOLPHIN_NICE_PEOPLE = "耐撕の人";
    public static final String DOLPHIN_SAY_GOOG_NIGHT = "说晚安";

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
    public static final String MUSIC_START = "MUSIC_START";
    public static final String MUSIC_RESUME = "MUSIC_RESUME";
    public static final String MUSIC_PAUSE = "MUSIC_PAUSE";
    public static final String MUSIC_STOP = "MUSIC_STOP";
    public static final String MUSIC_PREVIOUS = "MUSIC_PREVIOUS";
    public static final String MUSIC_NEXT = "MUSIC_NEXT";
    public static final String MUSIC_SET_CYCLE_MODE = "MUSIC_SET_CYCLE_MODE";
    public static final String MUSIC_SET_TIME = "MUSIC_SET_TIME";

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

    /**
     * 音乐循环模式
     */
    //单曲循环
    public static final int PLAY_IN_SINGLE_LOOP = 1;

    //随机播放
    public static final int PLAY_IN_RANDOM = 2;

    //列表循环
    public static final int PLAY_IN_LIST_LOOP = 3;

    //顺序播放
    public static final int PLAY_IN_ORDER = 4;
}
