package com.apeny.hduacm;

import java.util.Scanner;

/**
 * Problem Description
 * Hey, welcome to HDOJ(Hangzhou Dianzi University Online Judge).
 * In this problem, your task is to calculate SUM(n) = 1 + 2 + 3 + ... + n.
 * Input
 * The input will consist of a series of integers n, one integer per line.
 * Output
 * For each case, output SUM(n) in one line, followed by a blank line. You may assume the result will be in the range of 32-bit signed integer.
 * Sample Input
 * 1
 * 100
 * Sample Output
 * 1
 * 5050
 * Created by ahu on 2017年09月01日.
 */
public class Acm1001 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int number = sc.nextInt();
            int sum = 0;
            if (number > 0) {
                sum = number * (number + 1) / 2;
            }
            System.out.println(sum);
            System.out.println();
        }
    }
}