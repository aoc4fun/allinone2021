package org.rt.advent.twentyone.io;

public class FromArrayIntArrayReader implements  IntArrayReader{

    private final int[] intArray;

    public FromArrayIntArrayReader(int[] intArray) {
        this.intArray=intArray;
    }
    @Override
    public int[] read() {
        return intArray.clone();
    }
}
