package com.example.ben.rainy_night.http.okgo.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ben
 * @date 2018/4/19
 */

public class SleepReportEntity implements Serializable{

    private static final long serialVersionUID = 653732249514745005L;
    /**
     * code : 0
     * data : [{"analysisId":74,"analysisTitle":"睡眠时间过长有危害","analysisDesc":"你睡了那么久，占了床那么多便宜，要不干脆结婚吧~","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/2F/9C/CvtlhlodMs6ALD4kAAEsJomAzak700.jpg","keyWordsList":[{"keyId":20,"keywords":"赖床"}]},{"analysisId":71,"analysisTitle":"喝茶能治失眠？做错了反而会失眠","analysisDesc":"茶和咖啡是表亲，让你失眠、让你提神，效果杠杠的","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/2F/9C/CvtlhlodLk2ANJ1-AAGEJIidca0416.jpg","keyWordsList":[{"keyId":38,"keywords":"喝茶"}]},{"analysisId":69,"analysisTitle":"睡前锻炼会影响睡眠","analysisDesc":"运动时间最好是睡前6小时，睡前3小时剧烈运动会让你更加亢奋哟。","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/2F/9B/CvtlhlodLeiAOBdIAAE4j-83QXk331.jpg","keyWordsList":[{"keyId":19,"keywords":"运动"}]}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private static final long serialVersionUID = 856199250086709534L;
        /**
         * analysisId : 74
         * analysisTitle : 睡眠时间过长有危害
         * analysisDesc : 你睡了那么久，占了床那么多便宜，要不干脆结婚吧~
         * imgUrl : http://fileserver1.clife.net:8080/group1/M00/2F/9C/CvtlhlodMs6ALD4kAAEsJomAzak700.jpg
         * keyWordsList : [{"keyId":20,"keywords":"赖床"}]
         */

        private int analysisId;
        private String analysisTitle;
        private String analysisDesc;
        private String imgUrl;
        private List<KeyWordsListBean> keyWordsList;

        public int getAnalysisId() {
            return analysisId;
        }

        public void setAnalysisId(int analysisId) {
            this.analysisId = analysisId;
        }

        public String getAnalysisTitle() {
            return analysisTitle;
        }

        public void setAnalysisTitle(String analysisTitle) {
            this.analysisTitle = analysisTitle;
        }

        public String getAnalysisDesc() {
            return analysisDesc;
        }

        public void setAnalysisDesc(String analysisDesc) {
            this.analysisDesc = analysisDesc;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public List<KeyWordsListBean> getKeyWordsList() {
            return keyWordsList;
        }

        public void setKeyWordsList(List<KeyWordsListBean> keyWordsList) {
            this.keyWordsList = keyWordsList;
        }

        public static class KeyWordsListBean {
            /**
             * keyId : 20
             * keywords : 赖床
             */

            private int keyId;
            private String keywords;

            public int getKeyId() {
                return keyId;
            }

            public void setKeyId(int keyId) {
                this.keyId = keyId;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }
        }
    }
}
