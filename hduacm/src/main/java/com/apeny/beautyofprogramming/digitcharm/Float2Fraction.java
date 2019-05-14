package com.apeny.beautyofprogramming.digitcharm;

import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 小数表示转换为分数表示
 * Created by monis on 2019/5/4.
 */
public class Float2Fraction {

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        System.out.println("2/3 == " + float2Fraction("0.666(6)"));
        System.out.println("2/7 == " + float2Fraction("0.285714(285714)"));
        System.out.println("2/7() == " + float2Fraction("0.(285714)"));
        System.out.println("11/20 == " + float2Fraction("0.55"));
        System.out.println("1/6 == " + float2Fraction("0.1(66)"));
        System.out.println("1/6 == " + float2Fraction("0.1(6)"));
        System.out.println("1/6 == " + float2Fraction("0.166(6666)"));
        System.out.println("83/500 == " + float2Fraction("0.166"));
    }

    /**
     * 小数表示转换为分数表示
     * @param floatDescription
     * @return
     */
    private static String float2Fraction(String floatDescription) {
        Objects.requireNonNull(floatDescription);
        assert floatDescription.contains(".");
        //以0.开头
        assert Pattern.matches("^0\\.[)(\\d]+$", floatDescription);
        boolean hasUnmatchedBrackets = (floatDescription.contains("(") && !floatDescription.contains(")"))
                || (floatDescription.contains(")") && !floatDescription.contains("("));
        if (hasUnmatchedBrackets) {
            throw new IllegalArgumentException("()不匹配");
        }
        int leftBracketIndex = floatDescription.indexOf('(');
        int rightBracketIndex = floatDescription.indexOf(')');
        if (rightBracketIndex < leftBracketIndex) {
            throw new IllegalArgumentException("(必须在)之前");
        }
        String fixDigits;
        String loopByte;
        int n;
        int m;
        BigInteger numerator;
        BigInteger denominator;
        if (leftBracketIndex > 2) {
            fixDigits = floatDescription.substring(2, leftBracketIndex);
            n = leftBracketIndex - 2;
            m = rightBracketIndex - leftBracketIndex - 1;
            loopByte = floatDescription.substring(leftBracketIndex + 1, rightBracketIndex);
            //计算分子
            String subMain = fixDigits + loopByte;
            numerator = new BigInteger(subMain).subtract(new BigInteger(fixDigits));
            //计算分母
            String lineDowner = repeatN("9", m) + repeatN("0", n);
            denominator = new BigInteger(lineDowner);
        } else if (leftBracketIndex == 2) {
            //只有循环节
            m = rightBracketIndex - leftBracketIndex - 1;
            loopByte = floatDescription.substring(leftBracketIndex + 1, rightBracketIndex);
            //计算分子
            numerator = new BigInteger(loopByte);
            //计算分母
            denominator = new BigInteger(repeatN("9", m));
        } else {
            //无括号
            fixDigits = floatDescription.substring(2);
            n = floatDescription.length() - 2;
            //计算分子
            numerator = new BigInteger(fixDigits);
            //计算分母
            denominator = new BigInteger("1" + repeatN("0", n));
        }
        BigInteger gcd = denominator.gcd(numerator);
        return numerator.divide(gcd) + "/" + denominator.divide(gcd);
    }

    /**
     * 重复一个数m次
     * @param n
     * @param m
     * @return
     */
    private static String repeatN(String n, int m) {
        StringBuilder r = new StringBuilder();
        int i = 0;
        while (i < m) {
            r.append(n);
            i++;
        }
        return r.toString();
    }
}
