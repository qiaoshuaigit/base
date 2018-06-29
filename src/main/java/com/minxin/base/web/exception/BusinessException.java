package com.minxin.base.web.exception;

/**
 * 业务异常
 * @author todd
 */
@SuppressWarnings("serial")
public class BusinessException extends Exception {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
}
