package org.rt.advent.twentyone.day8;

import org.rt.advent.twentyone.day5.Line;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class DigitDisplayCounter {
    public DigitDisplayCounter() {

    }

    public int countOccurrencesOfIdentifiableNumbers(BufferedReader buffer) throws IOException {
        int count=0;
        for (String line = buffer.readLine(); line != null; line = buffer.readLine()) {
            count+=filterArrayAndCount(getOutputValuesPart(line));
        }
        return count;
    }

    protected String getOutputValuesPart(String inputValue) {
        return inputValue.split("\\|")[1];
    }
    protected int filterArrayAndCount(String outputValues) {
        return (int)Arrays.stream(outputValues.split(" "))
                .filter(this::filterOneFourSevenAndHeight)
                .count();
    }

    protected boolean filterOneFourSevenAndHeight(String value) {
        int length = value.length();
        switch(length) {
            case 2: case 3: case 4: case 7: return true;
            default:return false;
        }


    }
}
