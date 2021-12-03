package org.rt.advent.twentyone.day2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {

    @Test
    public void testDrive() {
        String[] instructions = {
                "forward 5",
                "down 5",
                "forward 8",
                "up 3",
                "down 8",
                "forward 2"
        };
        Day2 day2= new Day2();
        assertEquals(150, day2.multiplyHorizontalPositionAndDepth(new FromStringInstructionReader(instructions).read()));
    }

    @Test
    public void testDriveWithAim() {
        String[] instructions = {
                "forward 5",
                "down 5",
                "forward 8",
                "up 3",
                "down 8",
                "forward 2"
        };
        Day2 day2= new Day2();
        assertEquals(900, day2.multiplyHorizontalPositionAndDepthWithAim(new FromStringInstructionReader(instructions).read()));
    }
}