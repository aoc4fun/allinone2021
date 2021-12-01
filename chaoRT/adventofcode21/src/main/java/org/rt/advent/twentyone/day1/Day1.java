package org.rt.advent.twentyone.day1;

import org.rt.advent.twentyone.io.FromFileIntArrayReader;
import org.rt.advent.twentyone.io.IntArrayReader;
import org.rt.advent.twentyone.util.DayPuzzle;

import java.io.*;

public class Day1 extends DayPuzzle {

    IntArrayReader reader;

    public Day1() {
        reader = new FromFileIntArrayReader( this::getDayStream);
    }

    public int countHowManyPositiveSlope(int[] measures) {
        boolean first = true;
        int last = 0;
        int count = 0;
        for (int i : measures) {
            if (first) first = false;
            else {
                count+=(i>last)?1:0;
            }
            last=i;
        }
        return count;
    }


    public static void main(String[] args) {
        Day1 day1 = new Day1();
        runPuzzles(day1);
    }

    @Override
    public String puzzle1() {

        return ""+countHowManyPositiveSlope(reader.read());
    }

    @Override
    public String puzzle2() {
        MeasureAccumulator accumulator = new MeasureAccumulator(reader);
        return ""+countHowManyPositiveSlope(accumulator.read());
    }
}
