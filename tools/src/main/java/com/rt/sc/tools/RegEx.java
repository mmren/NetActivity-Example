package com.rt.sc.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by renmingming on 15/9/21.
 */
public class RegEx
{

    private static final boolean DEBUG = true;

    private static final String TAG = "RegEx";

    // 手机号码
    public static final String PHONENUMBER = "^(((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0-9])|(18[0-9]))[0-9]{8})$";

    // 电话号码
    public static final String TELPHONE = "^//(?(//d{3})//)?[- ]?(//d{6})[- ]?(//d{4})$";

    // 账号
    public static final String ACCOUNT = "^[A-Za-z0-9]{6,16}$";

    // 用户密码
    public static final String PASSWORD = "(?!.*[^A-Za-z\\d])((?=.*\\d)(?=.*[A-Za-z]).{6,16})";

    // 邀请码
    public static final String YAOQINGMA = "^[A-Za-z0-9]{6}$";

    // 判断是否为数字
    public static final String DIGITAL = "[0-9]";

    //电话号码
    public static final String DIGITAL_PHONE = "^1[0-9]{10}$";

    // 表情
    public static final String FACE = "f0[0-9]{2}|f10[0-7]";

    //验证码
    public static final String VERIFICATIONCODE = "[0-9]{6}";

    //邮箱验证
    public static final String EMAIL = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

    //银行卡
    public static final String BANKCODE ="[0-9]{14,21}";
    // \u4e00-\u9fbf
    public static final String REAL ="^(?=.*[\\u4e00-\\u9fbf]+)(?=.*\\w*)[\\u4e00-\\u9fbf\\w]{2,15}$";

    public static final String REAL_MORE ="^(?=.*[\\u4e00-\\u9fbf]+)(?=.*\\w*)[\\u4e00-\\u9fbf\\w]{2,50}$";

    public static boolean check(String regex, String matchString)
    {
        if (matchString == null)
        {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(matchString);
        return m.find();
    }


}
