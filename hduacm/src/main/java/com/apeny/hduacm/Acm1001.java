package com.apeny.hduacm;

import java.util.Scanner;

/**
 * Created by ahu on 2017年09月01日.
 */
public class Acm1001 {

    /**
     * 256 - 1 = 255 = -1 complement
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int number = sc.nextInt();
            int sum = 0;
            if (number > 0) {
                long numberLong = number;
                sum = (int) (numberLong * (numberLong + 1L) / 2L);
            }
            System.out.println(sum);
            System.out.println();
        }
    }
}