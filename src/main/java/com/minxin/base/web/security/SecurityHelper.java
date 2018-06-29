package com.minxin.base.web.security;

import com.minxin.base.common.utils.StringUtil;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by todd on 2016/11/9.
 *
 * @author todd
 */
public class SecurityHelper {
    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);

    /**
     * 获取当前登录用户
     *
     * @return 当前用户
     */
    public static SecurityUser getCurrentUser() {
        SecurityUser securityUser = new SecurityUser();

        Session session = SecurityUtils.getSubject().getSession();
        Object sessionAttribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (sessionAttribute instanceof SimplePrincipalCollection) {
            SimplePrincipalCollection principals = (SimplePrincipalCollection) sessionAttribute;
            Object primaryPrincipal = principals.getPrimaryPrincipal();
            if (primaryPrincipal instanceof Pac4jPrincipal) {
                Pac4jPrincipal pac4jPrincipal = (Pac4jPrincipal) primaryPrincipal;
                CommonProfile profile = pac4jPrincipal.getProfile();

                securityUser.setId(StringUtil.toInt(StringUtil.objectToString(profile.getAttribute("userid"))));
                securityUser.setName(profile.getUsername());
                securityUser.setNameZh(StringUtil.objectToString(profile.getAttribute("namezh")));
                securityUser.setGender(StringUtil.toInt(StringUtil.objectToString(profile.getAttribute("gender"))));
                securityUser.setEmail(profile.getEmail());
                securityUser.setStatus(StringUtil.toInt(StringUtil.objectToString(profile.getAttribute("status"))));
                securityUser.setPictureUrl(profile.getPictureUrl()); //TODO 设置用户的图片地址，可以考虑将用户的头像地址设置在此。
                securityUser.setCompanyId(StringUtil.toInt(StringUtil.objectToString(profile.getAttribute("company_id"))));
                securityUser.setSystems((Set<Map>) (profile.getAttribute("system")));

                if (LOGGER.isErrorEnabled()) {
                    LOGGER.debug("############# in security helper, user system = " + ((Set<Map>) (profile.getAttribute("system"))).size());
                }
            }
        }

        return securityUser;
    }
}
