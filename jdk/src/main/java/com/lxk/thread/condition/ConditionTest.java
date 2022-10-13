package com.lxk.thread.condition;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Condition test
 *
 * @author LiXuekai on 2021/5/12
 */
public class ConditionTest {

    @Test
    public void test() throws InterruptedException {
        CustomBlockedList<Integer> customBlockedList = new CustomBlockedList<>();

        new Thread(() -> {
            Random random = new Random();
            while (true) {
                int nextInt = random.nextInt(100);
                customBlockedList.in(nextInt);
                System.out.println(" in number is " + nextInt);
                sleep(1);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Integer out = customBlockedList.out();
                System.out.println("out number is .............." + out);
                sleep(1);
            }
        }).start();


        TimeUnit.MINUTES.sleep(5);
    }

    public void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
