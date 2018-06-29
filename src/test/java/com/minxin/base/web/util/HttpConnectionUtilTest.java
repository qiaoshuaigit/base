package com.minxin.base.web.util;

import com.minxin.base.common.utils.JsonUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by todd on 2017/3/3.
 */
public class HttpConnectionUtilTest {
    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionUtilTest.class);

    @Test
    public void test() {
//        String url = "https://passport.minxinjituan.com/serve/getBankContentByName";
        String url = "http://10.10.95.86:8080/serve/getBankContentByName";

        Map<String, Object> params = new HashMap<>();
        params.put("lname", "北京");
        String result = HttpConnectionUtil.doPost(url, params);
        Set<String> setResult = JsonUtil.jsonStringToObject(result, Set.class);
        LOGGER.debug("get user roles result = " + result);
    }

    @Test
    public void testWorkDate() {
        String url = "http://10.10.21.71:8080/minxin-passport/serve/getDateByBeginDateAndWorkdays";

        Map<String, Object> params = new HashMap<>();
        params.put("beginDate", "2017-04-13");
        params.put("days", 1);
        String result = HttpConnectionUtil.doPost(url, params);
//         = JsonUtil.jsonStringToObject(result, Set.class);
        LOGGER.debug("get user roles result = " + result);
    }
}