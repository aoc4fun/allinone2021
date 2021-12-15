package org.rt.advent.twentyone.day7;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CrabsBandInSubmarineTest {

    @Test
    public void shoudld_match_exempleOne() {
        int[] values = Arrays.stream("16,1,2,0,4,2,7,1,2,14".split(",")).mapToInt(Integer::parseInt).toArray();
        CrabsBandInSubmarine band = new CrabsBandInSubmarine();
        band.addCrabs(values);


        int minConsoTarget  = band.calcMinimalFuelCost();
        assertEquals(2, minConsoTarget);
        assertEquals(37, band.calcFuelCost(2));

    }

    @Test
    public void shoudld_match_exempleTwo() {
        int[] values = Arrays.stream("16,1,2,0,4,2,7,1,2,14".split(",")).mapToInt(Integer::parseInt).toArray();
        CrabsBandInSubmarine band = new CrabsBandInSubmarine();
        band.addCrabs(values);


        int minConsoTarget  = band.calcMinimalRealFuelCost();


        assertEquals(66, band.calcOneMove(16,5));
        assertEquals(15, band.calcOneMove(0,5));

        assertEquals(168, band.calcRealFuelCost(5));
        assertEquals(5, minConsoTarget);

    }

}