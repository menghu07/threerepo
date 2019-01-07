package com.apeny.servletjsp.util;

import java.text.DateFormat;
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
}
