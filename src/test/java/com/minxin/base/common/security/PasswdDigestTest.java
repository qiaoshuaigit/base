package com.minxin.base.common.security;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by todd on 2017/3/9.
 *
 * @author todd
 */
public class PasswdDigestTest {
    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(PasswdDigestTest.class);

    @Test
    public void digestEncodedPassword() throws Exception {
        String password = PasswdDigest.digestEncodedPassword("passportAdmin", "abcdefg");
        String password2 = PasswdDigest.digestEncodedPassword("passportAdmin", "ABCDEFG");
//        assertTrue("7b2885b4b469bfa00c307f128eb659e6da6974830d8865dff38fe950ccf6264f2810dc6d9bc626d350a04b59fa7544e0105068fb976f9541eb7485b7ee2e22eb".equals(password));
        LOGGER.info(password);
        LOGGER.info(password2);
        assertTrue(!password.equals(password2));

    }

    @Test
    public void generateRandomPassword() throws Exception {
        for (int i = 0; i < 100; ++i) {
            LOGGER.info(PasswdDigest.generateRandomPassword());
        }
    }

    @Test
    public void updateUsers() throws Exception {
        Map<String, String> users = new HashMap<>();
        users.put("retong", "Retong1752");
//        users.put("wangzi5731", "123456");

        /*users.put("chenhu", "123456");
        users.put("chenxueyang", "123456");
        users.put("songqingfeng", "123456");
        users.put("panfengjian", "123456");
        users.put("lixiufeng", "123456");
        users.put("caoyipeng", "123456");
        users.put("lipingwei", "123456");
        users.put("songwenjun", "123456");
        users.put("liuwangling", "123456");
        users.put("denghaijuan", "123456");*/


        /*
        users.put("minxin", "123456");
        users.put("yingyeyuan", "123456");
        users.put("xiaoshenyang", "123456");
        users.put("anna1929", "123456");

        users.put("test10", "123456");
        users.put("test11", "123456");
        users.put("test12", "123456");
        users.put("test13", "123456");
        users.put("test14", "123456");
        users.put("test15", "123456");
        users.put("test16", "123456");
        users.put("test17", "123456");
        users.put("test18", "123456");
        users.put("test19", "123456");
        users.put("test2", "123456");
        users.put("test20", "123456");
        users.put("test21", "123456");
        users.put("test22", "123456");
        users.put("test23", "123456");
        users.put("test24", "123456");
        users.put("test25", "123456");
        users.put("test26", "123456");
        users.put("test27", "123456");*/


        Set<String> keys = users.keySet();
        for (String name : keys) {
            String password = users.get(name);
            String encodePassword = PasswdDigest.digestEncodedPassword(name, password);
            LOGGER.info("update t_pp_user set password = '{}' where username = '{}';", encodePassword, name);

//            Assert.assertTrue("d70f8abe3f23575d4b2607f14a553261c30ef0e372d125ec2eff3e61972c85173b303943edf29a067b0b06e685624cc878560924e8fb95f05e845fbbd6ada3f4".equals(encodePassword));
        }
    }
}