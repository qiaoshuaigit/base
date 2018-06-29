package com.minxin.base.web.listener;

import com.minxin.base.common.utils.JsonUtil;
import com.minxin.base.web.exception.SessionExpirationException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by todd on 2016/12/13.
 *
 * @author todd
 */
public class WebSessionListener implements SessionListener {
    /**
     * 日志输出工具
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onStart(Session session) {
        if (logger.isDebugEnabled()) {
            logger.debug("============== on session onStart ==============");
        }
    }

    @Override
    public void onStop(Session session) {
        if (logger.isDebugEnabled()) {
            logger.debug("============== on session stop ==============");
        }
        SecurityUtils.getSubject().logout();
    }

    @Override
    public void onExpiration(Session session) {
        if (logger.isDebugEnabled()) {
            logger.debug("============== on session expiration ==============" );
        }

        throw new SessionExpirationException("session has expired, please login again");
    }

    /**
     * 将session转换为string输出
     *
     * @param session session对象
     * @return 返回转换后的session
     */
    private String getStringSession(Session session) {
        return JsonUtil.toJSONString(session);
    }
}
