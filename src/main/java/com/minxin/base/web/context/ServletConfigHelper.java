package com.minxin.base.web.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.web.context.ServletConfigAware;

import javax.servlet.ServletConfig;

/**
 * Created by todd on 2016/11/9.
 *
 * @author todd
 */
public class ServletConfigHelper implements ServletConfigAware, DisposableBean {
    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * servletConfig
     */
    private static ServletConfig SERVLETCONFIG;

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        if (servletConfig == null) {
            SERVLETCONFIG = servletConfig;
        }

        logger.info("======================ServletConfigAware");
    }

    /**
     * 获取
     */
    public static void getContextPath() {
        SERVLETCONFIG.getServletContext().getContextPath();
    }

    @Override
    public void destroy() throws Exception {

    }
}
