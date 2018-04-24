package com.example.ben.rainy_night.http.okgo.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ben
 * @date 2018/4/22
 */

public class SleepReadEntity implements Serializable{


    private static final long serialVersionUID = -6215214602043826392L;
    /**
     * code : 0
     * data : [{"articleId":211,"articleTitle":"晚上喝茶会影响睡眠吗？","articleDesc":"晚上喝茶不一定会失眠，关键看你喝什么茶，以及怎么喝。","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/66/B7/Cvtlp1rUOI6ACsLqAAJNhcvaAB0602.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=211"},{"articleId":200,"articleTitle":"什么？你游完泳居然失眠了","articleDesc":"运动把整个身体都撩起来了，虽然你已经很想睡了，但是你的身体还HIGH着呢","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/56/46/Cvtlp1qwdjiAD5KnAAKWCh-hvgc718.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=200"},{"articleId":199,"articleTitle":"赖床睡不着容易产生\u201c垃圾睡眠\u201d","articleDesc":"垃圾睡眠\u201d大多是由于现代电子产品的泛滥和快节奏的生活压力所导致","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/5E/5B/Cvtlhlqwb6SAGz3_AAC37QNGrsw952.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=199"},{"articleId":192,"articleTitle":"如何应对剧烈运动后的肌肉酸痛","articleDesc":"睡眠是修复损失最好的办法之一，大量的运动过后应该给自己足够的睡眠时间。","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/58/2B/CvtlhlqfiLmABEktAAHuFkm3xKE419.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=192"},{"articleId":186,"articleTitle":"为什么喝茶容易失眠？如何喝茶才不会失眠？","articleDesc":"喝茶造成失眠的原因并不是绝对的，它与饮茶的时间、饮茶量、不同茶类、不同个体情况等均有关系。","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/4C/BF/Cvtlp1qWSAWABiO4AAMxOXIyCNE762.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=186"},{"articleId":182,"articleTitle":"什么时间睡觉最有效？","articleDesc":"把握有效的睡眠时间，最好晚上9点钟睡觉，最晚不要超过10点半","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/4F/41/Cvtlhlp4JWaAC2PnAAC37QNGrsw926.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=182"},{"articleId":180,"articleTitle":"睡觉超过8小时会早衰？5个「睡眠习惯」让你越睡越不健康！","articleDesc":"睡眠太多或太少，都会导致大脑早衰？","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/4F/38/Cvtlhlp4Gr-Ac7hEAADQWHGflL0320.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=180"},{"articleId":176,"articleTitle":"什么时间做运动最好？","articleDesc":"一般人一天有24小时固定的生理时钟，按照这个生理规律从事运动或身体活动会更有效果","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/4B/C7/CvtlhlpwRiWAfu49AAHuFkm3xKE809.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=176"},{"articleId":174,"articleTitle":"冬季赖床5分钟 有利于健康","articleDesc":"冬季起床更是宜慢不宜快。尤其脑供血不足、颈椎病患者和心血管疾病患者等更是要注意。","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/42/E3/Cvtlp1pu4gWAY8XoAACzPj4YCVw464.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=174"},{"articleId":214,"articleTitle":"我来告诉你为什么有人喝咖啡竟能帮助睡眠？","articleDesc":"咖啡能帮助睡眠，可能吗？","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/68/FE/Cvtlp1rX83uADQYZAAJCnnZgH9o020.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=214"},{"articleId":213,"articleTitle":"叫醒一个正在梦游的人会发生什么？","articleDesc":"据说叫醒正在梦游中的人会变成白痴？是真的吗？","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/70/8C/CvtlhlrWovmAKDJxAAJo-zlPQ3s775.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=213"},{"articleId":212,"articleTitle":"失眠，就一定要吃安眠药吗？","articleDesc":"如何科学的服用才能达到更好的助眠效果呢？","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/70/01/CvtlhlrVhPWAWCcJAAIUEGxQJNA516.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=212"},{"articleId":211,"articleTitle":"晚上喝茶会影响睡眠吗？","articleDesc":"晚上喝茶不一定会失眠，关键看你喝什么茶，以及怎么喝。","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/66/B7/Cvtlp1rUOI6ACsLqAAJNhcvaAB0602.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=211"},{"articleId":210,"articleTitle":"失眠了？不妨试试音乐疗法","articleDesc":"临床实验发现，通过音乐疗法，失眠者往往能够排除心中的杂念，进入音乐的意境中，消除烦恼忧愁，睡眠质量也就提高了。","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/6D/4A/CvtlhlrQQHiAbsInAAG95hIe_yg603.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=210"},{"articleId":209,"articleTitle":"进食的时间也会影响睡眠质量","articleDesc":"临睡前不要吃得有多又油腻，否则就会一夜难眠","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/64/4C/Cvtlp1rO7y2AKaHKAAJSGXZ9yv0394.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=209"},{"articleId":208,"articleTitle":"夏季晚上怎么睡觉好？","articleDesc":"天气炎热，晚上睡觉应该注意什么？","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/63/7A/Cvtlp1rNf0-AdTebAAH9IR0xLbc679.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=208"},{"articleId":207,"articleTitle":"多梦也是睡眠障碍","articleDesc":"多梦的人往往睡眠质量不好。","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/6B/11/CvtlhlrMVPyAf0UVAAO6qSZ9eog734.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=207"},{"articleId":206,"articleTitle":"失眠练太极拳，可以改善睡眠","articleDesc":"太极拳是融合了中国古代阴阳五行学说、道家的哲学思想和养生术，以及中医的经络学说，而创编的一种优秀的文化体育项目。","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/68/AF/CvtlhlrEOKqAXmdYAAM83mNUXFI006.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=206"},{"articleId":205,"articleTitle":"反复倒腾生物钟有损身体健康","articleDesc":"推迟1小时与提前1小时是不一样的","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/67/07/CvtlhlrBwemAQHyRAAMWJsfcQu4396.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=205"},{"articleId":168,"articleTitle":"一个公式算出睡眠好坏 这8类失眠者终于有救了","articleDesc":"睡眠效率 = 睡眠时间/在床上的时间","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/44/41/CvtlhlpoHy6AMWJ9AADL6lVd7xk175.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=168"},{"articleId":149,"articleTitle":"男人适合什么茶？晚上喝茶其实有助睡眠？","articleDesc":"男性适合喝绿茶、三年以上的生普洱、乌龙茶，特别是武夷岩茶，被称为\u201c男人喝的茶\u201d","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/36/8A/CvtlhlpA1CSAN6QoAAEV_L6d0JU841.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=149"},{"articleId":139,"articleTitle":"体育锻炼如何影响体温节律","articleDesc":"如果你想立即提高睡眠质量而且以前不锻炼，那么开始锻炼吧。锻炼可以在很多方面改善你的睡眠，更不用提对健康的好处","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/35/5F/Cvtlhlo5xJCARHDRAAHuFkm3xKE512.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=139"},{"articleId":138,"articleTitle":"睡前锻炼会妨碍睡眠吗","articleDesc":"睡前应避免剧烈运动，理由是剧烈运动过程中人体会释放更多的肾上腺素和其他激素，这些激素会使人的情绪处在激越状态","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/2D/06/Cvtlp1o4pW2AXKntAADOL_hrquc492.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=138"},{"articleId":137,"articleTitle":"别不当回事 清晨赖床预示着三种病","articleDesc":"我们的痛苦大多来自睡眠节奏紊乱、睡眠环境不良、没有正确的清醒程序","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/2D/04/Cvtlp1o4lIaAbdbSAAFyXCDUfCU905.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=137"},{"articleId":80,"articleTitle":"喝茶能治失眠？做错了反而会失眠","articleDesc":"茶叶含有的咖啡碱能兴奋中枢神经系统","imgUrl":"http://fileserver1.clife.net:8080/group1/M00/27/87/Cvtlp1odTqiACKBeAAD32Lh_PcE672.jpg","articleUrl":"https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=80"}]
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
        private static final long serialVersionUID = -995693276816630333L;
        /**
         * articleId : 211
         * articleTitle : 晚上喝茶会影响睡眠吗？
         * articleDesc : 晚上喝茶不一定会失眠，关键看你喝什么茶，以及怎么喝。
         * imgUrl : http://fileserver1.clife.net:8080/group1/M00/66/B7/Cvtlp1rUOI6ACsLqAAJNhcvaAB0602.jpg
         * articleUrl : https://cms.clife.cn/manages/series/clifeapp/page/view.html#type=2&id=211
         */

        private int articleId;
        private String articleTitle;
        private String articleDesc;
        private String imgUrl;
        private String articleUrl;

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public String getArticleTitle() {
            return articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public String getArticleDesc() {
            return articleDesc;
        }

        public void setArticleDesc(String articleDesc) {
            this.articleDesc = articleDesc;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getArticleUrl() {
            return articleUrl;
        }

        public void setArticleUrl(String articleUrl) {
            this.articleUrl = articleUrl;
        }
    }
}
