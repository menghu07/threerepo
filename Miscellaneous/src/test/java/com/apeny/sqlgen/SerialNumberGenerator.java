package com.apeny.sqlgen;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by apeny on 2018年01月06日.
 */
public class SerialNumberGenerator {

    private static final SimpleDateFormat MINUTE = new SimpleDateFormat("yyMMddHHmmss");
    private static final SimpleDateFormat SIMPLE_MILLISECOND = new SimpleDateFormat("yyMMddHHmmssSSS");
    private static final SimpleDateFormat ALL_YEAR_MILLISECOND = new SimpleDateFormat("yyyyMMddHHmmssSSS");


    public static final int LEN_20 = 20;
    public static final int LEN_25 = 25;
    public static final int LEN_27 = 27;

    /**
     * <p>
     * Description  生成指定长度流水号(只包含数字)
     * </p>
     *
     * @param length 20,25,27
     * @return
     * @author apy
     * @date 2018年01月06日 11:44:58
     */
    public static String genTxNo(int length) {
        String txNo;
        switch (length) {
            case LEN_20:
                txNo = getTxNo20();
                break;
            case LEN_25:
                txNo = getTxNo25();
                break;
            case LEN_27:
                txNo = getTxNo27();
                break;
            default:
                throw new IllegalArgumentException("don't support length[" + length + "]");
        }
        return txNo;
    }

    /**
     * <p>
     * Description  * 20位流水号
     * yyMMddHHmm + random(10)
     * </p>
     *
     * @return
     * @author apy
     * @date 2018年01月06日 11:39:25
     */
    private static String getTxNo20() {
        String timeString = MINUTE.format(Calendar.getInstance().getTime());
        return timeString + RandomNumberGenerator.generateRandomWithLength(10);
    }

    /**
     * <p>
     * Description  25位流水号
     * yyMMddHHmmssSSS + random(10)
     * </p>
     *
     * @return
     * @author apy
     * @date 2018年01月06日 11:43:02
     */
    private static String getTxNo25() {
        String timeString = SIMPLE_MILLISECOND.format(Calendar.getInstance().getTime());
        return timeString + RandomNumberGenerator.generateRandomWithLength(10);
    }

    /**
     * <p>
     * Description 27位流水号
     * yyyyMMddHHmmssSSS = random(10)
     * </p>
     *
     * @return
     * @author apy
     * @date 2018年01月06日 11:42:33
     */
    private static String getTxNo27() {
        String timeString = ALL_YEAR_MILLISECOND.format(Calendar.getInstance().getTime());
        return timeString + RandomNumberGenerator.generateRandomWithLength(10);
    }

    private static class RandomNumberGenerator {

        /**
         * <p>
         * Description 生成指定长度的随机数，只包含数字
         * </p>
         *
         * @param length
         * @return
         * @author apy
         * @date 2018年01月06日 11:40:58
         */
        private static String generateRandomWithLength(int length) {
            Random random = new Random();
            String randomString = "";
            while (randomString.length() < length) {
                randomString += Math.abs(random.nextLong());
            }
            return randomString.substring(0, length);
        }
    }
}
