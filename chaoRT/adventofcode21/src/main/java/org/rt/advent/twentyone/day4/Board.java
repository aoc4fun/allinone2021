package org.rt.advent.twentyone.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private static final int BOARD_SIZE = 5;
    int[][] rowsAndColumns= new int[BOARD_SIZE][];

    private Board() {

    }
    public  int sumUnmarkedNumbers(Set<Integer> currentDraws){
        return Arrays.stream(rowsAndColumns)
                .map(row ->
                     Arrays.stream(row)
                        .filter(draw -> !(currentDraws.contains(draw)))
                        .reduce(0, Integer::sum)
                ).reduce(0, Integer::sum);
    }
    public int getScore(Set<Integer> currentDraws, int lastDraw) {
        return sumUnmarkedNumbers(currentDraws)*lastDraw;
    }

    public boolean doesBoardWin(Set<Integer> currentDraws) {
        return isThereAWiningRow(currentDraws) || isThereAWiningColumn(currentDraws);
    }
    protected boolean isThereAWiningRow(Set<Integer> currentDraws){
        return Arrays.stream(rowsAndColumns)
                .filter(row -> isThisAWiningSet(row,currentDraws))
                .toArray().length>0;
    }
    protected boolean isThisAWiningSet(int[] draws, Set<Integer> currentDraws){
            int countOfNonWining = Arrays.stream(draws)
                    .filter(draw -> !(currentDraws.contains(draw)))
                    .toArray().length;
            return countOfNonWining==0;
    }
    protected boolean isThereAWiningColumn(Set<Integer> currentDraws){
        for(int colIndex=0;colIndex<BOARD_SIZE;colIndex++) {
            if(isThisAWiningSet(buildColumn(colIndex), currentDraws)) return true;
        }
        return false;
    }
    public int[] buildColumn(int colIndex) {
        int[] result = new int[BOARD_SIZE];
        for(int rowIndex=0;rowIndex<BOARD_SIZE;rowIndex++) {
            result[rowIndex]=rowsAndColumns[rowIndex][colIndex];
        }
        return result;
    }

    public String displayRow(int rowIndex) {
        return Arrays.stream(rowsAndColumns[rowIndex]).sequential()
                .mapToObj(Board::formatBoardNumber)
                .collect(Collectors.joining(" "));
    }

    static String formatBoardNumber(int number) {
        if(number<0) throw new IllegalArgumentException("expect positive integer");
        return (number<10?" ":"")+String.valueOf(number);
    }
    static class BoardFactory {
        private BoardFactory() {
        }

        static Board createFromBuffer(BufferedReader reader) throws IOException {
            Board result = new Board();
            for(int rowIndex=0;rowIndex<result.rowsAndColumns.length;rowIndex++) {
                result.rowsAndColumns[rowIndex] = readRow(reader);
            }
            return result;
        }
        private static int[] readRow(BufferedReader reader) throws IOException {
            String line = reader.readLine();
            return Arrays.stream(line.split(" ")).filter(s -> s.length()>0).mapToInt(Integer::parseInt).toArray();
        }
    }
}
