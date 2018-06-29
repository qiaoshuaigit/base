package com.minxin.base.common.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by todd on 2016/11/15.
 *
 * @author todd
 */
public final class CacheUtils {
    /**
     *
     */
    private static JedisPool JEDIS_POOL;

    private CacheUtils() {
    }

    /**
     * 在配置文件中通过静态setter方法的方式注入JedisPool对象
     *
     * @param jedisPool redis池
     */
    public static void setJedisPool(JedisPool jedisPool) {
        JEDIS_POOL = jedisPool;
    }

    /**
     * 向缓存中存入数据,key与value都是可序列化的非String类型的Object
     * 若Key,Value均为String类型，请参考String set(String key, String value)方法
     *
     * @param key   缓存中对象的key
     * @param value key对应的value
     * @return Status code reply
     */
    public static String set(Object key, Object value) {
        Jedis jedis = JEDIS_POOL.getResource();
        String statusCode = jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
        jedis.close();
        return statusCode;
    }

    /**
     * 向缓存中存入数据,key与value都是可序列化的Object对象
     *
     * @param key     缓存中对象的key
     * @param value   key对应的value
     * @param seconds 过期时间（秒）
     * @return Status code reply
     */
    public static String set(Object key, Object value, int seconds) {
        Jedis jedis = JEDIS_POOL.getResource();
        String statusCode = jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
        jedis.expire(SerializeUtil.serialize(key), seconds);
        jedis.close();
        return statusCode;
    }

    /**
     * 向缓存中存入数据,key与value都是可序列化的Object对象
     *
     * @param key     缓存中对象的key
     * @param value   key对应的value
     * @param unixTime 过期时间（从January 1 1970开始的某个时间对应的毫秒数）
     * @return Status code reply
     */
    public static String set(Object key, Object value, long unixTime) {
        Jedis jedis = JEDIS_POOL.getResource();
        String statusCode = jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
        jedis.expireAt(SerializeUtil.serialize(key), unixTime);
        jedis.close();
        return statusCode;
    }

    /**
     * 从缓存中查询数据，非String,String类型，若Key,Value均为String类型，请参考string get(String key)方法
     *
     * @param key 缓存中对象的key
     * @return 根据key获取到的value
     */
    public static Object get(Object key) {
        Jedis jedis = JEDIS_POOL.getResource();
        byte[] byteValue = jedis.get(SerializeUtil.serialize(key));
        Object value = SerializeUtil.deserialize(byteValue);
        jedis.close();
        return value;
    }

    /**
     * 判断缓存中是否存在相应数据
     *
     * @param key 要查询的key
     * @return 是否存在
     */
    public static Boolean exists(Object key) {
        Jedis jedis = JEDIS_POOL.getResource();
        Boolean exists = jedis.exists(SerializeUtil.serialize(key));
        jedis.close();
        return exists;
    }

    /**
     * 从缓存中删除数据
     *
     * @param key 要删除的数据的key
     * @return 删除的记录数
     */
    public static Long del(Object key) {
        Jedis jedis = JEDIS_POOL.getResource();
        Long result = jedis.del(SerializeUtil.serialize(key));
        jedis.close();
        return result;
    }

    /**
     * Return the number of keys in the currently selected cache database.
     *
     * @return the number of keys
     */
    public static Long size() {
        Jedis jedis = JEDIS_POOL.getResource();
        Long size = jedis.dbSize();
        jedis.close();
        return size;
    }

    /**
     * 返回换成中所有的keys,Object类型
     *
     * @return keys set
     */
    public static Set<Object> keys() {
        Jedis jedis = JEDIS_POOL.getResource();
        Set<byte[]> byteKeys = jedis.keys("*".getBytes());
        jedis.close();
        Set<Object> keys = new HashSet<>();

        if (byteKeys != null && byteKeys.size() > 0) {
            Iterator<byte[]> iterator = byteKeys.iterator();
            while (iterator.hasNext()) {
                keys.add(SerializeUtil.deserialize(iterator.next()));
            }
        }

        return keys;
    }

    /**
     * 返回缓存中所有的value
     *
     * @return 缓存value组成的list
     */
    public static Collection values() {
        Jedis jedis = JEDIS_POOL.getResource();

        Set<byte[]> byteKeys = jedis.keys("*".getBytes());
        List<Object> result = new ArrayList<>();

        if (byteKeys != null && byteKeys.size() > 0) {
            Iterator<byte[]> iterator = byteKeys.iterator();
            while (iterator.hasNext()) {
                byte[] byteValue = jedis.get(iterator.next());
                result.add(SerializeUtil.deserialize(byteValue));
            }
        }

        jedis.close();

        return Collections.unmodifiableList(result);
    }

    /**
     * 清空当前缓存中的数据
     *
     * @return flush Status code reply
     */
    public static String flush() {
        Jedis jedis = JEDIS_POOL.getResource();
        String result = jedis.flushDB();
        jedis.close();
        return result;
    }
}
