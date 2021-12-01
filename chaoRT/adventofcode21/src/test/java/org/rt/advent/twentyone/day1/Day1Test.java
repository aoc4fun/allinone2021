package org.rt.advent.twentyone.day1;

import org.junit.jupiter.api.Test;
import org.rt.advent.twentyone.day1.Day1;
import org.rt.advent.twentyone.day1.MeasureAccumulator;
import org.rt.advent.twentyone.io.FromArrayIntArrayReader;

import static org.junit.jupiter.api.Assertions.*;

class Day1Test {
    final int[] measures = {
            199,
            200,
            208,
            210,
            200,
            207,
            240,
            269,
            260,
            263
    };
    @Test
    public void test1() {
        Day1 day1 = new Day1();


        assertEquals(7, day1.countHowManyPositiveSlope(measures));
        
    }

    @Test
    public void Test2() {
        Day1 day1 = new Day1();

        FromArrayIntArrayReader reader = new FromArrayIntArrayReader(measures);
        MeasureAccumulator accumulator = new MeasureAccumulator(reader);
        ;

        assertEquals(5, day1.countHowManyPositiveSlope(accumulator.read()));

    }

    @Test
    public void TestAccumulator() {
        Day1 day1 = new Day1();

        FromArrayIntArrayReader reader = new FromArrayIntArrayReader(measures);
        MeasureAccumulator accumulator = new MeasureAccumulator(reader);

        assertArrayEquals(new int[] {
        607,
        618,
        618,
        617,
        647,
        716,
        769,
        792
        }, accumulator.read());
    }

}