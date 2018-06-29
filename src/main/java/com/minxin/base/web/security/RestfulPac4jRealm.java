package com.minxin.base.web.security;

import com.minxin.base.common.utils.JsonUtil;
import com.minxin.base.common.utils.StringUtil;
import com.minxin.base.web.util.HttpConnectionUtil;
import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by todd on 2017/3/2.
 *
 * @author todd
 */
public class RestfulPac4jRealm extends Pac4jRealm {
    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(RestfulPac4jRealm.class);

    /**
     * 获取权限的passport地址的url
     */
    private String authorizationUrl;

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    /**
     * 增加多系统获取
     * @param authenticationToken token
     * @return 包含了多系统数据的AuthenticationInfo
     * @throws AuthenticationException 异常
     */
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken) throws AuthenticationException {
        final AuthenticationInfo authenticationInfo = super.doGetAuthenticationInfo(authenticationToken);
        final Pac4jPrincipal principal = authenticationInfo.getPrincipals().oneByType(Pac4jPrincipal.class);
        if (principal != null) {
            final CommonProfile profile = principal.getProfile();
            String userId = StringUtil.objectToString(profile.getAttribute("userid"));
            Set<Map> result  = this.getUserSystems(StringUtil.toInt(userId));
            profile.addAttribute("system", result);
        }
        return authenticationInfo;
    }

    /**
     * 修改pac4j获取权限的方式，通过http从passport加载，提取到公共方法，不需要业务方自己加载。
     *
     * @param principals 权限对象
     * @return 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        final Set<String> roles = new HashSet<>();
        final Set<String> permissions = new HashSet<>();
        final Pac4jPrincipal principal = principals.oneByType(Pac4jPrincipal.class);
        if (principal != null) {
            final List<CommonProfile> profiles = principal.getProfiles();
            for (CommonProfile profile : profiles) {
                if (profile != null) {
                    roles.addAll(profile.getRoles());
                    permissions.addAll(profile.getPermissions());

                    if (roles.size() == 0) {
                        roles.addAll(this.getPrimaryProfiles(profile.getId(), "/serve/getUserRoles"));
                    }

                    if (permissions.size() == 0) {
                        permissions.addAll(this.getPrimaryProfiles(profile.getId(), "/serve/getUserPermission"));
                    }
                }
            }
        }

        final SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 根据用户id，查询用户所分配的系统
     * @param userId 用户id
     * @return 用户所分配的系统
     */
    private Set<Map> getUserSystems(Integer userId) {
        if (this.authorizationUrl == null) {
            return null;
        }

        String functionPath = "/serve/getUserSystems";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        String result = HttpConnectionUtil.doPost(this.buildGetProfileUrl(functionPath), params);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("get user authorization results from {} = {}", functionPath, result);
        }
        if (result == null) {
            return Collections.unmodifiableSet(new HashSet<Map>());
        }
        Set<Map> userSystemsResult = JsonUtil.jsonStringToObject(result, Set.class);
        if (userSystemsResult == null) {
            return Collections.unmodifiableSet(new HashSet<Map>());
        }

        return Collections.unmodifiableSet(userSystemsResult);
    }

    /**
     * 获取权限列表
     *
     * @param userName     当前登录账号
     * @param functionPath 权限地址，角色、控件对应的查询url
     * @return 完整权限列表
     */
    private Set<String> getPrimaryProfiles(String userName, String functionPath) {
        if (this.authorizationUrl == null) {
            return Collections.unmodifiableSet(new HashSet<String>());
        }

        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        String result = HttpConnectionUtil.doPost(this.buildGetProfileUrl(functionPath), params);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("get user authorization results from {} = {}", functionPath, result);
        }
        if (result == null) {
            return Collections.unmodifiableSet(new HashSet<String>());
        }
        Set<String> primaryProfiles = JsonUtil.jsonStringToObject(result, Set.class);
        if (primaryProfiles == null) {
            return Collections.unmodifiableSet(new HashSet<String>());
        }
        return Collections.unmodifiableSet(primaryProfiles);
    }

    /**
     * 构造请求url
     *
     * @param functionPath 角色、控件对应的查询url
     * @return 完整请求url
     */
    private String buildGetProfileUrl(String functionPath) {
        if (this.authorizationUrl == null) {
            return null;
        }

        if (functionPath != null) {
            return this.authorizationUrl + functionPath;
        }
        return null;
    }
}
