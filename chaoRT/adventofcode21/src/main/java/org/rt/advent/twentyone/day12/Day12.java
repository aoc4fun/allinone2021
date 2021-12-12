package org.rt.advent.twentyone.day12;

import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.util.Set;

public class Day12 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
            CavePath path = new CavePath();
            path.load(getDayStreamAllLines());
            Set<String> possible = path.findAllPath("start", "end");
            return ""+possible.size();
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day12.class.getSimpleName(),e);
        }
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {
            CavePath path = new CavePath();
            path.load(getDayStreamAllLines());
            Set<String> possible = path.findAllPath("start", "end", true);
            return ""+possible.size();

        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day12.class.getSimpleName(),e);
        }
    }

    public static void main(String[] args) {
        Day12 day = new Day12();
        runPuzzles(day);
    }
}
