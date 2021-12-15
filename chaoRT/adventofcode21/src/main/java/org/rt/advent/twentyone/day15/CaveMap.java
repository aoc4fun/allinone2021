package org.rt.advent.twentyone.day15;

import org.rt.advent.twentyone.day5.Direction;
import org.rt.advent.twentyone.day5.PointInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CaveMap {
    int[][] levels=null;
    int nbRow=0;
    int nbCol=0;

    int sizeMultiplier=1;

    private CaveMap() {

    }
    private CaveMap(int sizeMultiplier) {
        this.sizeMultiplier=sizeMultiplier;
    }

    public static CaveMap createFromLevels(BufferedReader reader, int multiplier) throws IOException {
        CaveMap result = new CaveMap(multiplier);

        String line = reader.readLine();
        while (line != null) {
            result.addRow(Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray());
            line = reader.readLine();
        }
        return result;
    }

    public void addRow(int[] row) {
        if( levels==null)  levels=new int[row.length][];
        levels[nbRow++]=row;
        nbCol=row.length;
    }

    public int getLevel(PointInt pos) {
        if(isOutOfBound(pos)) return Integer.MAX_VALUE;
        int modX = pos.getX()/nbCol;
        int modY = pos.getY()/nbRow;
        int x = pos.getX()-nbCol*modX;
        int y = pos.getY()-nbRow*modY;
        int val = levels[y][x];
        val+=modX+modY;
        val=val>9?val-9:val;

        return val;
    }

    private boolean isOutOfBound(PointInt pos) {

        if (pos.getX() < 0 || pos.getX()>= nbCol*sizeMultiplier ) return true;
        if (pos.getY() < 0 || pos.getY() >= nbRow*sizeMultiplier) return true;

        return false;
    }


    public boolean isInMap(PointInt pointInt) {
        return !isOutOfBound(pointInt);
    }

    public String displayMap(Set<PointInt> highlighted) {
        StringBuilder displayMap = new StringBuilder();

        IntStream.range(0,nbCol*sizeMultiplier).forEach(
                x -> {
                    IntStream.range(0,nbRow*sizeMultiplier).forEach(y -> {
                        PointInt toDraw = new PointInt(x,y);
                        if(highlighted.contains(toDraw)) {
                            displayMap.append(ConsoleColors.ANSI_BLUE);
                        } else {
                            displayMap.append(ConsoleColors.ANSI_YELLOW);
                        }
                        displayMap.append(getLevel(toDraw));
                    });
                    displayMap.append(System.lineSeparator());
                }
        );
        return displayMap.toString();
    }
}
