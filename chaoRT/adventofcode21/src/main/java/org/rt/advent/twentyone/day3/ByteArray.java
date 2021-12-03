package org.rt.advent.twentyone.day3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ByteArray {
    static int[] values;
    private static int count;
    private static boolean init = false;

    public void init(int size) {
        values=new int[size];
        count=0;
        init=true;
    }

    public void reset() {
        init=false;
    }

    public void accumulate(String value) {
        if(init==false) {
            init(value.length());
        }
        int[] chars = value.chars().toArray();
        if(chars.length!=values.length) throw new IllegalArgumentException("invalid entry : "+value);
        for (int index=0;index<chars.length;index++) {
            if(chars[index]=='1') values[index]+=1;
            else values[index]+=0;
        }
        count++;
    }
    public int calculateGammaRate() {
        return convertByteToInt(getMostCommonByteNotEqual());
    }

    public int calculateEpsilonRate() {
        return convertByteToInt(getLeastCommonByteNotEqual());
    }

    protected int convertByteToInt(String binaryRepOfByte) {
        return Integer.parseInt(binaryRepOfByte, 2);
    }
    public String getBytesAsString(boolean mostCommonByte) {
        char[] bytes = new char[values.length];
        float limit = ((float)count)/2;
        byte value=0;

        for (int index =0 ;index<values.length;index++) {
            if(mostCommonByte && values[index] > limit) {
                bytes[index]='1';
            } else if (!mostCommonByte && values[index] < limit ) {
                bytes[index]='1';
            } else {
                bytes[index]='0';
            }
        }
        return new String(bytes);
    }

    public String getMostCommonByteNotEqual() {
        return calcPredicateOnByte((count, limit) -> { return count > limit; });
    }
    public String getLeastCommonByteNotEqual() {
        return calcPredicateOnByte((count, limit) -> { return count < limit; });
    }
    public String getMostCommonByteEqual() {
        return calcPredicateOnByte((count, limit) -> { return count >= limit; });
    }

    public String calcPredicateOnByte(BiPredicate<Integer,Float> condition) {
        char[] bytes = new char[values.length];
        float limit = ((float)count)/2;
        byte value=0;

        for (int index =0 ;index<values.length;index++) {
            bytes[index]=condition.test(values[index], limit)?'1':'0';
        }
        return new String(bytes);
    }

    public void accumulateAll(Stream<String> values) {
        values.forEach(s -> accumulate(s));
    }
    public Collection<String> filterValues(Stream<String> values, int index, BiPredicate<Integer,Float> condition) {
        Collection<String> valuesArray = values.collect(Collectors.toCollection(() -> new ArrayList<String>()));
        reset();
        valuesArray.stream().forEach(s -> accumulate(s));

        String filter = calcPredicateOnByte(condition);

        return valuesArray.stream().filter(s -> s.charAt(index) == filter.charAt(index)).collect(Collectors.toList());
    }

    public String getOxygenRating(Stream<String> values) {
        return filterValues(values, (count, limit) -> { return count >= limit; });
    }

    public String getCO2ScrubberRating(Stream<String> values) {
        return filterValues(values, (count, limit) -> { return count < limit; });
    }

    public String filterValues(Stream<String> values, BiPredicate<Integer,Float> condition) {
        int index=0;

        Collection<String> matching = filterValues(values, index, condition);
        do {
            index++;
            matching = filterValues(matching.stream(), index, condition);
        } while (matching.size()>1);

        return matching.iterator().next();
    }



}
