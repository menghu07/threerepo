package com.apeny.doublehit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 倍投法
 * Created by ahu on 2017年09月10日.
 */
public class DoubleHit {

    private static final int WIN = 1;

    private static final int LOSE = 0;

    public static void main(String[] args) {
        long amount = 9_000_000_000_000L;
        long smallAmount = 900_000_000;
        long bet = 1L;
        doubleHit(smallAmount, bet);
    }

    /**
     * 倍投法：以1的倍数押注，资金底9千亿
     *
     * @param amount
     * @param bet
     */
    private static void doubleHit(long amount, long bet) {
        Random r = new Random(2);
        DecimalFormat format = new DecimalFormat("#,###");
        long money = amount;
        long localBet = bet;
        long times = 0;
        long winTimes = 0;
        long loseTimes = 0;
        int successiveLoseTimes = 0;
        while (money > 0) {
            int probability = r.nextInt(2);
            String winOrLose = "胜";
            long currentBet = localBet;
            if (probability == WIN) {
                money += localBet;
                localBet = bet;
                winTimes++;
                successiveLoseTimes = 0;
            } else if (probability == LOSE) {
                money -= localBet;
                winOrLose = "负";
                loseTimes++;
                successiveLoseTimes++;
                if (successiveLoseTimes >= 8) {
                    localBet = bet;
                } else {
                    localBet = localBet << 2;
                }
                if (localBet < 0) {
                    System.err.println("localBet 小于 0 " + localBet);
                    System.exit(1);
                }
            } else {
                System.err.println("程序出错......");
                System.exit(1);
            }
            System.out.println(++times + ", 此局胜负：" + winOrLose + ", 当前拥有资金："
                    + format.format(money) + "元, 此局押注：" + currentBet
                    + ",胜次：" + winTimes + ",负次：" + loseTimes
                    + ",胜率：" + BigDecimal.valueOf(winTimes).divide(BigDecimal.valueOf(times), 4, BigDecimal.ROUND_HALF_UP));
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("对不起,你的输光了：" + money);

    }
}
