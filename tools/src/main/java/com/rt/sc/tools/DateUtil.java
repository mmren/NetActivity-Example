package com.rt.sc.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{


    /**
     * 返回日期的天
     *
     * @param date Date
     * @return int
     */
    public static int getDay(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的年
     *
     * @param date Date
     * @return int
     */
    public static int getYear(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的月份，1-12
     *
     * @param date Date
     * @return int
     */
    public static int getMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回日期的小时，1-12
     *
     * @param date Date
     * @return int
     */
    public static int getHour(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR);
    }

    /**
     * 返回日期的小时，24小时
     *
     * @param date Date
     * @return int
     */
    public static int getHourAll(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回日期的分钟，1-12
     *
     * @param date Date
     * @return int
     */
    public static int getMinute(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回日期的秒，1-12
     *
     * @param date Date
     * @return int
     */
    public static int getSecond(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }


    /**
     * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
     *
     * @param date1 Date
     * @param date2 Date
     * @return long
     */
    public static long dayDiff(Date date1, Date date2)

    {
        long day = (date2.getTime() - date1.getTime()) / 86400000;
        day = day > 0 ? day : 0;
        return day;
    }


    /**
     * 计算两个日期相差的小时数，如果date2 > date1 返回正数，否则返回负数
     *
     * @param date1 Date
     * @param date2 Date
     * @return long
     */
    public static long hourDiff(Date date1, Date date2)
    {
        long hour = (date2.getTime() - date1.getTime()) % 86400000 / 3600000;
        hour = hour > 0 ? hour : 0;
        return hour;
    }

    /**
     * 计算两个日期相差的分钟数，如果date2 > date1 返回正数，否则返回负数
     *
     * @param date1 Date
     * @param date2 Date
     * @return long
     */
    public static long minuteDiff(Date date1, Date date2)
    {
        long minute = (date2.getTime() - date1.getTime()) % 3600000 / 60000;
        minute = minute > 0 ? minute : 0;
        return minute;
    }


    /**
     * 获取当前时间的后一天
     *
     * @throws
     */
    public static String getDateAfter1day()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取当前时间的后N天
     *
     * @throws
     */
    public static String getDateAfterdays(String days)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(days));
        Date date = calendar.getTime();
        return sdf.format(date);
    }


    /**
     * 把符合日期格式的字符串转换为日期类型
     */
    public static Date stringtoDate(String dateStr, String format)
    {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try
        {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e)
        {
            d = null;
        }
        return d;
    }

    /**
     * 把日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, String format)
    {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try
        {
            result = formater.format(date);
        } catch (Exception e)
        {
            // log.error(e);
        }
        return result;
    }

    /**
     * 获得日期字符串
     *
     * @return
     */
    public static String getDateString(long time, String s_year, String s_month, String s_data, String space, String s_hour, String s_minute, String s_second)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        StringBuffer sb = new StringBuffer();
        if (s_year != null)
        {
            sb.append(year).append(s_year);
        }
        if (s_month != null)
        {
            sb.append(month).append(s_month);
        }
        if (s_data != null)
        {
            sb.append(day).append(s_data);
        }

        if (space != null)
        {
            sb.append(space);
        }

        if (s_hour != null)
        {
            sb.append(hour).append(s_hour);
        }
        if (s_minute != null)
        {
            sb.append(minute).append(s_minute);
        }
        if (s_second != null)
        {
            sb.append(second).append(s_second);
        }

        return sb.toString();
    }
}
