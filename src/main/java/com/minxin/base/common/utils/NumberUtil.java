package com.minxin.base.common.utils;

import java.text.NumberFormat;
import java.util.Random;

/**
 * Created by todd on 2016/12/21.
 *
 * @author todd
 */
public class NumberUtil {
    /**
     * Convert a byte variant to corresponding value of ASCII.
     *
     * @param b byte type variant to convert.
     * @return int which has been converted
     * @since 0.1
     */
    public static int byte2int(byte b) {
        if (b < 0) {
            return (int) b + 0x100;
        }

        return b;
    }

    /**
     * Show the percentage based on the appointed number of fraction digits.
     *
     * @param dblPercent java.lang.double
     * @param min        the minimum number of fraction digits to be shown;
     * @param max        the maximum number of fraction digits to be shown;
     * @return java.lang.String[]
     */
    public static String getPercent(double dblPercent, int min, int max) {
        NumberFormat dataformat = NumberFormat.getInstance();
        dataformat.setMaximumFractionDigits(max);
        dataformat.setMinimumFractionDigits(min);
        if (Double.isNaN(dblPercent)) {
            dblPercent = 0.0;
        }
        String tmp = dataformat.format(dblPercent * 100);
        if ("0".equals(tmp)) {
            tmp = "*";
        }
        return tmp;
    }

    /**
     * Function to get random ID,the default value is a random string of 15 bits
     * length.
     *
     * @return A random string of 15 bits length.
     */
    public static Long getRandomNumber() {

        Random randGen = new Random();
        String strID = String.valueOf(Math.abs(randGen.nextLong()));

        return Long.valueOf(strID.substring(0, 15));
    }

    /**
     * Format an int to string which length is appointed, if the length is less
     * than appointed length,add 0 ahead of the int.
     *
     * @param number an int to format.
     * @param len    the length of this string.
     * @return a string representation of the int argument.
     */
    public static String formatIntToStr(int number, int len) {
        StringBuffer t = new StringBuffer("");
        String s = String.valueOf(number);

        if (s.length() < len) {
            for (int j = 0; j < (len - s.length()); j++) {
                t = t.append("0");
            }
        }
        return t.append(s).toString();
    }

    /**
     * Convert the number 1 to 26 to corresponding capital letter, for example:1
     * correspond to A, 2 correspond to B,26 correspond to Z.
     *
     * @param number        the number to convert to a character.
     * @param upperCaseFlag the uppercase equivalent of the character, if any; otherwise,
     *                      the character itself.
     * @return String Capital letter.
     * @throws Exception exception
     */
    public static String numberToLetter(int number, boolean upperCaseFlag) throws Exception {

        // add nine to bring the numbers into the right range (in java, a= 10, z
        // =
        // 35)
        if (number < 1 || number > 26) {
            throw new Exception("The number is out of the proper range (1 to "
                    + "26) to be converted to a letter.");
        }
        int modnumber = number + 9;
        char thechar = Character.forDigit(modnumber, 36);
        if (upperCaseFlag) {
            thechar = Character.toUpperCase(thechar);
        }
        return "" + thechar;
    }
}
