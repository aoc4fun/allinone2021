package org.rt.advent.twentyone.day15;

import org.junit.jupiter.api.Test;
import org.rt.advent.twentyone.day5.PointInt;
import org.rt.advent.twentyone.day5.PointIntFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {

    String sample = "1163751742\n" +
            "1381373672\n" +
            "2136511328\n" +
            "3694931569\n" +
            "7463417111\n" +
            "1319128137\n" +
            "1359912421\n" +
            "3125421639\n" +
            "1293138521\n" +
            "2311944581";

    @Test
    public void pathFinderShouldMatch() throws IOException {
        PathFinder finder = new PathFinder();
        finder.loadMap(new BufferedReader(new StringReader(sample)));
        assertEquals(1, finder.riskLevels.getLevel(new PointInt(0,0)));
        assertEquals(1, finder.riskLevels.getLevel(new PointInt(9,9)));
        assertEquals(8, finder.riskLevels.getLevel(new PointInt(8,9)));

        assertEquals(40,
                finder.findBestPath(PointIntFactory.createFromCoords(0,0),
                PointIntFactory.createFromCoords(9,9))
        );
    }

    @Test
    public void pathFinderShouldMatchNewMode() throws IOException {
        PathFinder finder = new PathFinder();
        finder.loadMap(new BufferedReader(new StringReader(sample)),5);
        assertEquals(2, finder.riskLevels.getLevel(new PointInt(10,0)));
        assertEquals(3, finder.riskLevels.getLevel(new PointInt(9,10)));
        assertEquals(3, finder.riskLevels.getLevel(new PointInt(10,10)));

        assertEquals(315,
                finder.findBestPath(PointIntFactory.createFromCoords(0,0),
                        PointIntFactory.createFromCoords(49,49))
        );
    }

}