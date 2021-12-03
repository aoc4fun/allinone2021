package org.rt.advent.twentyone.day3;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {

    @Test
    public void testConvert() {
        ByteArray array = new ByteArray();


        Assert.assertEquals(9,array.convertByteToInt("01001"));
    }
    @Test
    public void sample1Tests() {
        String[] values = new String[]{ "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010"};

        Day3 day3 = new Day3();

        day3.readCommonByteAndConvert(Arrays.stream(values));
        Assert.assertEquals(22,day3.calculateGammaRate());
        Assert.assertEquals(9,day3.calculateEpsilonRate());
        Assert.assertEquals(198,day3.calculateConsumption());

    }
    @Test
    public void sample2Tests() {
        String[] values = new String[]{ "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010"};

        Day3 day3 = new Day3();

        day3.readCommonByteAndConvert(Arrays.stream(values));

        Assert.assertEquals("10111",day3.array.getOxygenRating(Arrays.stream(values)));
        Assert.assertEquals("01010",day3.array.getCO2ScrubberRating(Arrays.stream(values)));
        Assert.assertEquals(230,day3.calculateLifeSupportRating(Arrays.stream(values)));

    }


}