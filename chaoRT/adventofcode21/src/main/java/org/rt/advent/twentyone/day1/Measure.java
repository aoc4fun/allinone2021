package org.rt.advent.twentyone.day1;

public class Measure {

    int value=0;
    int count=0;
    public void add(int toAdd) {
        if(isReady()) return;
        value+=toAdd;
        count++;
    }
    public boolean isReady() {
        return count>=3;
    }
    public int value() {
        return value;
    }
}
