package com.minxin.base.common.utils;

import com.minxin.base.common.utils.test.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * Created by todd on 2016/11/7.
 */
public class JsonUtilTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void toJSONString() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("test");
        user.setGender('1');
        Calendar c = Calendar.getInstance();
        c.set(2001, 5, 20);
        user.setBirthday(c.getTime());

        String userString = JsonUtil.toJSONString(user);
        logger.debug("user json = " + userString);
    }

    @Test
    public void jsonStringToObject() throws Exception {
        String userJson = "{\"id\":2,\"name\":\"test2\",\"gender\":\"1\",\"birthday\":\"2011-5-20\"}";
        User user = JsonUtil.jsonStringToObject(userJson, User.class);
        logger.debug(user.getName());
    }
}