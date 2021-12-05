package org.rt.advent.twentyone.day4;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    final String testInput="78 27 82 68 20\n" +
            "14  2 34 51  7\n" +
            "58 57 99 37 81\n" +
            " 9  4  0 76 45\n" +
            "67 69 70 17 23";


    final String winningBoard ="14 21 17 24  4\n" +
            "10 16 15  9 19\n" +
            "18  8 23 26 20\n" +
            "22 11 13  6  5\n" +
            " 2  0 12  3  7";
    @Test
    public void should_parse() throws IOException {

        Board board = readTest(testInput);
        String[] expect = testInput.split("\\n");

        assertEquals(5, board.rowsAndColumns.length);
        for(int i=0;i<board.rowsAndColumns.length;i++) {
            assertEquals(5, board.rowsAndColumns[i].length);
            assertEquals(expect[i], board.displayRow(i));
        }

    }

    Board readTest(String testInput) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(testInput));
        return  Board.BoardFactory.createFromBuffer(reader);
    }

    @Test
    public void should_calculate_score() throws IOException {

        BufferedReader reader = new BufferedReader(new StringReader(winningBoard));

        Board board = Board.BoardFactory.createFromBuffer(reader);
        Set<Integer> draws = Arrays.stream("7,4,9,5,11,17,23,2,0,14,21,24".split(","))
                .map(Integer::parseInt)
                        .collect(Collectors.toCollection(TreeSet::new));

        assertEquals(188, board.sumUnmarkedNumbers(draws));
        assertEquals(4512, board.getScore(draws, 24));



    }

    @Test
    public void should_win() throws IOException {

        BufferedReader reader = new BufferedReader(new StringReader(winningBoard));

        Board board = Board.BoardFactory.createFromBuffer(reader);
        Set<Integer> draws = Arrays.stream("7,4,9,5,11,17,23,2,0,14,21,24".split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(TreeSet::new));

        assertEquals(true, board.doesBoardWin(draws));
    }

}