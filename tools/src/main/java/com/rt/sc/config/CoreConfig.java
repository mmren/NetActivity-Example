package com.rt.sc.config;

/**
 * Created by renmingming on 15/9/21.
 */
public class CoreConfig
{
    private boolean isDebug;

    private String appTag;

    private String appName;

    private boolean isOpenBaiduStat;
    //baidu map api key
    private String appBaiduMapKey;

    //统计配置
    private boolean isAnalyse;
//    private String analyseUrl;
//    private int analyseBufferSize=10;
    private String analyseChannel;
//
    private String defaultCity;



    public boolean isDebug() {
        return isDebug;
    }
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }
    public String getAppTag() {
        return appTag;
    }
    public void setAppTag(String appTag) {
        this.appTag = appTag;
    }
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public boolean isOpenBaiduStat() {
        return isOpenBaiduStat;
    }
    public void setOpenBaiduStat(boolean isOpenBaiduStat) {
        this.isOpenBaiduStat = isOpenBaiduStat;
    }
    public String getAppBaiduMapKey() {
        return appBaiduMapKey;
    }
    public void setAppBaiduMapKey(String appBaiduMapKey) {
        this.appBaiduMapKey = appBaiduMapKey;
    }

    public boolean isAnalyse() {
        return isAnalyse;
    }
    public void setAnalyse(boolean isAnalyse) {
        this.isAnalyse = isAnalyse;
    }
//    public String getAnalyseUrl() {
//        return analyseUrl;
//    }
//    public void setAnalyseUrl(String analyseUrl) {
//        this.analyseUrl = analyseUrl;
//    }
//    public int getAnalyseBufferSize() {
//        return analyseBufferSize;
//    }
//    public void setAnalyseBufferSize(int analyseBufferSize) {
//        this.analyseBufferSize = analyseBufferSize;
//    }
    public String getAnalyseChannel() {
        return analyseChannel;
    }
    public void setAnalyseChannel(String analyseChannel) {
        this.analyseChannel = analyseChannel;
    }
    public String getDefaultCity() {
        return defaultCity;
    }
    public void setDefaultCity(String defaultCity) {
        this.defaultCity = defaultCity;
    }
}
