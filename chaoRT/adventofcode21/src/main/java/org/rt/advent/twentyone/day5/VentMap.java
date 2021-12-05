package org.rt.advent.twentyone.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VentMap {
    private static final int MAP_SIZE = 1000;
    int[][] map = new int[MAP_SIZE][MAP_SIZE];

    private VentMap() {
        Arrays.stream(map).forEach(this::clearLine);
    }

    private void clearLine(int[] line) {
        for (int index = 0; index < line.length; index++) line[index] = 0;
    }

    public void drawLine(Line line) {
        Direction direction = line.getDirection();
        int length = line.getLength();
        PointInt current = line.source;
        for(int step = 0;step <=length;step++) {
            drawPoint(current);
            current=current.next(direction);
        }
    }

    private void drawPoint(PointInt point) {
        map[point.getX()][point.getY()]++;
    }

    private void drawFromLeftToRight(int y, int start, int end) {
        for (int x = start; x <= end; x++) {
            map[x][y]++;
        }
    }

    private void drawFromTopToBottom(int x, int start, int end) {
        for (int y = start; y <= end; y++) {
            map[x][y]++;
        }
    }

    public int countPointWhereTwoPointsOverlaps() {
        return Arrays.stream(map).mapToInt(line -> (int) (Arrays.stream(line).filter(i -> i > 1).count())).sum();
    }

    static class VentMapFactory {
        private VentMapFactory() {
            super();
        }

        static VentMap createFromReader(BufferedReader reader, Predicate<Line> drawIf) throws IOException {
            VentMap result = new VentMap();


            for (Line line = readLineFromReader(reader); line != null; line = readLineFromReader(reader)) {
                if (drawIf.test(line)) {
                    result.drawLine(line);
                }
            }

            return result;
        }

        static VentMap createEmpty() {
            VentMap result = new VentMap();

            return result;
        }
    }

    static Line readLineFromReader(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line != null) {
            return Line.LineFactory.createFromDef(line);
        }
        return null;
    }


    public String getMapAsString(int range) {
        StringBuilder result = new StringBuilder();
        Arrays.stream(map).limit(range)
                .forEach(line -> {
                    result.append(showLine(line, range));
                    result.append(System.lineSeparator());
                });
        return result.toString();
    }

    public String showLine(int[] line, int range) {
        return Arrays.stream(line)
                .limit(range).
                mapToObj(String::valueOf).collect(Collectors.joining(""));
    }

}
