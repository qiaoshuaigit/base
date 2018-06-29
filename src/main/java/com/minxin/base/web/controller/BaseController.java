package com.minxin.base.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import java.util.TimeZone;

/**
 * 基础controller类，抽取通用方法、属性
 * @author todd
 * @param <E>
 */
public abstract class BaseController<E> {
    /**
     * 返回到页面中的结果，成功
     */
    private static final String OK = "ok";
    /**
     * 返回到页面中的结果，数据库中已经存在相应的数据
     */
    private static final String EXISTS = "exists";
    /**
     * 返回到页面中的结果，增加失败
     */
    private static final String FALSE = "false";

    /**
     * session中错误信息属性的key
     */
    protected static final String GLOBAL_ERROR_INFO_KEY = "GLOBAL_ERROR_INFO_KEY";

    /**
     * session中信息属性的key
     */
    protected static final String GLOBAL_SUCCESS_INFO_KEY = "GLOBAL_SUCCESS_INFO_KEY";

    /**
     * 日志记录工具
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     */
    private static String CTX;

    /**
     * 设置时间格式为东8区
     */
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    /**
     *
     * @return logger日志对象
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * 添加Model消息
     *
     * @param model model
     * @param messages 绑定消息
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     *
     * @param addResult 新增MOdel成功与否的结果
     * @return 返回对应的返回到页面的结果
     */
    protected String getReturnMessage(int addResult) {
        if (addResult > 0) {
            return OK;
        } else if (addResult == -1) {
            return EXISTS;
        } else {
            return FALSE;
        }
    }


}
