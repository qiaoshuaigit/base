package com.minxin.base.common.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

/**
 * JsonUtil 工具类，将对象与json互相转换。
 *
 * @author todd
 */
public final class JsonUtil {
    /**
     * objectMapper 对象，定义为全局属性
     */
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * jsonFactory 对象，定义为全局属性
     */
    private static JsonFactory JSON_FACTORY = OBJECT_MAPPER.getFactory();

    static {
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 将object对象转换成JSON输出，null字段也进行输出
     *
     * @param object 要转换为json的对象
     * @return 返回转换后的json字符串
     */
    public static String toJSONString(Object object) {
        String result = "";
        StringWriter stringWriter = new StringWriter();
        try {
            JsonGenerator jsonGenerator = JSON_FACTORY.createGenerator(stringWriter);
            OBJECT_MAPPER.writeValue(jsonGenerator, object);

            result = stringWriter.toString();

            jsonGenerator.close();
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将Json 字符串转换为对应的类型
     *
     * @param string 要转换为Object对象的json字符串
     * @param clazz  相应的转换类型
     * @param <T>    类型
     * @return 返回转换后的对象
     */
    public static <T> T jsonStringToObject(String string, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(string, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
