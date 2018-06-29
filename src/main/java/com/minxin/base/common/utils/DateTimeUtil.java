package com.minxin.base.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author todd TODO 方法需要优化、测试
 */
public class DateTimeUtil extends DateUtils {
    /**
     *
     */
    public static final String DATE = "yyyy-MM-dd";

    /**
     *
     */
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     *
     */
    public static final String DATE_TIME_SLASH = "yyyy/MM/dd HH:mm:ss";

    /**
     *
     */
    public static final String TIME = "HH:mm:ss";

    /**
     * get the current time of the system
     *
     * @return Timestamp
     */
    public static Timestamp getNowTimestamp() {
        Date d = new Date();
        Timestamp numTime = new Timestamp(d.getTime());
        return numTime;
    }

    /**
     * get the current time of the system , return java.util.Date class
     *
     * @return java.util.Date
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * get the current date of the system and format it with JAVA_DATE_FORMATTER
     * as a String
     *
     * @return String
     */
    public static String getNowDateString() {
        return dateToString(getNowDate(), DATE_TIME);
    }

    /**
     * get the current time of the system and format it with JAVA_TIME_FORMATTER
     * as a String
     *
     * @return String
     */
    public static String getNowStrTime() {
        return dateToString(getNowDate(), DATE_TIME);
    }

    /**
     * format a Timestamp with a format string, if Timestamp is null then return
     * null
     *
     * @param date    Timestamp
     * @param pattern a format string
     * @return String
     */
    public static String timestampToString(Timestamp date, String pattern) {
        String strTemp = null;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            strTemp = formatter.format(date);
        }
        return strTemp;
    }

    /**
     * format a Timestamp with a format string as String, if Timestamp is null
     * then return the default string
     *
     * @param date       Timestamp
     * @param pattern    a format string
     * @param strDefault a default string
     * @return String
     */
    public static String timestampToString(Timestamp date, String pattern,
                                           String strDefault) {
        String strTemp = strDefault;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            strTemp = formatter.format(date);
        }
        return strTemp;
    }

    /**
     * format a Date with a formated string as String , if Date is null then
     * return null
     *
     * @param date    java.util.Date
     * @param pattern a formated string
     * @return String
     */
    public static String dateToString(Date date, String pattern) {
        String strTemp = null;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            strTemp = formatter.format(date);
        }
        return strTemp;
    }

    /**
     * format a java.util.Date with a format string as String, if java.util.Date
     * is null then return the default string
     *
     * @param date       java.util.Date
     * @param pattern    a formated string
     * @param strDefault a default string
     * @return 转换为string的日期
     */
    public static String dateToString(Date date, String pattern,
                                      String strDefault) {
        String strTemp = strDefault;
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            strTemp = formatter.format(date);
        }
        return strTemp;
    }

    /**
     * change a String to Timestamp with a formated String
     *
     * @param strDate String
     * @param pattern a formated string
     * @return Timestamp
     */
    public static Timestamp stringToTimestamp(String strDate, String pattern) {
        if (strDate != null && !strDate.equals("")) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                Date d = formatter.parse(strDate);
                Timestamp numTime = new Timestamp(d.getTime());
                return numTime;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * change a String to java.sql.date with a formated String
     *
     * @param strDate String
     * @param format  a formated string
     * @return java.util.Date
     */
    public static Date stringToDate(String strDate, String format) {
        if (strDate != null && !strDate.equals("")) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                Date d = formatter.parse(strDate);
                return d;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * validate whether a String meet to the formater 'MM/dd/YYYY' if yes return
     * true, else return false
     *
     * @param strExp String
     * @return boolean
     */
    public static boolean isDate(String strExp) {
        if (strExp.length() != 10) {
            return false;
        }

        try {
            Calendar cal = new GregorianCalendar();
            cal.setLenient(false);
            cal.set(Integer.parseInt(strExp.substring(6, 10)), Integer
                    .parseInt(strExp.substring(3, 5)) - 1, Integer
                    .parseInt(strExp.substring(0, 2)));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * validate whether the three integer coulde be consised of a appropriate
     * date if yes return true, else return false
     *
     * @param intYear  int
     * @param intMonth int
     * @param intDay   int
     * @return boolean
     */
    public static boolean isDate(int intYear, int intMonth, int intDay) {
        try {
            Calendar cal = new GregorianCalendar();
            cal.setLenient(false);
            cal.set(intYear, intMonth, intDay);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * get a Date according to the given data (year, month, day, hour, minute)
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   小时
     * @param minute 分钟
     * @return Date 年月日小时分钟对应的日期
     */
    public static final Date getDate(int year, int month, int day, int hour,
                                     int minute) {
        Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month),
                day, hour, minute);
        return cal.getTime();
    }

    /**
     * get a Date according to the given data (year, month, day)
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 年月日对应的日期
     */
    public static final Date getDate(int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month),
                day);
        return cal.getTime();
    }

    /**
     * chage a Date to Timestamp
     *
     * @param date 日期
     * @return 转换为timestamp的数据
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * chage a Calendar to Timestamp
     *
     * @param date calendar日期
     * @return 对应的timestamp
     */
    public static Timestamp getTimestamp(Calendar date) {
        return new Timestamp(date.getTime().getTime());
    }

    /**
     * change a String to Timestamp with a formated String
     *
     * @param date    String
     * @param pattern a formated string
     * @return Timestamp
     */
    public static Timestamp getTimestamp(String date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            Timestamp timestamp = new Timestamp(formatter.parse(date).getTime());
            return timestamp;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * add several days to a given date
     *
     * @param originalDate 起始日期
     * @param days         增加的天数
     * @return Date 增加后的日期
     */
    public static final Date addDays(Date originalDate, int days) {
        long msPerDay = 0x5265c00L;
        long msTarget = originalDate.getTime();
        long msSum = msTarget + msPerDay * (long) days;
        Date result = new Date();
        result.setTime(msSum);
        return result;
    }

    /**
     * @param source a date
     * @param field  a time type, the constant of Calendar should be use eg.
     *               Calendar.YEAR,Calendar.MONTH,Calendar.WEEK
     * @param amount a interval, could be a positive or negtive integer
     * @return a formated string like 2005-07-06
     */
    public static final String addCalendar(final Date source, int field,
                                           int amount) {

        Calendar greCal = new GregorianCalendar();
        if (source != null) {
            greCal.setTime(source);
        }
        greCal.add(field, amount);
        SimpleDateFormat f = new SimpleDateFormat(
                DATE_TIME);
        return (f.format(greCal.getTime()));
    }

    /**
     * @param source a date
     * @param field  a time type, the constant of Calendar should be use eg.
     *               Calendar.YEAR,Calendar.MONTH,Calendar.WEEK
     * @param amount a interval, could be a positive or negtive integer
     * @return a date
     */
    public static final Date addCalendar2(final Date source, int field,
                                          int amount) {

        Calendar greCal = new GregorianCalendar();
        if (source != null) {
            greCal.setTime(source);
        }
        greCal.add(field, amount);
        return greCal.getTime();
    }

    /**
     * get the interal of two dates
     *
     * @param first  一个日期
     * @param second 第二个日期
     * @return the interal of two dates
     */
    public static int dayDiff(Date first, Date second) {
        long msPerDay = 0x5265c00L;
        long diff = first.getTime() / msPerDay - second.getTime() / msPerDay;
        Long convertLong = Long.valueOf(diff);
        return convertLong.intValue();
    }

    /**
     * get the year part of a date
     *
     * @param date 日期
     * @return date对应的年份
     */
    public static int getYear(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(1);
    }

    /**
     * get the month part of a date
     *
     * @param date 日期
     * @return date对应的月
     */
    public static int getMonth(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int calendarMonth = cal.get(2);
        return calendarMonthToInt(calendarMonth);
    }

    /**
     * get the day part of a date
     *
     * @param date 日期
     * @return 对应的日
     */
    public static int getDay(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(5);
    }

    /**
     * get the hour part of a date
     *
     * @param date 日期
     * @return date对应的小时
     */
    public static int getHour(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(11);
    }

    /**
     * get the minute part of a date
     *
     * @param date 日期
     * @return date对应的分钟
     */
    public static int getMinute(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(12);
    }

    /**
     * get the days of a month whitch the date is in
     *
     * @param date 日期
     * @return date对应当月的第几天
     */
    public static int getDayOfMonth(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * change the calendarMonth to integer
     *
     * @param calendarMonth 月份的索引
     * @return 真实的月，如0->对应1月
     */
    private static int calendarMonthToInt(int calendarMonth) {
        if (calendarMonth <= 11 && calendarMonth >= 0) {
            return calendarMonth + 1;
        } else {
            return 1;
        }
    }

    /**
     * format a Date with a formatted string as String
     *
     * @param date    Date
     * @param pattern a formatted string
     * @return 根据日期格式，将日期格式化后的日期字符串
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * change the integer to calendarMonth
     *
     * @param month 月份的数字
     * @return date中月份的值，如1月，在date中month字段对应为0
     */
    private static int intToCalendarMonth(int month) {
        if (month >= 1 && month <= 12) {
            return month - 1;
        } else {
            return 0;
        }
    }

    /**
     * compare two dates if anotherDate is after firstDate return 1 if anotherDate if befor
     * firstDate return -1 if they are equal return 0
     *
     * @param firstDate   第一个日期
     * @param anotherDate 第二个日期
     * @return 比第二个日期是否比第一个日期大，大于返回1，等于返回0，小于返回-1
     */
    public static int compareTwoDate(String firstDate, String anotherDate) {

        if ((firstDate == null || "".equals(firstDate))
                && (anotherDate == null || "".equals(anotherDate))) {
            return 0;
        } else if (firstDate == null || "".equals(firstDate)) {
            return -1;
        } else if (anotherDate == null || "".equals(anotherDate)) {
            return 1;
        }

        int result = 0;
        Date date = null;
        Date dateAnother = null;
        SimpleDateFormat dateFomat = null;

        try {
            dateFomat = new SimpleDateFormat(DATE_TIME);

            date = dateFomat.parse(firstDate);
            dateAnother = dateFomat.parse(anotherDate);

            result = date.compareTo(dateAnother);
        } catch (ParseException pe) {
            pe.printStackTrace();
            System.err.println("Exception in compareTwoDate.");
            result = 1;
        }

        return result;
    }

    /**
     * 将java.util.Date 转为 java.sql.Date
     *
     * @param date 日期类型的date
     * @return Date java.sql.Date类型的date
     */
    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 获取档期日期之后days天数的日期
     *
     * @param days 天数
     * @return Date days天后的日期
     */
    public static Date addDay(int days) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, days);
        date = calendar.getTime();
        return date;
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     *
     * @return yyyy-MM-dd格式的日期字符串
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param pattern 日期格式
     * @return 格式化后的日期字符串
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param date    日期
     * @param pattern 格式化表达式
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     *
     * @return HH:mm:ss格式的日期字符串
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     *
     * @return yyyy-MM-dd HH:mm:ss格式的日期字符串
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     *
     * @return yyyy年份字符串
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     *
     * @return MM月份字符串
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     *
     * @return dd天字符串
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     *
     * @return 星期字符串
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 与当前日期相比差的毫秒数
     *
     * @param date 日期
     * @return 与当前日期相比差的毫秒数
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException 日期转换异常
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }
}
