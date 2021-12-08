package org.rt.advent.twentyone.day6;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class LanternFishBand {
    final static int MAY_AGE = 8;
    long[] countPerDay=new long[MAY_AGE+1];


    public LanternFishBand() {
        initCounts();
    }
    private void initCounts() {
        for(int day=0;day<=MAY_AGE;day++) {
            countPerDay[day]=0;
        }
    }
    private void foundAFish(int age) {
        countPerDay[age]++;
    }

    public void init(int[] foundFishList) {
        initCounts();
        Arrays.stream(foundFishList).sequential().forEach(this::foundAFish);
    }

    public void nextDay() {
        long breaders = countPerDay[0];
        for (int age=1;age<=MAY_AGE;age++) {
            countPerDay[age-1]=countPerDay[age];
        }
        countPerDay[6]+=breaders;
        countPerDay[8]=breaders;
    }

    public void moveInTime(int count, boolean display) {
        while(count>0) {
            nextDay();
            if(display) displayDay();
            count--;
        }
    }


    public long countFishAfter(int days) {
        moveInTime(days, false);
        return sumFush();
    }

    public long countFishAfterAndDisplay(int days) {
        displayDay();
        moveInTime(days, true);
        return sumFush();
    }
    private long sumFush() {
        long start= 0;
        for(long toAdd:countPerDay) {
            start+=toAdd;
        }
        return start;
    }

    private void appendDayFishTo(StringBuilder buffer, int age) {
        IntStream.rangeClosed(1, (int)countPerDay[age]).forEach(fish -> buffer.append(",").append(age));
    }
    public void displayDay() {
       System.out.println(getDisplay());
    }
    public String getDisplay() {
        StringBuilder result  = new StringBuilder();
        IntStream.rangeClosed(0, MAY_AGE).forEach(age -> this.appendDayFishTo(result, age));
        return result.substring(1);
    }
}
