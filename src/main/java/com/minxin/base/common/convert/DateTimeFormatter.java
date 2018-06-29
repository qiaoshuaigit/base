package com.minxin.base.common.convert;

import com.minxin.base.common.convert.annotation.DateTimeFormat.Style;
import org.springframework.format.Formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by todd on 2016/12/20.
 *
 * @author todd
 */
public class DateTimeFormatter implements Formatter<Date> {
    /**
     *
     */
    private static final Map<Style, String> DATE_PATTERNS;

    static {
        Map<Style, String> formats = new HashMap<>(5);
        formats.put(Style.DATE, "yyyy-MM-dd");
        formats.put(Style.TIME, "HH:mm:ss");
        formats.put(Style.DATE_TIME, "yyyy-MM-dd HH:mm:ss");
        formats.put(Style.DATE_TIME_SLASH, "yyyy/MM/dd HH:mm:ss");
        DATE_PATTERNS = Collections.unmodifiableMap(formats);
    }

    /**
     *
     */
    private Style style = Style.NONE;

    public DateTimeFormatter() {
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        if (text == null || "".equals(text)) {
            return null;
        }
        return getDateFormat().parse(text);
    }

    /**
     * @param object 日期对象
     * @param locale locale
     * @return format之后的结果
     */
    @Override
    public String print(Date object, Locale locale) {
        return getDateFormat().format(object);
    }

    /**
     * @return 生产dateformat对象
     */
    private DateFormat getDateFormat() {
        if (this.style != null && this.style != Style.NONE) {
            String pattern = DATE_PATTERNS.get(this.style);
            if (pattern == null) {
                throw new IllegalStateException("Unsupported Date format " + this.style);
            }
            return new SimpleDateFormat(pattern);
        }

        return DateFormat.getDateTimeInstance();
    }
}
