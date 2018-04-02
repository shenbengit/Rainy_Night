package com.example.ben.rainy_night.http.okgo.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 自然音符 GSon 实体类
 *
 * @author Ben
 * @date 2018/3/15
 */

public class MusicEntity implements Serializable{


    private static final long serialVersionUID = -1067586129370775738L;
    /**
     * code : 0
     * msg : null
     * data : [{"sceneId":121,"sceneType":1,"sceneName":"涓涓流水","coverUrl":"http://fileserver1.clife.net:8080/group1/M00/23/AA/Cvtlp1n67U6AaxeEAAK-fLQT2Ug080.png","coverSize":179836,"voiceUrl":null,"voiceSize":0,"alphaUrl":null,"alphaSize":0,"videoUrl":"http://htsleep.hetyj.com/%E7%99%BD%E5%99%AA%E9%9F%B3%E8%A7%86%E9%A2%91-%E6%B6%93%E6%B6%93%E6%B5%81%E6%B0%B4.1509617066921.mp4","videoSize":7465861,"videoPictureUrl":"http://fileserver1.clife.net:8080/group1/M00/23/AA/Cvtlp1n67bSAWyLkAAHhZ23UF1k330.png","videoPictureSize":123239,"audioUrl":"http://htsleep.hetyj.com/%E7%99%BD%E5%99%AA%E9%9F%B3-%E6%B6%93%E6%B6%93%E6%B5%81%E6%B0%B4.1509616997642.mp3","audioSize":1468006,"audioPictureUrl":null,"audioPictureSize":0,"priority":1,"applyType":"30639","publishStatus":1,"publishTime":"2017-11-02 10:04:50","updateTime":"2017-11-02 10:04:50","createTime":"2017-11-02 10:04:50","sceneDetail":null,"totalsize":9236942}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private static final long serialVersionUID = -2042957329963517025L;
        /**
         * sceneId : 121
         * sceneType : 1
         * sceneName : 涓涓流水
         * coverUrl : http://fileserver1.clife.net:8080/group1/M00/23/AA/Cvtlp1n67U6AaxeEAAK-fLQT2Ug080.png
         * coverSize : 179836
         * voiceUrl : null
         * voiceSize : 0
         * alphaUrl : null
         * alphaSize : 0
         * videoUrl : http://htsleep.hetyj.com/%E7%99%BD%E5%99%AA%E9%9F%B3%E8%A7%86%E9%A2%91-%E6%B6%93%E6%B6%93%E6%B5%81%E6%B0%B4.1509617066921.mp4
         * videoSize : 7465861
         * videoPictureUrl : http://fileserver1.clife.net:8080/group1/M00/23/AA/Cvtlp1n67bSAWyLkAAHhZ23UF1k330.png
         * videoPictureSize : 123239
         * audioUrl : http://htsleep.hetyj.com/%E7%99%BD%E5%99%AA%E9%9F%B3-%E6%B6%93%E6%B6%93%E6%B5%81%E6%B0%B4.1509616997642.mp3
         * audioSize : 1468006
         * audioPictureUrl : null
         * audioPictureSize : 0
         * priority : 1
         * applyType : 30639
         * publishStatus : 1
         * publishTime : 2017-11-02 10:04:50
         * updateTime : 2017-11-02 10:04:50
         * createTime : 2017-11-02 10:04:50
         * sceneDetail : null
         * totalsize : 9236942
         */

        private int sceneId;
        private int sceneType;
        private String sceneName;
        private String coverUrl;
        private int coverSize;
        private String voiceUrl;
        private int voiceSize;
        private String alphaUrl;
        private int alphaSize;
        private String videoUrl;
        private int videoSize;
        private String videoPictureUrl;
        private int videoPictureSize;
        private String audioUrl;
        private int audioSize;
        private String audioPictureUrl;
        private int audioPictureSize;
        private int priority;
        private String applyType;
        private int publishStatus;
        private String publishTime;
        private String updateTime;
        private String createTime;
        private String sceneDetail;
        private int totalsize;

        public int getSceneId() {
            return sceneId;
        }

        public void setSceneId(int sceneId) {
            this.sceneId = sceneId;
        }

        public int getSceneType() {
            return sceneType;
        }

        public void setSceneType(int sceneType) {
            this.sceneType = sceneType;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getCoverSize() {
            return coverSize;
        }

        public void setCoverSize(int coverSize) {
            this.coverSize = coverSize;
        }

        public String getVoiceUrl() {
            return voiceUrl;
        }

        public void setVoiceUrl(String voiceUrl) {
            this.voiceUrl = voiceUrl;
        }

        public int getVoiceSize() {
            return voiceSize;
        }

        public void setVoiceSize(int voiceSize) {
            this.voiceSize = voiceSize;
        }

        public String getAlphaUrl() {
            return alphaUrl;
        }

        public void setAlphaUrl(String alphaUrl) {
            this.alphaUrl = alphaUrl;
        }

        public int getAlphaSize() {
            return alphaSize;
        }

        public void setAlphaSize(int alphaSize) {
            this.alphaSize = alphaSize;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getVideoSize() {
            return videoSize;
        }

        public void setVideoSize(int videoSize) {
            this.videoSize = videoSize;
        }

        public String getVideoPictureUrl() {
            return videoPictureUrl;
        }

        public void setVideoPictureUrl(String videoPictureUrl) {
            this.videoPictureUrl = videoPictureUrl;
        }

        public int getVideoPictureSize() {
            return videoPictureSize;
        }

        public void setVideoPictureSize(int videoPictureSize) {
            this.videoPictureSize = videoPictureSize;
        }

        public String getAudioUrl() {
            return audioUrl;
        }

        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }

        public int getAudioSize() {
            return audioSize;
        }

        public void setAudioSize(int audioSize) {
            this.audioSize = audioSize;
        }

        public String getAudioPictureUrl() {
            return audioPictureUrl;
        }

        public void setAudioPictureUrl(String audioPictureUrl) {
            this.audioPictureUrl = audioPictureUrl;
        }

        public int getAudioPictureSize() {
            return audioPictureSize;
        }

        public void setAudioPictureSize(int audioPictureSize) {
            this.audioPictureSize = audioPictureSize;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getApplyType() {
            return applyType;
        }

        public void setApplyType(String applyType) {
            this.applyType = applyType;
        }

        public int getPublishStatus() {
            return publishStatus;
        }

        public void setPublishStatus(int publishStatus) {
            this.publishStatus = publishStatus;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSceneDetail() {
            return sceneDetail;
        }

        public void setSceneDetail(String sceneDetail) {
            this.sceneDetail = sceneDetail;
        }

        public int getTotalsize() {
            return totalsize;
        }

        public void setTotalsize(int totalsize) {
            this.totalsize = totalsize;
        }
    }
}
