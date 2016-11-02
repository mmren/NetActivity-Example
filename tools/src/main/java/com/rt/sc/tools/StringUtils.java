package com.rt.sc.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by renmingming on 15/9/21.
 */
public class StringUtils {

    private static final boolean DEBUG = false;

    private static final String TAG = "StringUtils";

    private static final int BUFFER_SIZE = 1024;

    private static final String ENCODING_FORMAT = "UTF-8";

    /**
     * 获取字符串中的数字
     *
     * @param string
     * @return
     */
    public static int getDigitalFromString(String string) {
        Pattern p = Pattern.compile(RegEx.DIGITAL);
        Matcher m = p.matcher(string);
        String str = m.replaceAll("").trim();
        if (StringUtils.isEmpty(str)) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }

    /***
     * 文件输入流 转字符串
     *
     * @param in InputStream
     * @return String
     * @throws Exception
     */
    public static String inputStreamToString(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
            outStream.write(data, 0, count);
        }
        data = null;
        in.close();
        return new String(outStream.toByteArray(), ENCODING_FORMAT);
    }

    /**
     * 获取***手机号
     */
    public static String getPhoneNum(@NonNull String phone) {
        if (!isEmpty(phone)) {
            return phone.substring(0, 3) + "****" + phone.substring(7, 11);
        } else {
            return "";
        }
    }

    /***
     * 安全姓名
     */
    public static String safeName(String str) {
        if (!isEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length() - 1; i++) {
                sb.append("*");
            }
            return sb.append(str.substring(str.length() - 1, str.length())).toString();
        }
        return str;
    }

    /***
     * 安全身份证
     */
    public static String safecardNum(String str) {
        if (!isEmpty(str)) {
            String s = str.substring(str.length() - 1);
            String s_ = str.substring(0, 6);
            return s_ + "***********" + s;
        }
        return str;
    }

    /***
     * 判断一个字符串非空
     *
     * @param str String
     * @return true is null,false is not null.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals("") || str.length() == 0 || str.equals("null");
    }

    /***
     * 判断一个字符串非空
     *
     * @param str String
     * @return true is null,false is not null.
     */
    public static boolean isEmpty(Editable str) {
        return str == null || str.toString() == null || str.toString().equals("") || str.toString().length() == 0;
    }

    /**
     * 半角字符转换为全角字符
     *
     * @param input
     * @return FanLei
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 替换、过滤特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException FanLei
     */
    public static String StringFilter(String str) throws PatternSyntaxException {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 验证手机格式
     */
    public static String isMobileNO(String mobiles, String pattern) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186
         * 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        if (pattern == null) {
            pattern = RegEx.PHONENUMBER;
        }
        if (TextUtils.isEmpty(mobiles)) {
            return "手机号不能为空";
        } else {
            if (!mobiles.matches(pattern)) {
                return "手机号码不正确";
            }
        }
        return null;
    }

    /**
     * 检查字符串是否为电话号码的方法,并返回true or false的判断值
     */
    public static String isPhoneNumberValid(String phoneNumber, String pattern) {
        /** 可接受的电话格式有:
         * ^//(? : 可以使用 "(" 作为开头
         * (//d{3}): 紧接着三个数字
         * //)? : 可以使用")"接续
         * [- ]? : 在上述格式后可以使用具选择性的 "-".
         * (//d{3}) : 再紧接着三个数字
         * [- ]? : 可以使用具选择性的 "-" 接续.
         * (//d{5})$: 以五个数字结束.
         * 可以比较下列数字格式:
         * (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890
         */
        if (pattern == null) {
            pattern = RegEx.TELPHONE;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            return "请输入手机号";
        } else {
            if (!phoneNumber.matches(pattern)) {
                return "手机号不正确";
            }
        }
        return null;
    }

    /**
     * 验证用户名格式
     *
     * @param account
     * @param pattern FanLei
     */
    public static String isAccount(String account, String pattern) {
        if (pattern == null) {
            pattern = RegEx.ACCOUNT;
        }
        if (TextUtils.isEmpty(account)) {
            return "用户名不能为空";
        } else {
            if (!account.matches(pattern)) {
                return "用户名不正确";
            }
        }
        return null;
    }

    /**
     * 验证密码格式
     *
     * @param pattern FanLei
     */
    public static String isPassword(String pwd, String pattern) {
        if (pattern == null) {
            pattern = RegEx.ACCOUNT;
        }
        if (TextUtils.isEmpty(pwd)) {
            return "密码不能为空";
        } else {
            if (!pwd.matches(pattern)) {
                return "密码不正确";
            }
        }
        return null;
    }

    /**
     * 本地校验手机号码
     *
     * @return
     */
    public static boolean validPhone(Context act, String phone) {
        boolean isValid = true;
        if (!TextUtils.isEmpty(isMobileNO(phone, "[1]\\d{10}"))) {
            isValid = false;
            Toast.makeText(act, isMobileNO(phone, "[1]\\d{10}"), Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    /**
     * 本地校验密码
     *
     * @return
     */
    public static boolean validPwd(Context act, String pwd) {
        boolean isValid = true;
        if (!TextUtils.isEmpty(isPassword(pwd,
                "([0-9](?=[0-9]*?[a-zA-Z])\\w{5,})|([a-zA-Z](?=[a-zA-Z]*?[0-9])\\w{5,})"))) {
            isValid = false;
            Toast.makeText(
                    act,
                    isPassword(pwd, "([0-9](?=[0-9]*?[a-zA-Z])\\w{5,})|([a-zA-Z](?=[a-zA-Z]*?[0-9])\\w{5,})"),
                    Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0 || "null".equals(str);
    }

    /**
     * md5加密
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (isEmpty(string)) {
            return "";
        }
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();

    }

    /**
     * 手机号格式
     */
    public static String ToPhoneCod(String str) {
        str = str.replace(" ", "");
        int length = str.length();
        if (str != null && length > 3) {
            for (int i = 0; i < length / 4; i++) {
                int end = i > 0 ? (i + 1) * 5 - 2 : 3;
                if (end > length) {
                    continue;
                }
                String start = str.substring(0, end);
                String ends = str.substring(end);
                if (start.endsWith(" ") || ends.startsWith(" ")) {
                    continue;
                }
                str = start + " " + ends;
            }
        }
        return str.trim();
    }

    public static String ToPhoneCodeNum(String str) {

        str = str.replace(" ", "");
        return str;
    }


    /**
     * 转换银行卡形式
     */
    public static String ToBankCode(String str) {
        str = str.replace(" ", "");
        int length = str.length();
        if (str != null && length > 4) {
            for (int i = 0; i < length / 4; i++) {
                int end = i > 0 ? (i + 1) * 5 - 1 : 4;
                if (end > length) {
                    continue;
                }
                String start = str.substring(0, end);
                String ends = str.substring(end);
                if (start.endsWith(" ") || ends.startsWith(" ")) {
                    continue;
                }
                str = start + " " + ends;
                length = str.length();
            }
        }
        return str.trim();
    }

    /**
     * 银行卡号转换成数字
     */
    public static String ToBankCodeNum(String str) throws Exception {

        String code = ToBankCodeString(str);

        long codeL = Long.valueOf(code);

        return code;
    }

    /**
     * 银行卡号转换成数字
     */
    public static String ToBankCodeString(String str) throws NumberFormatException {

        String[] ss = str.split(" ");
        StringBuffer sb = new StringBuffer();
        if (ss != null && ss.length > 0) {
            for (int i = 0; i < ss.length; i++) {
                sb.append(ss[i].trim());
            }
        }
        String code = sb.toString();

        return code;
    }

/*********************************** 身份证验证开始 ****************************************/
    /**
     * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码， 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位） 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
     * 4、顺序码（第十五位至十七位） 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数） （1）十七位数字本体码加权求和公式 S =
     * Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和 Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5
     * 8 4 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     */

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    public static String IDCardValidate(String IDStr) throws ParseException {
        IDStr = IDStr.toLowerCase();
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "请输入真实身份证号";
//            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "请输入真实身份证号";
//            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "请输入真实身份证号";
//            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "请输入真实身份证号";
//                errorInfo = "身份证生日不在有效范围。";
                return errorInfo;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "请输入真实身份证号";
//            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "请输入真实身份证号";
//            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "请输入真实身份证号";
//            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "请输入真实身份证号";
//                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return errorInfo;
        }
        // =====================(end)=====================
        return errorInfo;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern =
                Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }


    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                /*|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS*/) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        if (name.length() < 2 || name.length() > 15) {
            res = false;
        }
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    public static String formatString(String str) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Float.parseFloat(str));
    }

    public static String formatNumber(double number, int decimals) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(decimals);
        nf.setMinimumFractionDigits(decimals);
        nf.setGroupingUsed(false);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        return nf.format(number);
    }

    public static String formatNumber(float number, int decimals) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(decimals);
        nf.setMinimumFractionDigits(decimals);
        nf.setGroupingUsed(false);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        return nf.format(number);
    }

    public static <T> boolean equalsObject(T t1, T t2) throws IllegalAccessException {

        if(t1 == null && t2==null){
            return true;
        }
        if((t1 == null && t2!=null) || (t2 == null && t1!=null)){
            return false;
        }

        Class<?> class1 = t1.getClass();

        Field[] fs = class1.getDeclaredFields();

        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); //设置些属性是可以访问的
            Object val1 = f.get(t1);//得到此属性的值
            Object val2 = f.get(t2);//得到此属性的值


            if(val1 == null && val2==null){
                continue;
            }
            if((val1 == null && val2!=null) || (val2 == null && val1!=null)){
                return false;
            }
            if (!val1.equals(val2)) {
                return false;
            }

            String type = f.getType().toString();//得到此属性的类型

            if (type.endsWith("String")) {
                if (val1 == null) {
                    val1 = "";
                }
                if (val2 == null) {
                    val2 = "";
                }

            }

        }


        return true;
    }
}
