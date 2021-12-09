package org.rt.advent.twentyone.day9;


import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;


import java.util.Collection;

public class Day9 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
            CaveMap map = CaveMap.createFromLevels(getDayStream());
            Collection<CaveMap.RiskLevel> levels  = map.getLocalMins();
            long sum = levels.stream().mapToLong(CaveMap.RiskLevel::getRiskLevel).sum();
            long mySum = mySum(levels.stream().mapToLong(CaveMap.RiskLevel::getRiskLevel).toArray());
            return ""+ sum+" "+mySum;
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }
    public long mySum(long[] values) {
        long result = 0;
        for(long val:values) {
            result+=val;
        }
        return result;
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {
            CaveMap map = CaveMap.createFromLevels(getDayStream());
            return ""+map.getRiskysurfaceSize();

        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    public static void main(String[] args) {
        Day9 day = new Day9();
        runPuzzles(day);
    }
}
