package com.apeny.exceptionforward.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by monis on 2019/1/5.
 */
public class TimeUtils {

    /**
     * 生成17位时间
     * @return
     */
    public static String getTimestampsss() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
