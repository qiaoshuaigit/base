package com.minxin.base.web.filter;

import com.minxin.base.web.exception.SessionExpirationException;
import org.pac4j.core.exception.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by todd on 2016/11/23.
 *
 * @author todd
 */
public class HandleSingleSignOutFilter implements Filter {
    /**
     * 日志输出工具
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 当HandleSingleSignOutFilter捕捉到异常时，如果是由于转换为session引起的异常，说明是缓存中session被单点登出时，
     * 被cas回调函数删除，而浏览器中的cookie仍然存在，需要在此处理掉，并回到登录页面
     *
     * @param request  请求
     * @param response 响应
     * @param chain    filterChain
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception exception) {
            String host = request.getRemoteHost();
            if (logger.isDebugEnabled()) {
                logger.info("============== in HandleSingleSignOutFilter ==============, remote host = " + host);
            }

            Throwable cause = exception.getCause();
            if (cause instanceof ClassCastException || cause instanceof SessionExpirationException || cause instanceof TechnicalException) {

                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;

                Cookie[] cookies = httpServletRequest.getCookies();

                if (cookies != null) {
                    if (logger.isDebugEnabled()) {
                        logger.info("============== cookies = " + cookies.length);
                    }
                    for (Cookie cookie : cookies) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("============== cookie = " + cookie.getName());
                        }

                        cookie.setMaxAge(0);
                        httpServletResponse.addCookie(cookie);
                    }
                }

                if (cause instanceof TechnicalException) {
                    String headerHost = httpServletRequest.getHeader("host");
                    response.getWriter().write("<script>window.top.location.assign('http://" + headerHost + "')</script>");
                } else {
                    response.getWriter().write("<script>window.top.location.reload();</script>");
                }
                return;
            } else {
                throw exception;
            }
        }
    }

    @Override
    public void destroy() {

    }
}
