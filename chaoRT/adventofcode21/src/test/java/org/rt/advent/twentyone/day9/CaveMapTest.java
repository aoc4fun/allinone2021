package org.rt.advent.twentyone.day9;

import org.junit.jupiter.api.Test;
import org.rt.advent.twentyone.day5.PointIntFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CaveMapTest {
    String line =  System.lineSeparator();
    String sample = "2199943210" + line +
            "3987894921" + line +
            "9856789892" + line +
            "8767896789" + line +
            "9899965678";

    private CaveMap getSampleMap() throws IOException {
        return CaveMap.createFromLevels(new BufferedReader(new StringReader(sample)));
    }
    @Test
    void should_match_sample() throws IOException {



        CaveMap map = getSampleMap();
        assertEquals(2, map.getLevel(PointIntFactory.createFromCoords(0,0)));
        assertEquals(1, map.getLevel(PointIntFactory.createFromCoords(1,0)));
        assertEquals(3, map.getLevel(PointIntFactory.createFromCoords(0,1)));
        Collection<CaveMap.RiskLevel> levels  = map.getLocalMins();
        assertEquals(4, levels.size());
        assertEquals(15, levels.stream().mapToLong(CaveMap.RiskLevel::getRiskLevel).sum());


    }

    @Test
    void equal_should_be_removed() throws IOException {
        String line =  System.lineSeparator();
        String testLevels = "2299943210" + line +
                "2987894921" + line +
                "9856789892" + line +
                "8767896789" + line +
                "9899965678";
        CaveMap map = CaveMap.createFromLevels(new BufferedReader(new StringReader(testLevels)));
        Collection<CaveMap.RiskLevel> levels  = map.getLocalMins();
        CaveMap.RiskLevel firstMatch = levels.stream().findFirst().get();

        assertEquals(1, firstMatch.getRiskLevel());

    }

    @Test
    void test_bassin_size() throws IOException {
        String line =  System.lineSeparator();
        CaveMap map = getSampleMap();

        Collection<CaveMap.RiskLevel> lowPoints = map.getLocalMins();
        var iterator = lowPoints.iterator();

        assertEquals(3, iterator.next().getBassin().size());
        assertEquals(9, iterator.next().getBassin().size());
        assertEquals(14, iterator.next().getBassin().size());
        assertEquals(9, iterator.next().getBassin().size());
        assertEquals(1134, map.getRiskysurfaceSize());


    }


}