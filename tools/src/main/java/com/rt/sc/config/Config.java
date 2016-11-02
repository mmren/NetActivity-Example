package com.rt.sc.config;

/**
 * Created by renmingming on 15/9/10.
 */
public class Config
{
    /**
     * 客服电话
     */
    public static final String CONSTACT_US = "4008623400";

    public static final String CONSTACT_US2 = "400-8623-400";

    /**
     * 获取验证码倒计时60s
     */
    public static final long Countdown_start = 60000;

    public static final long Countdown_end = 1000;

    public static final double dayfee = 0.003f;

    public static final double termfee = 0.03f;

    public static final String date_fromat = "yyyyMMddHHmmss";

    public static final String show_date_fromat = "yyyy-MM-dd HH:mm:ss";


    public static final String date_fromat_short = "yyyyMMdd";

    public static final String show_date_fromat_short = "yyyy-MM-dd";

    public static final String show_date_fromat_md = "MM-dd";

    /**
     * 单位秒
     */
    public static final int collect_time = 3600 * 24 * 30;

    //圆角图片半径
    public static final int radius = 4;
}
