package com.apeny.servletjsp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by monis on 2018/11/26.
 */
public class SqlUtil {
    private static final int[] NUBMERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * 生成主键
     * @return
     */
    public static String generateOrderID() {
        Calendar calendar = Calendar.getInstance();
        Random r = new Random();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, r.nextInt(12));
        calendar.set(Calendar.DATE, r.nextInt(28) + 1);
        Date date = calendar.getTime();
        DateFormat format = new SimpleDateFormat("yyMMddhhmmss");
        String text = format.format(date);
        for (int i = 0; i < 13; i++) {
            text += NUBMERS[r.nextInt(10)];
        }
        return text;
    }

    /**
     * 添加指定秒数
     * @param source
     * @param toAddSeconds
     * @return
     */
    public static String addSeconds(String source, int toAddSeconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        try {
            Date sourceDate = simpleDateFormat.parse(source);
            sourceDate.setTime(sourceDate.getTime() + toAddSeconds);
            return simpleDateFormat.format(sourceDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return source;
        }
    }

    /**
     * 10以内随机数
     * @return
     */
    public static int rand10() {
        return new Random(17).nextInt(10);
    }

    /**
     * 365天以内随机数
     * @return
     */
    public static int rand365() {
        return new Random(17).nextInt( 365 * 86400);
    }
}
