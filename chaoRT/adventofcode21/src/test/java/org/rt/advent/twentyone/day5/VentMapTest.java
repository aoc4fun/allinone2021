package org.rt.advent.twentyone.day5;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class VentMapTest {

    @Test
    public void should_draw_a_line() throws IOException {
        VentMap tested = VentMap.VentMapFactory.createEmpty();
        tested.drawLine(Line.LineBuilder.from(1,0).to(9,0));

        BufferedReader read = new BufferedReader(new StringReader(tested.getMapAsString(10, System.lineSeparator())));
        assertEquals("0111111111", read.readLine());


    }

}