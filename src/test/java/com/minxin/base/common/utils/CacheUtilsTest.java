package com.minxin.base.common.utils;

import com.minxin.base.common.security.MessageDigest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by todd on 2016/11/15.
 *
 * @author todd
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@ContextConfiguration(locations = {"classpath:/root-context-base.xml"})
public class CacheUtilsTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void set() throws Exception {
        String statusCode = CacheUtils.set("username", "test");
        assertTrue("OK".equals(statusCode));
    }

    /*@Test
    public void get() throws Exception {
        String value = CacheUtils.get("username");
        logger.debug("============value" + value);
        assertTrue("test".equals(value));
    }*/

    @Test
    public void size() throws Exception {
        Long size = CacheUtils.size();
        logger.debug("the cache size ===============" + size);
    }

    @Test
    public void keys() throws Exception {
        Set keys = CacheUtils.keys();
        Object[] allKeys = keys.toArray();
        for (Object obj : allKeys) {
            logger.debug("key============" + obj);
//            logger.debug("value ==== " + CacheUtils.get(obj));
        }
    }

    @Test
    public void del() throws Exception {
        CacheUtils.del(MessageDigest.getSha1DigestMessage("register_noreply@minxinjituan.cn" + 1 + "times", "register"));
        CacheUtils.del(MessageDigest.getSha1DigestMessage("register_noreply@minxinjituan.cn" + 1 + "times", "find"));
        CacheUtils.del(MessageDigest.getSha1DigestMessage("register_noreply@minxinjituan.cn" + 1 + "expTime", "register"));
        CacheUtils.del(MessageDigest.getSha1DigestMessage("register_noreply@minxinjituan.cn" + 1 + "expTime", "find"));
        CacheUtils.del("b453b6b86d5205f74a87ea20c1fcca1189df1794");
        CacheUtils.del("567fae3dd7f0854a299192ff156ce114b3c78fc4");

    }

    @Test
    public void flush() throws Exception {
        String result = CacheUtils.flush();
        assertTrue("OK".equals(result));
    }


    @Test
    public void setExpire() throws Exception {
        String statusCode = CacheUtils.set("username", "test", 10);
        assertTrue("OK".equals(statusCode));
    }

    @Test
    public void exists() throws Exception {
        Assert.assertTrue(CacheUtils.exists("username"));

        Thread.sleep(10000L);
        Assert.assertFalse(CacheUtils.exists("username"));
    }

    @Test
    public void setExpireUnixTime() throws Exception {
        long times = Calendar.getInstance().getTimeInMillis()/1000 + 6L;
        CacheUtils.set("username", "test", times);

        Assert.assertTrue(CacheUtils.exists("username"));

        Thread.sleep(10000L);
        Assert.assertFalse(CacheUtils.exists("username"));
    }

    @Test
    public void test() throws Exception {
        Object o = CacheUtils.get("08cd2829-c5c6-4515-b23f-0cfc6fb8f554");
        System.out.println(o);

        boolean exists = CacheUtils.exists("08cd2829-c5c6-4515-b23f-0cfc6fb8f554");
        System.out.println("exist= " + exists);
    }

}