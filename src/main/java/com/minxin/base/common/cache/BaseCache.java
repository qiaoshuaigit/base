package com.minxin.base.common.cache;

import com.minxin.base.common.utils.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

/**
 * 通过CacheUtils类来实现shiro中cache manager中获取cache的目的
 * Created by todd on 2016/11/15.
 *
 * @param <K> 缓存key
 * @param <V> 缓存value
 * @author todd
 */
public class BaseCache<K, V> implements Cache<K, V> {
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 用于shiro的cache的名字
     */
    private String name;

    /**
     * redis cache 工具类
     */
    private RedisCacheManager redisCacheManager;

    BaseCache(RedisCacheManager redisCacheManager, String name) {
        if (redisCacheManager == null) {
            throw new IllegalArgumentException("RedisCacheManager cannot be null.");
        }

        this.redisCacheManager = redisCacheManager;
        this.name = name;
    }

    public RedisCacheManager getRedisCacheManager() {
        return redisCacheManager;
    }

    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Override
    public V get(K key) throws CacheException {
        if (key == null) {
            return null;
        }
        byte[] rawData = redisCacheManager.get(SerializeUtil.serialize(key));
        V value = null;
        if (rawData != null) {
            value = (V) SerializeUtil.deserialize(rawData);
        }
        return value;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        String stats = redisCacheManager.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
        return (V) stats;
    }

    /**
     * 保存数据到缓存中，并设置过期时间
     * @param key key
     * @param value value
     * @param expire expire time,in seconds
     * @return cache status
     * @throws CacheException cacheException
     */
    public V put(K key, V value, int expire) throws CacheException {
        String stats = redisCacheManager.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value), expire);
        return (V) stats;
    }

    @Override
    public V remove(K key) throws CacheException {
        Long result = redisCacheManager.del(SerializeUtil.serialize(key));
        return (V) result;
    }

    @Override
    public void clear() throws CacheException {
        redisCacheManager.flush();
    }

    @Override
    public int size() {
        Long cacheSize = redisCacheManager.size();
        if (cacheSize != null) {
            return cacheSize.intValue();
        }
        return 0;
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) redisCacheManager.keys();
    }

    @Override
    public Collection<V> values() {
        return redisCacheManager.values();
    }
}
