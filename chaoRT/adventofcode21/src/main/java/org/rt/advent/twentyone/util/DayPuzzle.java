package org.rt.advent.twentyone.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class DayPuzzle {

    public BufferedReader getDayStream() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(getClass().getSimpleName().toLowerCase()+".txt");
        return new BufferedReader(new InputStreamReader(is));
    }

    public abstract String puzzle1();
    public abstract String puzzle2();

    public static void runPuzzles(DayPuzzle day) {
        try {
            display("Puzzle 1:");
            display(day.puzzle1());
            display("Puzzle 2:");
            display(day.puzzle2());

        } catch(Exception e) {
            System.err.println("Failed");
            e.printStackTrace();
        }
    }
    public static void display(String value) {
        System.out.println(value);
    }
}
