package com.minxin.base.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by todd on 2017/3/9.
 *
 * @author todd
 */
public final class PasswdDigest {
    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(PasswdDigest.class);

    /**
     * static salt
     */
    private static final String STATIC_SALT = "s@1t";


    /**
     * 字母
     */
    private static final char[] CHARS = {'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'i', 'J', 'j', 'K', 'k',
            'L', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z'};

    /**
     * 特殊字符
     */
    private static final char[] SIGNAL = {'!', '@', '=', '_', '%', '&'};

    /**
     * 数字
     */
    private static final int[] NUMBER = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

    /**
     * Digest encoded password.
     *
     * @param dynamicSalt dynamic salt
     * @param password    the original password
     * @return the digested password
     */
    public static String digestEncodedPassword(final String dynamicSalt, final String password) {
        if (dynamicSalt == null || dynamicSalt.length() == 0 || password == null || password.length() == 0) {
            throw new NullPointerException();
        }

        return MessageDigest.getSha512DigestMessage(password, dynamicSalt, STATIC_SALT);
    }

    /**
     * 随机密码
     *
     * @return 生成的随机密码
     */
    public static String generateRandomPassword() {
        StringBuffer randomPasswd = new StringBuffer("");
        int arrayIndex;
        for (int i = 0; i < 8; ++i) {
            arrayIndex = new Long(Math.round(Math.random() * 10 / 4)).intValue();

            switch (arrayIndex) {
                case 0:
                    randomPasswd.append(CHARS[new Long(Math.round(Math.random() * 49)).intValue()]);
                    continue;
                case 1:
                    randomPasswd.append(SIGNAL[new Long(Math.round(Math.random() * 5)).intValue()]);
                    continue;
                default:
                    randomPasswd.append(NUMBER[new Long(Math.round(Math.random() * 9)).intValue()]);
            }
        }

        return randomPasswd.toString();
    }
}
