package com.minxin.base.common.convert.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by todd on 2016/12/20.
 * @author todd
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeFormat {

    Style style() default Style.NONE;

    String pattern() default "";

    /**
     * Style类型
     */
    enum Style {
        /**
         * The most common ISO Date Format {@code yyyy-MM-dd},
         * e.g. "2000-10-31".
         */
        DATE,

        /**
         * The most common ISO Time Format {@code HH:mm:ss},
         * e.g. "01:30:00".
         */
        TIME,

        /**
         * 日期时间格式,yyyy-MM-dd HH:mm:ss
         */
        DATE_TIME,

        /**
         * 日期时间格式,2016/12/20 00:00:00
         */
        DATE_TIME_SLASH,

        /**
         * Indicates that no format pattern should be applied.
         */
        NONE
    }
}
