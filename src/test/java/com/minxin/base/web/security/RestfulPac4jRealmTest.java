package com.minxin.base.web.security;

import com.minxin.base.common.utils.JsonUtil;
import com.minxin.base.web.util.HttpConnectionUtil;
import com.minxin.base.web.util.HttpConnectionUtilTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by todd on 2017/3/6.
 * @author todd
 *
 */
public class RestfulPac4jRealmTest {
    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionUtilTest.class);

    @Test
    public void testNullPointer() {
        Collections.unmodifiableSet(new HashSet<Map>());
    }

    @Test
    public void testGetUserSystem() {
        String authorizationUrl = "http://10.10.95.86:8080";
        String functionPath = "/serve/getUserSystems";
        Integer userId = 76;

        Map<String, Object> params = new HashMap<>();

        params.put("userId", userId);
        String result = HttpConnectionUtil.doPost(this.buildAuthorizationUrl(authorizationUrl, functionPath), params);
        Set<Map> setResult = JsonUtil.jsonStringToObject(result, Set.class);
        LOGGER.debug("get user roles result = " + setResult);
    }

    @Test
    public void doPost() throws Exception {
        String authorizationUrl = "http://10.10.21.71:8080/minxin-passport";
//        String authorizationUrl = "https://passport.minxinjituan.com/minxin-passport";
        String userName = "passportAdmin";
        String functionPath = "/serve/getUserRoles";

        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        String result = HttpConnectionUtil.doPost(this.buildAuthorizationUrl(authorizationUrl, functionPath), params);
        Set<String> setResult = JsonUtil.jsonStringToObject(result, Set.class);
        LOGGER.debug("get user roles result = " + result);
    }

    private String buildAuthorizationUrl(String authorizationUrl, String functionPath) {
        if (authorizationUrl == null) {
            return null;
        }

        if (functionPath != null) {
            return authorizationUrl + functionPath;
        }
        return null;
    }

    @Test
    public void testCASRest() throws Exception {
//        String casUrl = "http://10.10.21.71:8080/sso/v1/tickets";
        String casUrl = "https://login.minxinjituan.com/v1/tickets";

        Map<String, Object> params = new HashMap<>();
        params.put("username", "passportAdmin");
        params.put("password", "123456");
        String result = HttpConnectionUtil.doPostReturnHeader(casUrl, params, "Location");
        LOGGER.info("cas result = " + result);
    }

    @Test
    public void testServiceTickert() throws Exception {
//        String casUrl = "http://10.10.21.71:8080/sso/v1/tickets/TGT-18-wFyxE6B4qelffQ3CiacucdRu6qYtxo42nkIFTavWrVRaL9UeUu-login.minxin.com";
        String casUrl = "https://login.minxinjituan.com/v1/tickets/TGT-1-nASnymmOX2DGS6NRabgvGM7jA99Ab0BqMvrjGANaRdih3cNYFq-login121.minxinjituan.com";

        Map<String, Object> ticketParames = new HashMap<>();
        ticketParames.put("service", "https://passport.minxinjituan.com/serve/getUserRoles");
        String result = HttpConnectionUtil.doPost(casUrl, ticketParames);
        LOGGER.info("service ticket = "  + result);
    }

    @Test
    public void deleteRest()  throws Exception {
        String casUrl = "https://login.minxinjituan.com/v1/tickets/TGT-1-nASnymmOX2DGS6NRabgvGM7jA99Ab0BqMvrjGANaRdih3cNYFq-login121.minxinjituan.com";

        String result = HttpConnectionUtil.doDelete(casUrl);
        LOGGER.info("service ticket = "  + result);
    }

}