package org.rt.advent.twentyone.day6;

import org.junit.jupiter.api.Test;
import org.rt.advent.twentyone.day6.LanternFishBand;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LanternFishBandTest {

    @Test
    public void sample1_should_match() {
        LanternFishBand band = new LanternFishBand();
        band.init(Arrays.stream("3,4,3,1,2".split(",")).mapToInt(Integer::parseInt).toArray());
        assertEquals(26, band.countFishAfterAndDisplay(18));
        band.init(Arrays.stream("3,4,3,1,2".split(",")).mapToInt(Integer::parseInt).toArray());
        assertEquals(5934, band.countFishAfter(80));
    }
    }
