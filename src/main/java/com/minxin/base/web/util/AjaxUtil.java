package com.minxin.base.web.util;

import org.springframework.web.context.request.WebRequest;

/**
 * 判断请求是否是ajax请求
 *
 * @author todd
 */
public final class AjaxUtil {

    /**
     * 根据请求头判断是否是ajax请求
     * @param webRequest spring mvc中webRequest
     * @return 是否是ajax请求
     */
    public static boolean isAjaxRequest(WebRequest webRequest) {
        String requestedWith = webRequest.getHeader("X-Requested-With");
        return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
    }

    /**
     * 根据请求头判断是否是ajax upload请求
     * @param webRequest spring mvc中webRequest
     * @return 是否是ajax upload请求
     */
    public static boolean isAjaxUploadRequest(WebRequest webRequest) {
        return webRequest.getParameter("ajaxUpload") != null;
    }

    private AjaxUtil() {
    }

}
