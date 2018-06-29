package com.minxin.base.common.convert;

import com.minxin.base.common.convert.annotation.DateTimeFormat;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by todd on 2016/12/20.
 * @author todd
 */
public class DateTimeFormatterFactory implements AnnotationFormatterFactory<DateTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<Class<?>>(1, 1);
        fieldTypes.add(String.class);
        return fieldTypes;
    }

    @Override
    public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
        return getFormatter(annotation);
    }

    @Override
    public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
        return null;
    }

    /**
     *
     * @param annotation 日期时间格式注解
     * @return 符合annotation条件的formatter
     */
    protected Formatter<Date> getFormatter(DateTimeFormat annotation) {
        DateTimeFormatter formatter = new DateTimeFormatter();
        formatter.setStyle(annotation.style());
        return formatter;
    }
}
