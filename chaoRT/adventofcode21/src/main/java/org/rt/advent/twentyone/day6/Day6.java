package org.rt.advent.twentyone.day6;

import org.rt.advent.twentyone.day5.Day5;
import org.rt.advent.twentyone.day5.Line;
import org.rt.advent.twentyone.day5.VentMap;
import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.io.IOException;
import java.util.Arrays;

public class Day6 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
            LanternFishBand band = new LanternFishBand();
            band.init(getDayStringAsIntArray());


            return ""+band.countFishAfter(80);
        } catch (IOException e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {
            LanternFishBand band = new LanternFishBand();
            band.init(Arrays.stream(getDayStream().readLine().split(",")).mapToInt(Integer::parseInt).toArray());


            return ""+band.countFishAfter(256);
        } catch (IOException e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    public static void main(String[] args) {
        Day6 day = new Day6();
        runPuzzles(day);
    }
}
