package org.rt.advent.twentyone.day13;

import org.junit.jupiter.api.Test;
import org.rt.advent.twentyone.day5.PointInt;
import org.rt.advent.twentyone.day5.PointIntFactory;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TransparentPaperTest {
    String sample= "6,10\n" +
            "0,14\n" +
            "9,10\n" +
            "0,3\n" +
            "10,4\n" +
            "4,11\n" +
            "6,0\n" +
            "6,12\n" +
            "4,1\n" +
            "0,13\n" +
            "10,12\n" +
            "3,4\n" +
            "3,0\n" +
            "8,4\n" +
            "1,10\n" +
            "2,14\n" +
            "8,10\n" +
            "9,0\n" +
            "\n" +
            "fold along y=7\n" +
            "fold along x=5";

    @Test
    void should_parse() {
        TransparentPaper paper = TransparentPaperFactory.create(sample.split("\n"));
        assertEquals(18L, paper.getDotCount());
        assertEquals(2, paper.getInstructions().size());

    }

    @Test
    void should_match_sample_1() {
        TransparentPaper paper = TransparentPaperFactory.create(sample.split("\n"));
        assertEquals(2, paper.getInstructions().size());
        Iterator<TransparentPaper.FoldInstruction> instruction = paper.getInstructions().iterator();
        TransparentPaper folded = paper.fold(instruction.next());
        assertEquals(17, folded.getDotCount());

        folded = folded.fold(instruction.next());
        assertEquals(16, folded.getDotCount());

        String result = "#####.\n" +
                        "#...#.\n" +
                        "#...#.\n" +
                        "#...#.\n" +
                        "#####.\n" +
                        "......\n" +
                        "......\n" +
                        "......\n";
        assertEquals(result, folded.getPaper("\n"));
    }


    @Test
    void test_fold() {
        TransparentPaper.FoldInstruction test = new TransparentPaper.FoldInstruction(Axe.Y, 7);
        PointInt folded = test.applyTransformation(PointIntFactory.createFromCoords(0,14));
        assertEquals(0, folded.getX());
        assertEquals(0, folded.getY());

        folded = test.applyTransformation(PointIntFactory.createFromCoords(3,14));
        assertEquals(3, folded.getX());
        assertEquals(0, folded.getY());


    }
}