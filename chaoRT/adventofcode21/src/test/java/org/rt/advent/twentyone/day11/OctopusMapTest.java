package org.rt.advent.twentyone.day11;

import org.junit.jupiter.api.Test;
import org.rt.advent.twentyone.day5.PointInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class OctopusMapTest {
    final String sample = "5483143223\n" +
            "2745854711\n" +
            "5264556173\n" +
            "6141336146\n" +
            "6357385478\n" +
            "4167524645\n" +
            "2176841721\n" +
            "6882881134\n" +
            "4846848554\n" +
            "5283751526";

    @Test
    public void should_process_small_sample_properly() throws IOException {
        String smallTest = "11111\n" +
                "19991\n" +
                "19191\n" +
                "19991\n" +
                "11111";
        OctopusMap map = createFromString(smallTest);
        String afterStep1 = "34543\n" +
                "40004\n" +
                "50005\n" +
                "40004\n" +
                "34543\n";
        String afterStep2 = "45654\n" +
                "51115\n" +
                "61116\n" +
                "51115\n" +
                "45654\n";



        Set<OctopusMap.EnergyLevel> readyToFlash = map.getOctopusWithLevel(9);
        assertEquals(8, readyToFlash.size());

        Set<OctopusMap.EnergyLevel> thisStepFlashes = map.applyStepRuleAndReturnOctoThatFlashed();
        assertEquals(9,thisStepFlashes.size());

        assertEquals(afterStep1, map.getMapAsString("\n"));
        thisStepFlashes = map.applyStepRuleAndReturnOctoThatFlashed();
        assertEquals(0,thisStepFlashes.size());

        assertEquals(afterStep2, map.getMapAsString("\n"));

    }

    @Test
    public void propagation_test() throws IOException {
        String smallTest =  "91118\n" +
                            "18181\n" +
                            "11811\n" +
                            "18181\n" +
                            "81118";
        OctopusMap map = createFromString(smallTest);
        String afterStep1 = "04440\n" +
                            "40504\n" +
                            "45054\n" +
                            "40504\n" +
                            "04440\n";



        Set<OctopusMap.EnergyLevel> readyToFlash = map.getOctopusWithLevel(9);
        assertEquals(1, readyToFlash.size());

        Set<OctopusMap.EnergyLevel> thisStepFlashes = map.applyStepRuleAndReturnOctoThatFlashed();
        assertEquals(9,thisStepFlashes.size());

        assertEquals(afterStep1, map.getMapAsString("\n"));
    }

    @Test
    void simple_increment () throws IOException {


        OctopusMap map = createFromString(sample);
        map.incrementEnergyLevelOfOctopuses();
        String afterStep1 ="6594254334\n" +
                "3856965822\n" +
                "6375667284\n" +
                "7252447257\n" +
                "7468496589\n" +
                "5278635756\n" +
                "3287952832\n" +
                "7993992245\n" +
                "5957959665\n" +
                "6394862637\n";

        assertEquals(afterStep1, map.getMapAsString("\n"));

    }
    @Test
    void first_sample_should_match () throws IOException {
        String sample = "5483143223\n" +
                "2745854711\n" +
                "5264556173\n" +
                "6141336146\n" +
                "6357385478\n" +
                "4167524645\n" +
                "2176841721\n" +
                "6882881134\n" +
                "4846848554\n" +
                "5283751526";

        OctopusMap map = createFromString(sample);

        String afterStep1 ="6594254334\n" +
                "3856965822\n" +
                "6375667284\n" +
                "7252447257\n" +
                "7468496589\n" +
                "5278635756\n" +
                "3287952832\n" +
                "7993992245\n" +
                "5957959665\n" +
                "6394862637\n";

        assertEquals(0,map.flashAndCount(1));
        assertEquals(afterStep1, map.getMapAsString("\n"));
        assertEquals(13,map.getOctopusWithLevel(9).stream().count());

        String afterstep2= "8807476555\n" +
                "5089087054\n" +
                "8597889608\n" +
                "8485769600\n" +
                "8700908800\n" +
                "6600088989\n" +
                "6800005943\n" +
                "0000007456\n" +
                "9000000876\n" +
                "8700006848\n";

        long countAfterStep2 = map.flashAndCount(1);
        assertEquals(afterstep2, map.getMapAsString("\n"));
        assertEquals(35,countAfterStep2);


        assertEquals(204, map.flashAndCount(8));

        map = createFromString(sample);
        assertEquals(1656, map.flashAndCount(100));
    }

    @Test
    void should_all_flash_at_195() throws IOException {
        OctopusMap map = createFromString(sample);
        assertEquals(195, map.getNextFullFlash());

    }


    private OctopusMap createFromString(String sample) throws IOException {
        return OctopusMap.createFromLevels(new BufferedReader(new StringReader(sample)));

    }

}