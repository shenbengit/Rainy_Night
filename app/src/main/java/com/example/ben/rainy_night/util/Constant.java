package com.example.ben.rainy_night.util;

import com.lzx.musiclibrary.cache.CacheUtils;

/**
 * 常量
 *
 * @author Ben
 * @date 2018/2/23
 */

public class Constant {
    /**
     * 视频缓存路径
     */
    public static final String VIDEO_CACHE_PATH = CacheUtils.getStorageDirectoryPath() + "/RainyNight/Video/";
    /**
     * 音乐缓存路径
     */
    public static final String MUSIC_CACHE_PATH = CacheUtils.getStorageDirectoryPath() + "/RainyNight/Music/";

    /**
     * 用户objectId
     */
    public static final String USER_OBJECT_ID = "user_objectId";
    /**
     * 用户手机号
     */
    public static final String USER_PHONE = "user_phone";
    /**
     * 用户密码
     */
    public static final String USER_PASSWORD = "user_password";
    /**
     * 用户头像url地址
     */
    public static final String USER_HEAD_IMAGE = "user_head_image";
    /**
     * 用户名字
     */
    public static final String USER_NAME = "user_name";
    /**
     * 用户昵称
     */
    public static final String USER_NICK_NAME = "user_nickname";
    /**
     * 用户性别
     */
    public static final String USER_SEX = "user_sex";
    /**
     * 用户生日
     */
    public static final String USER_BIRTHDAY = "user_birthday";
    /**
     * 用户邮箱
     */
    public static final String USER_EMAIL = "user_email";

    /**
     * 音乐播放相关
     */
    public static final String ACTION_CLOSE = "com.lzx.nicemusic.android.Action_CLOSE";
    public static final String ACTION_FAVORITE = "com.lzx.nicemusic.android.Action_FAVORITE";
    public static final String ACTION_LYRICS = "com.lzx.nicemusic.android.Action_Lyrics";

    /**
     * 助眠界面相关，记录最近一次播放音乐的位置
     */
    public static final String LATEST_MUSIC_TYPE = "LATEST_MUSIC_TYPE";
    public static final String LATEST_MUSIC_NAME = "LATEST_MUSIC_NAME";
    public static final String LATEST_MUSIC_POSITION = "LATEST_MUSIC_POSITION";

    /**
     * 音乐专辑封面(方形)
     */
    public static final String MUSIC_SQUARE_COVER = "http://bmob-cdn-16440.b0.upaiyun.com/2018/05/12/6cae2fba409874e180088143aa4928cb.png";

    /**
     * 请求码
     */
    public static final int REQUEST_IMAGE = 188;
    public static final int REQUEST_POP = 99;
    public static final int POST_SROTY = 101;
    public static final int USER_LOGIN = 102;
    /**
     * 帖子最大选择图片数
     */
    public static final int MAX_PICTURES = 6;

    /**
     * 海豚睡眠相关
     */

    public static final int REQUEST_SUCCESS = 0;

    public static final int DOLPHIN_NATURAL_MUSIC = 1;
    public static final int DOLPHIN_LIGHT_MUSIC = 2;

    /**
     * 其他变量
     */
    public static final int DOLPHIN_HYPNOSIS = 27;
    public static final int DOLPHIN_BEFORE_SLEEP_AND_READ = 7;
    public static final int DOLPHIN_NICE_PEOPLE = 6;
    public static final int DOLPHIN_SAY_GOOG_NIGHT = 5;

    public static final String ADD_POST_PICTURE = "ADD_POST_PICTURE";

    public static final String POST_COMMENT = "POST_COMMENT";
    public static final String POST_LIKES = "POST_LIKES";

    public static final String ACTION_ADD = "ACTION_ADD";
    public static final String ACTION_REMOVE = "ACTION_REMOVE";
    public static final String ACTION_QUERY = "ACTION_QUERY";

    /**
     * 海豚睡眠 api接口
     * baseUrl
     */
    public static final String DOLPHIN_BASEURL = "https://api.clife.cn/";

    public static final String DOLPHIN_MUSIC = "v1/app/csleep/scene/getSceneList?appId=30639&sceneIds=";
    public static final String DOLPHIN_SHARE = "/v1/app/csleep/operate/getPictureList?appId=30639&sleepRegion=1";

    public static final String DOLPHIN_ALBUMS_MEDIA_LIST = "/v1/app/manage/common/media/getAlbumsMediaList";

    /**
     * cache 数据缓存||音乐类型
     */
    public static final String DOLPHIN_MUSIC_CACHE = "dolphin_music";

    public static final String DOLPHIN_NATURAL_MUSIC_CACHE = "自然音符";
    public static final String DOLPHIN_LIGHT_MUSIC_CACHE = "轻音乐";
    public static final String DOLPHIN_BEFORE_SLEEP_AND_READ_CACHE = "睡前伴读";
    public static final String DOLPHIN_NICE_PEOPLE_CACHE = "耐撕の人";
    public static final String DOLPHIN_HYPNOSIS_CACHE = "催眠ASMR";
    public static final String DOLPHIN_SAY_GOOG_NIGHT_CACHE = "说晚安";

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
    public static final String REQUSET_POST_REFRESH = "post_refresh";
    public static final String REQUSET_POST_LOAD_MORE = "post_load_more";

    /**
     * 循环模式
     */
    public static final String CYCLE_MODE = "cycle_mode";
    public static final String SINGLE_CYCLE = "single_cycle";
    public static final String LIST_CYCLE = "list_cycle";

    /**
     * 定时
     */
    public static final String REMAIN_TIME = "ramain_time";
    public static final int REMAIN_TIME_ZZ = -1;
    public static final int REMAIN_TIME_10 = 10;
    public static final int REMAIN_TIME_20 = 20;
    public static final int REMAIN_TIME_30 = 30;


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
