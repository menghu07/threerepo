package com.apeny.hduacm.hduacm100x;

import org.junit.Test;

/**
 * Created by ahu on 2017年09月02日.
 */
public class Acm100xTest {

    @Test
    public void test1001() {
        int max = 999999;
        for (int i = 0; i < max; i++) {
            //int sum = i % 2 == 0 ? (i / 2) * (i + 1) : i * ((i + 1) / 2);
            int sum = (int) (1L * i * (i + 1) / 2);
            int sum1 = 0;
            for (int j = 0; j <= i; j++) {
                sum1 += j;
            }
            if (sum1 != sum) {
                System.out.println(i + "sum1" + sum1 + ", " + sum);
                break;
            } else {
                if (sum < 0) {
                    System.out.println(Integer.toHexString(sum) + " i " + i + " overflow sum " + sum);
                    break;
                }
                System.out.println(Integer.toHexString(i) + " i sum " + sum);
            }
        }
        System.out.println("test1001 end......");
    }
}
