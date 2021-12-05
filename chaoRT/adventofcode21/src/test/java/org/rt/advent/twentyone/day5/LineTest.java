package org.rt.advent.twentyone.day5;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    public void should_be_vertical_and_strait() {
        Line line = Line.LineBuilder.from(1,1).to(1,10);
        assertTrue(line.isVertical());
        assertTrue(line.isStrait());
    }

    @Test
    public void should_be_horizontal_and_strait() {
        Line line = Line.LineBuilder.from(1,1).to(9,1);
        assertTrue(line.isHorizonal());
        assertTrue(line.isStrait());
    }
    @Test
    public void should_not_be_strait() {
        Line line = Line.LineBuilder.from(1,1).to(9,9);
        assertTrue(!line.isStrait());
    }

    @Test
    public void should_parse_properly() {
        Line line = Line.LineBuilder.from(0,9).to(5,9);
        Line line2 = Line.LineFactory.createFromDef("0,9 -> 5,9");
        assertEquals(line, line2);

    }

    @Test
    public void should_point_to_est_and_be_8() {
        Line line = Line.LineBuilder.from(1,1).to(9,1);
        assertEquals(Direction.EST, line.getDirection());
        assertEquals(8, line.getLength());

    }
    @Test
    public void should_point_to_south_est_and_be_8() {
        Line line = Line.LineBuilder.from(1,1).to(9,9);
        assertEquals(Direction.SE, line.getDirection());
        assertEquals(8, line.getLength());
    }


}