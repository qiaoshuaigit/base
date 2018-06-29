package com.minxin.base.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 *
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {
    /**
     * 替换掉HTML标签方法
     * @param html 输入的html文本
     * @return 返回去掉标签后的文本
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return 缩略的字符串
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : str.toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Convert the string (yes or no, 1 or 0, y or n) to corresponding value:
     * true or false.
     *
     * @param theString yes or no, 1 or 0, y or n
     * @return true or false.
     */
    public static boolean toBoolean(String theString) {
        if (theString == null) {
            return false;
        }

        theString = theString.trim();
        if (theString.equalsIgnoreCase("y")
                || theString.equalsIgnoreCase("yes")
                || theString.equalsIgnoreCase("true")
                || theString.equalsIgnoreCase("1")) {
            return true;
        }
        return false;
    }

    /**
     * 转换为Double类型
     * @param val 可转换为double的对象
     * @return 转换后的double数据
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * transform string to double
     *
     * @param src 可转换为double的字符串
     * @param defaultVal if conversion fail,return this value
     * @return 转换后的double数据
     */
    public static double toDouble(String src, double defaultVal) {
        double iResult;
        try {
            iResult = Double.parseDouble(src);
        } catch (Exception e) {
            iResult = defaultVal;
        }
        return iResult;

    }

    /**
     * 转换为Float类型
     * @param val 可转换为float的对象
     * @return 转换后的float数据
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     * @param val 可转换为long的对象
     * @return 转换后的long数据
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     * @param val 可转换为Integer的对象
     * @return 转换后的integer数据
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * 将字符串转换为int
     *
     * @param src 数字字符串
     * @return 转换后的int数
     */
    public static int toInt(String src) {
        return toInt(src, 0);
    }

    /**
     * transform string to integer
     *
     * @param src 数字字符串
     * @param defaultVal if conversion fail,return this value
     * @return 转换后的int
     */
    public static int toInt(String src, int defaultVal) {
        int iResult;
        try {
            iResult = Integer.parseInt(src);
        } catch (Exception e) {
            iResult = defaultVal;
        }
        return iResult;

    }

    /**
     * 将字符串类型的Object对象转换为String
     * @param string Object对象的String
     * @return 转换后的String
     */
    public static String objectToString(Object string) {
        if (string != null && string instanceof String) {
            return (String) string;
        } else {
            return "";
        }
    }

    /**
     * 删除字符串后的“，”
     * @param str 输入的字符串
     * @return 删除“，”后的字符串
     */
    public static String trimLastComma(String str) {
        if (null == str) {
            return str;
        }
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * <b> @Title:</b> mergeStringsBySeparator<br/>
     * <b> @Description:</b>(把字符串数组转换成公式形态字符串)<br/>
     * <b> @param str		前缀
     * <b> @param strings	数组
     * <b> @param sep		分隔符
     * <b> @return </b><br/>
     * <b> @Create：</b>Liuzhen<br/>
     * <b> @Create_Date：</b>2016-9-29 下午5:33:43<br/>
     */
    public static String mergeStringsBySeparator(String str, String[] strings, String sep) {
        String result = str + strings[0];
        for (int i = 1; i < strings.length; i++) {
            result = result + sep + str + strings[i];
        }
        return result;
    }

    /**
     * <b> @Title:</b> join<br/>
     * <b> @Description:</b>(把字符串数组转成集合形态字符串)<br/>
     * <b> @param arr		数组
     * <b> @param beginStr	以此开始
     * <b> @param lastStr	以此结束
     * <b> @param join		以此分隔
     * <b> @return </b><br/>
     * <b> @Create：</b>Liuzhen<br/>
     * <b> @Create_Date：</b>2016-9-29 下午5:31:35<br/>
     */
    public static String join(String[] arr, String beginStr, String lastStr, String join) {
        String result = "";
        boolean flag = true;
        for (String s : arr) {
            if (isNotEmpty(s)) {
                if (flag) {
                    result = beginStr + s;
                    flag = false;
                } else {
                    result = result + join + s;
                }
            }
        }
        if (!flag) {
            result += lastStr;
        }
        return result;
    }

    /**
     * Unite the strings with appointed list separator to one string.
     *
     * @param array String array.
     * @param delim List separator,while it is null,replace it with "".
     * @return String which has been united.
     * @since 0.4
     */
    public static String combineStringArray(String[] array, String delim) {
        int length = array.length - 1;
        if (delim == null) {
            delim = "";
        }
        StringBuffer result = new StringBuffer(length * 8);
        for (int i = 0; i < length; i++) {
            result.append(array[i]);
            result.append(delim);
        }
        result.append(array[length]);
        return result.toString();
    }
}
