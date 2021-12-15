package org.rt.advent.twentyone.day15;

import org.rt.advent.twentyone.day5.PointInt;
import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Set;
import java.util.TreeSet;


public class Day15 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
            PathFinder finder = new PathFinder();
            finder.loadMap(getDayStream());
            int result = finder.findBestPath(new PointInt(0,0), new PointInt(99,99));
            Set<PointInt> route = new TreeSet<>();


            route.addAll(finder.getShortestPath(new PointInt(0,0), new PointInt(99,99)));
            finder.displayPath(route);
            return ""+result;
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day15.class.getSimpleName(),e);
        }
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {
            PathFinder finder = new PathFinder();
            finder.loadMap(getDayStream(),5);
            return ""+finder.findBestPath(new PointInt(0,0), new PointInt(499,499));
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day15.class.getSimpleName(),e);
        }
    }

    public static void main(String[] args) {
        try {
            Day15 day = new Day15();
            runPuzzles(day);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
