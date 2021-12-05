package org.rt.advent.twentyone.day3;

import org.rt.advent.twentyone.day2.Day2;
import org.rt.advent.twentyone.util.DayPuzzle;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 extends DayPuzzle {
    ByteArray array = new ByteArray();
    @Override
    public String puzzle1() {
        try {
            array = new ByteArray();
            readCommonByteAndConvert(Files.lines(Path.of(getDayInput())));
            return ""+calculateConsumption();
        } catch(Exception e) {
            throw new RuntimeException("unable to complete puzzle",e);
        }
    }

    @Override
    public String puzzle2() {
        try {
            array = new ByteArray();
            return ""+calculateLifeSupportRating(Files.lines(Path.of(getDayInput())));
        } catch(Exception e) {
            throw new RuntimeException("unable to complete puzzle",e);
        }
    }

    public void readCommonByteAndConvert(Stream<String> stream) {
        array = new ByteArray();
        array.accumulateAll(stream);

    }


    public int calculateGammaRate() {
        return array.calculateGammaRate();
    }

    public int calculateEpsilonRate() {
        return array.calculateEpsilonRate();
    }

    public int calculateConsumption() {
        return calculateGammaRate()*calculateEpsilonRate();
    }




    public int calculateLifeSupportRating(Stream<String> stream) {
        Collection<String> valuesArray = stream.collect(Collectors.toCollection(() -> new ArrayList<String>()));
        int oxygenRating = array.convertByteToInt(array.getOxygenRating(valuesArray.stream()));
        int co2SrubberRating = array.convertByteToInt(array.getCO2ScrubberRating(valuesArray.stream()));
        return oxygenRating*co2SrubberRating;
    }
}
