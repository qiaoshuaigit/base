package com.minxin.base.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;

/**
 * Created by todd on 2016/11/15.
 *
 * @author todd
 */
public class SerializeUtil {
    /**
     * 日志工具类
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SerializeUtil.class);
    /**
     * 将object序列化
     * @param object 可Serializable的对象
     * @return 序列化后的byte[]
     */
    public static byte[] serialize(Object object) {
        byte[] result = new byte[0];

        if (object == null) {
            return result;
        }

        if (!(object instanceof Serializable)) {
            LOGGER.error(SerializeUtil.class.getSimpleName() + " requires a Serializable object " +
                    "but received an object of type [" + object.getClass().getName() + "]");
            throw new IllegalArgumentException(SerializeUtil.class.getSimpleName() + " requires a Serializable object " +
                    "but received an object of type [" + object.getClass().getName() + "]");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(object);
            outputStream.flush();
            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Error occurred while  deserializing object, IOException has been thrown");
            e.printStackTrace();
        }


        return result;
    }

    /**
     * 将输入的byte[]反序列化
     * @param input 输入的byte[]对象
     * @return 反序列化后的对象
     */
    public static Object deserialize(byte[] input) {
        Object object = new Object();

        if (input == null || input.length == 0) {
            return object;
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
        try {
            ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
            object = inputStream.readObject();
        } catch (IOException e) {
            LOGGER.error("Error occurred while  deserializing object, IOException has been thrown");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Error occurred while  deserializing object, ClassNotFoundException has been thrown");
            e.printStackTrace();
        }

        return object;
    }
}
