package org.rt.advent.twentyone.day7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

public class CrabsBandInSubmarine {
    Collection<Integer> wholePack = new ArrayList<>();

    public void addCrabs(int[] pos) {
        for (int crabPos : pos) {
            wholePack.add(crabPos);
        }
    }
    public int calcFuelCost(int targetPos) {
        return wholePack.stream().mapToInt(origin -> Math.abs(targetPos-origin)).sum();
    }

    public int calcRealFuelCost(int targetPos) {
        return wholePack.stream().mapToInt(i -> calcOneMove(targetPos,i))
            .sum();
    }
    protected int calcOneMove(int start, int target) {
        int move =Math.abs(target-start);
        if(move==0) return 0;
        if(move==1) return 1;
        return ((move+1)*move)/2;
    }
    public int calcMinimalFuelCost() {
        return calcMinimalFuelCost(i -> calcFuelCost(i));
    }
    public int calcMinimalRealFuelCost() {
        return calcMinimalFuelCost(i -> calcRealFuelCost(i));
    }

    public int calcMinimalFuelCost(IntFunction<Integer> fuelCalculator) {
        int min=Integer.MAX_VALUE;
        int minTarget=-1;
        for(int target=wholePack.size()-1;target>-1;target--) {
            int fuelConso = fuelCalculator.apply(target);
            if(fuelConso<min) {
                minTarget=target;
                min=fuelConso;
            }
        }

        return minTarget;

    }
}
