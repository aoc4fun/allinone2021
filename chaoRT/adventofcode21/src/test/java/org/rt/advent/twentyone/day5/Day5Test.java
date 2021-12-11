package org.rt.advent.twentyone.day5;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    @Test
    public void basicReadTest() throws IOException {
        String test = "0,9 -> 5,9\n" +
                "8,0 -> 0,8\n" +
                "9,4 -> 3,4\n" +
                "2,2 -> 2,1\n" +
                "7,0 -> 7,4\n" +
                "6,4 -> 2,0\n" +
                "0,9 -> 2,9\n" +
                "3,4 -> 1,4\n" +
                "0,0 -> 8,8\n" +
                "5,5 -> 8,2";
        VentMap map = VentMap.VentMapFactory.createFromReader(new BufferedReader(new StringReader(test)), line -> line.isStrait());

        String expected = "0000000002" +  System.lineSeparator() +
                "0000100002" +  System.lineSeparator() +
                "0110100002" +  System.lineSeparator() +
                "0000200001" +  System.lineSeparator() +
                "0000100001" +  System.lineSeparator() +
                "0000100001" +  System.lineSeparator() +
                "0000100000" +  System.lineSeparator() +
                "1111200000" +  System.lineSeparator() +
                "0000100000" +  System.lineSeparator() +
                "0000100000" +  System.lineSeparator();
        assertEquals(expected, map.getMapAsString(10, System.lineSeparator()));

        assertEquals(5, map.countPointWhereTwoPointsOverlaps());
    }

}