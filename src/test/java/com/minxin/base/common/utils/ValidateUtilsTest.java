package com.minxin.base.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by todd on 2017/4/11.
 */
public class ValidateUtilsTest {
    @Test
    public void test() {
        assertFalse(!ValidateUtils.validateEmailAddress("abc@123.com") && !ValidateUtils.validateMobilePhone("abc@123.com"));
        assertFalse(!ValidateUtils.validateEmailAddress("13456852458") && !ValidateUtils.validateMobilePhone("13456852458"));
        assertTrue(!ValidateUtils.validateEmailAddress("258") && !ValidateUtils.validateMobilePhone("854"));
        assertTrue(!ValidateUtils.validateEmailAddress("abdc@") && !ValidateUtils.validateMobilePhone("abdc@"));
    }

    public void validateEmailAddress() throws Exception {
        validMail();
        noneDomain();
        invalidMail();
        inValidMail();
//        invalidChar();
    }

    private void validMail() {
        String email = "abc@123.com";
        assertTrue(ValidateUtils.validateEmailAddress(email));

        email = "ab_c@123.com";
        assertTrue(ValidateUtils.validateEmailAddress(email));
    }

    private void noneDomain() {
        String email = "abdc@";
        assertFalse(ValidateUtils.validateEmailAddress(email));
    }

    private void invalidMail() {
        String email = "@abdc.com";
        assertFalse(ValidateUtils.validateEmailAddress(email));
    }

    private void inValidMail() {
        String email = "@abdc";
        assertFalse(ValidateUtils.validateEmailAddress(email));
    }

    private void invalidChar() {
        String email = "a.bc@123.com";
        assertFalse(ValidateUtils.validateEmailAddress(email));
    }
}