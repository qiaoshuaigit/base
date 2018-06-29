package com.minxin.base.web.util;

import com.minxin.base.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Http 工具类. TODO 方法需要测试
 *
 * @author todd
 */
public class HttpUtil {
    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    /**
     *
     * @param request HttpServletRequest
     * @return 从request中获取到的远程ip
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtil.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (StringUtil.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (StringUtil.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }


    /**
     * Convert the string which contains html Tag to common string.
     *
     * @param sourcestr html文本
     * @return 转换为普通文本
     */
    public static String escapeHTMLTags(String sourcestr) {
        if (sourcestr == null) {
            return "";
        }

        int strlen;
        String restring = "";
        String destr = "";
        strlen = sourcestr.length();

        for (int i = 0; i < strlen; i++) {
            char ch = sourcestr.charAt(i);

            switch (ch) {
                case '<':
                    destr = "&lt;";
                    break;
                case '>':
                    destr = "&gt;";
                    break;
                case '\"':
                    destr = "&quot;";
                    break;
                case '&':
                    destr = "&amp;";
                    break;
                case 13:
                    destr = "<br>";
                    break;
                case 32:
                    destr = "&nbsp;";
                    break;
                default:
                    destr = "" + ch;
                    break;
            }
            restring = restring + destr;
        }
        return "" + restring;
    }
}
