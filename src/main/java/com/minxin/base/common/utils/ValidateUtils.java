package com.minxin.base.common.utils;

import java.util.regex.Pattern;

/**
 * 验证邮箱、手机号等格式是否正确
 * create by ZhuLiYu on 2017/03/03
 *@author zhuliyu
 *
 */
public class ValidateUtils {

	/**
	 * 邮箱地址正则表达式，符合情况：含有英文字母、数字、下划线、英文句号、中划线的邮箱名称，
	 * 含有英文字母、数据、下划线的N级域名
	 *
	 */
	private static String EMAIL_REGEX = "^[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

	/**
	 * 手机号码的正则表达式，符合情况：13、14、15、17、18号段11位数字
	 */
	private static String MOBILE_REGEX = "^1[3|4|5|7|8]{1}[0-9]{9}$";

	/**
	 * 验证邮箱地址格式是否正确
	 * @param emailAddress 邮箱地址字符串
	 * @return 布尔
	 */
	public static boolean validateEmailAddress(String emailAddress){
		return validateByRegEx(emailAddress,EMAIL_REGEX);
	}

	/**
	 * 验证手机号格式是否正确
	 * @param mobilePhone 手机号字符串
	 * @return 布尔
	 */
	public static boolean validateMobilePhone(String mobilePhone){
		return validateByRegEx(mobilePhone,MOBILE_REGEX);
	}

	/**
	 * 字符内容是否match正则表达式
	 * @param str 字符内容
	 * @param regEx 正则表达式
	 * @return 布尔
	 */
	private static boolean validateByRegEx(String str, String regEx) {
		return !(null == str || "".equals(str)) && Pattern.matches(regEx, str);
	}
}
