package com.apeny.servletjsp.applicationstarter;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by monis on 2019/1/24.
 */

public class OtherShardingTest {

    @Test
    public void testDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        String date = "180201";
        try {
            Date date1 = simpleDateFormat.parse(date);
            System.out.println("date1: " + date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
