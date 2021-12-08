package org.rt.advent.twentyone.day7;

import org.rt.advent.twentyone.day6.LanternFishBand;
import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.io.IOException;
import java.util.Arrays;

public class Day7 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {

            CrabsBandInSubmarine band = new CrabsBandInSubmarine();
            band.addCrabs(getDayStringAsIntArray());

            int minConsoTarget  = band.calcMinimalFuelCost();
            return ""+band.calcFuelCost(minConsoTarget);
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {

            CrabsBandInSubmarine band = new CrabsBandInSubmarine();
            band.addCrabs(getDayStringAsIntArray());

            int minConsoTarget  = band.calcMinimalRealFuelCost();
            return ""+band.calcRealFuelCost(minConsoTarget);
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    public static void main(String[] args) {
        Day7 day = new Day7();
        runPuzzles(day);
    }
}
