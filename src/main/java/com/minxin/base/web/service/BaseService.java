package com.minxin.base.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by todd on 2016/11/10.
 * @author todd
 * @param <E>
 */
public class BaseService<E> {

    /**
     * 日志记录工具
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @return logger日志对象
     */
    public Logger getLogger() {
        return logger;
    }

}
