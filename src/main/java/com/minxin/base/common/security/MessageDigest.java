package com.minxin.base.common.security;

import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.ConfigurableHashService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by todd on 2017/3/28.
 * @author todd
 */
public class MessageDigest {
    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(MessageDigest.class);

    /**
     * 算法
     */
    private static final String SHA_1 = MessageDigestAlgorithms.SHA_1;
    /**
     *
     */
    private static final String SHA_256 = MessageDigestAlgorithms.SHA_256;
    /**
     *
     */
    private static final String SHA_384 = MessageDigestAlgorithms.SHA_384;
    /**
     *
     */
    private static final String SHA_512 = MessageDigestAlgorithms.SHA_512;
    /**
     *
     */
    private static final String MD5 = MessageDigestAlgorithms.MD5;

    /**
     * salt迭代次数
     */
    private static final int NUMBER_OF_ITERATOR = 10;

    /**
     * static salt
     */
    private static final String STATIC_SALT = "stat!c_s@1t";

    /**
     * SHA_1 algorithm
     *
     * @param message     original message
     * @param dynamicSalt dynamic salt
     * @return the digested message
     */
    public static String getSha1DigestMessage(final String message, final String dynamicSalt) {
        return digestEncodedMessage(message, dynamicSalt, SHA_1, STATIC_SALT);
    }

    /**
     * SHA_256 algorithm
     *
     * @param message     original message
     * @param dynamicSalt dynamic salt
     * @return the digested message
     */
    public static String getSha256DigestMessage(final String message, final String dynamicSalt) {
        return digestEncodedMessage(message, dynamicSalt, SHA_256, STATIC_SALT);
    }

    /**
     * SHA_384 algorithm
     *
     * @param message     original message
     * @param dynamicSalt dynamic salt
     * @return the digested message
     */
    public static String getSha384DigestMessage(final String message, final String dynamicSalt) {
        return digestEncodedMessage(message, dynamicSalt, SHA_384, STATIC_SALT);
    }

    /**
     * SHA_512 algorithm
     *
     * @param message     original message
     * @param dynamicSalt dynamic salt
     * @return the digested message
     */
    public static String getSha512DigestMessage(final String message, final String dynamicSalt) {
        return digestEncodedMessage(message, dynamicSalt, SHA_512, STATIC_SALT);
    }

    /**
     * SHA_512 algorithm
     *
     * @param message     original message
     * @param dynamicSalt dynamic salt
     * @param staticSalt  static salt
     * @return the digested message
     */
    public static String getSha512DigestMessage(final String message, final String dynamicSalt, final String staticSalt) {
        return digestEncodedMessage(message, dynamicSalt, SHA_512, staticSalt);
    }

    /**
     * SHA_256 algorithm
     *
     * @param message     original message
     * @param dynamicSalt dynamic salt
     * @return the digested message
     */
    public static String getMd5DigestMessage(final String message, final String dynamicSalt) {
        return digestEncodedMessage(message, dynamicSalt, MD5, STATIC_SALT);
    }

    /**
     * common algorithm
     *
     * @param message     original message
     * @param dynamicSalt dynamic salt
     * @param alg         algorithm
     * @param staticSalt  static salt
     * @return the digested message
     */
    private static String digestEncodedMessage(final String message, final String dynamicSalt, final String alg, final String staticSalt) {
        if (message == null || message.length() == 0 || dynamicSalt == null || dynamicSalt.length() == 0) {
            throw new NullPointerException();
        }

        final ConfigurableHashService hashService = new DefaultHashService();

        if (StringUtils.isNotBlank(staticSalt)) {
            hashService.setPrivateSalt(ByteSource.Util.bytes(staticSalt));
        }
        hashService.setHashAlgorithmName(alg);
        hashService.setHashIterations(NUMBER_OF_ITERATOR);

        final HashRequest request = new HashRequest.Builder().setSalt(dynamicSalt).setSource(message).build();
        return hashService.computeHash(request).toHex();
    }
}
