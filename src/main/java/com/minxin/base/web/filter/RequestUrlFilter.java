package com.minxin.base.web.filter;

import com.minxin.base.common.utils.DateTimeUtil;
import com.minxin.base.web.security.SecurityHelper;
import com.minxin.base.web.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;

/**
 * 记录用户请求
 *
 * Created by todd on 2017/4/12.
 *
 * @author todd
 */
public class RequestUrlFilter implements Filter {
    /**
     * 日志输出工具
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            logger.info("Audit trail record |+| [{\"user\":\"{}\", \"host\":\"{}\", \"url\":\"{}\", \"time\":\"{}\", \"from\":\"{}\"}]",
                    SecurityHelper.getCurrentUser().getName(), httpServletRequest.getHeader("host"), httpServletRequest.getRequestURI(),
                    DateTimeUtil.dateToString(Calendar.getInstance().getTime(), DateTimeUtil.DATE_TIME),
                    HttpUtil.getRemoteAddr(httpServletRequest));
        } else {
            logger.info("Audit trail record |+| [{\"user\":\"{}\", \"host\":\"{}\", \"url\":\"{}\", \"time\":\"{}\", \"from\":\"{}\"}]",
                    SecurityHelper.getCurrentUser().getName(), request.getServletContext(),
                    DateTimeUtil.dateToString(Calendar.getInstance().getTime(), DateTimeUtil.DATE_TIME),
                    (request.getRemoteAddr() + "_" + request.getRemoteHost()));
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
