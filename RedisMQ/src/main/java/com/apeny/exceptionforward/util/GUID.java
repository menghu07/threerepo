package com.apeny.exceptionforward.util;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by monis on 2019/1/5.
 */
public class GUID {

    public static final int L32 = 32;

    /**
     * UID
     * @param length
     * @return
     */
    public static String generate(int length) {
        if (length <= 0) {
            return "";
        }
        int dateLength = 14;
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Random random = new Random(17);
        String randomNumber = random.nextInt(10) + "";
        String formattedDate = simpleDateFormat.format(date);
        if (length <= dateLength) {
            return formattedDate.substring(dateLength - length);
        }
        int leftLength = length - dateLength;
        int randomLength = randomNumber.length();
        if (randomLength >= leftLength) {
            return formattedDate + randomNumber.substring(randomLength - leftLength);
        }
        StringBuilder finalUID = new StringBuilder(formattedDate);
        for (int i = 0; i < leftLength - randomLength; i++) {
            finalUID.append("0");
        }
        return finalUID + randomNumber;
    }
}
