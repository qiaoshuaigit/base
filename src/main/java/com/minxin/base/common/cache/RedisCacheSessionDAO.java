package com.minxin.base.common.cache;

import com.minxin.base.web.exception.SessionExpirationException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;

/**
 * Created by todd on 2017/5/17.
 *
 * @author todd
 */
public class RedisCacheSessionDAO extends EnterpriseCacheSessionDAO {

    /**
     * 保存session到缓存中
     *
     * @param session   session
     * @param sessionId sessionId
     * @param cache     cache
     */
    @Override
    protected void cache(Session session, Serializable sessionId, Cache<Serializable, Session> cache) {
        if (cache instanceof BaseCache) {
            BaseCache baseCache = (BaseCache) cache;
            if (session != null) {
                long timeout = session.getTimeout();
                baseCache.put(sessionId, session, new Long(timeout / 1000L).intValue());
            }
        }
    }

    /**
     * 读取session,在父类CachingSessionDAO中，已经执行了从缓存中查询session，找不到的话，才会执行本方法，
     * 所以默认抛出session超时的异常，在HandleSingleSignOutFilter类中刷新父页面。
     *
     * @param sessionId sessionId
     * @return 缓存中的session
     */
    protected Session doReadSession(Serializable sessionId) {
        throw new SessionExpirationException("session has expired.");
//        return null; //should never execute because this implementation relies on parent class to access cache, which
        //is where all sessions reside - it is the cache implementation that determines if the
        //cache is memory only or disk-persistent, etc.
    }
}
