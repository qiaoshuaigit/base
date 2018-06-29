package com.minxin.base.common.security;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by todd on 2017/3/28.
 */
public class MessageDigestTest {
    @Test
    public void getSha1DigestMessage() throws Exception {
        long start = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 100000; ++i) {
            MessageDigest.getSha1DigestMessage("wangshutao007X@minxinjituan.cn", "123456");
        }
        System.out.println("===============" + (Calendar.getInstance().getTimeInMillis() - start));
    }

}