package com.minxin.base.web.context;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.Collection;

/**
 * Created by todd on 2016/11/9.
 * @author todd
 */
public class ApplicationContextHolder implements ApplicationListener, DisposableBean {
    /**
     *
     */
    private static Logger LOGGER = LoggerFactory.getLogger(ApplicationContextHolder.class);
    /**
     *
     */
    private ApplicationContext applicationContext;

    /**
     *
     */
    public static void getContext() {
        Subject subject = SecurityUtils.getSubject();

        PrincipalCollection principals = subject.getPrincipals();
        LOGGER.info("" + principals.asList().size());

        Session session = subject.getSession();
        Collection collection = session.getAttributeKeys();
        LOGGER.info("" + collection.size());
    }

    @Override
    public void destroy() throws Exception {
        LOGGER.warn("======================= destroy");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        LOGGER.warn("======================= event = " + event.getClass());
    }
}
