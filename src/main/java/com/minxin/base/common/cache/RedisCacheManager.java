package com.minxin.base.common.cache;

import com.minxin.base.common.utils.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by todd on 2017/3/2.
 *
 * @author todd
 */
public class RedisCacheManager {

    /**
     *
     */
    private String host = "127.0.0.1";

    /**
     *
     */
    private int port = 6379;

    /**
     *
     */
    private int timeout = 0;

    /**
     *
     */
    private int expire = 0;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private static JedisPool JEDIS_POOL = null;

    /**
     *
     */
    private JedisPoolConfig jedisPoolConfig;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JedisPoolConfig getJedisPoolConfig() {
        return jedisPoolConfig;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public RedisCacheManager(JedisPool jedisPool) {
        JEDIS_POOL = jedisPool;
    }

    /**
     * 向缓存中存入数据,key与value都是序列化后的对象
     *
     * @param key   缓存中对象的key
     * @param value key对应的value
     * @return Status code reply
     */
    public String set(byte[] key, byte[] value) {
        Jedis jedis = JEDIS_POOL.getResource();
        String statusCode = jedis.set(key, value);
        if (this.expire != 0) {
            jedis.expire(key, this.expire);
        }
        jedis.close();
        return statusCode;
    }

    /**
     * 向缓存中存入数据,key与value都是序列化后的对象
     *
     * @param key    缓存中对象的key
     * @param value  key对应的value
     * @param expire 缓存中对象的过期时间
     * @return Status code reply
     */
    public String set(byte[] key, byte[] value, int expire) {
        Jedis jedis = JEDIS_POOL.getResource();
        String statusCode = jedis.set(key, value);
        if (expire != 0) {
            jedis.expire(key, expire);
        }
        jedis.close();
        return statusCode;
    }

    /**
     * 从缓存中查询数据
     *
     * @param key 缓存中对象的key
     * @return 根据key获取到的value
     */
    public byte[] get(byte[] key) {
        Jedis jedis = JEDIS_POOL.getResource();
        byte[] byteValue = jedis.get(key);
        jedis.close();
        return byteValue;
    }

    /**
     * 判断缓存中是否存在相应数据
     *
     * @param key 要查询的key
     * @return 是否存在
     */
    public Boolean exists(byte[] key) {
        Jedis jedis = JEDIS_POOL.getResource();
        Boolean exists = jedis.exists(key);
        jedis.close();
        return exists;
    }

    /**
     * 从缓存中删除数据
     *
     * @param key 要删除的数据的key
     * @return 删除的记录数
     */
    public Long del(byte[] key) {
        Jedis jedis = JEDIS_POOL.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    /**
     * Return the number of keys in the currently selected cache database.
     *
     * @return the number of keys
     */
    public Long size() {
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
    public Set<Object> keys() {
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
    public Collection values() {
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
     * 情况当前缓存中的数据
     *
     * @return flush Status code reply
     */
    public String flush() {
        Jedis jedis = JEDIS_POOL.getResource();
        String result = jedis.flushDB();
        jedis.close();
        return result;
    }
}
