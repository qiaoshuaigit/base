package com.minxin.base.web.exception;

/**
 * Created by todd on 2017/3/20.
 * @author todd
 */
public class SessionExpirationException extends RuntimeException {
    /**
     * Constructs a <code>SessionExpirationException</code> with no detail message.
     */
    public SessionExpirationException() {
        super();
    }

    /**
     * Constructs a <code>SessionExpirationException</code> with the specified
     * detail message.
     *
     * @param message the detail message.
     */
    public SessionExpirationException(String message) {
        super(message);
    }
}
