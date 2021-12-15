package org.rt.advent.twentyone.day13;

import org.rt.advent.twentyone.day5.PointInt;
import org.rt.advent.twentyone.day5.PointIntFactory;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TransparentPaper {
    final int DOT=1;
    int[][] dots;

    Set<PointInt> myDots=new TreeSet<>();
    int maxX;
    int maxY;
    private Collection<FoldInstruction> instructions;

    public void drawDots(Collection<PointInt> dotsToDraw) {
        drawDots(dotsToDraw, null);
    }
    public void drawDots(Collection<PointInt> dotsToDraw, PointInt newSize) {
        if(newSize==null) initMaximums(dotsToDraw);
        else initMaximums(List.of(newSize));

        this.dots=new int[maxX+1][];
        initRows();
        dotsToDraw.stream().forEach(dot -> setValue(dot, DOT));


        this.myDots.addAll(dotsToDraw);
    }

    private void initRows() {
        IntStream.rangeClosed(0,maxX).forEach(this::initRow);
    }
    private void initRow(int x) {
        dots[x]=new int[maxY+1];
        IntStream.rangeClosed(0,maxY).forEach(y -> dots[x][y]=0);
    }

    public long getDotCount() {
        return myDots.size();
    }

    public int getValue(PointInt pos) {
        return dots[pos.getX()][pos.getY()];
    }
    public int getValue(int x, int y ) {
        return dots[x][y];
    }
    public void setValue(PointInt pos ,int value) {
        if(pos.getX()<0 || pos.getX()>maxX) return;
        if(pos.getY()<0 || pos.getY()>maxY) return;
        dots[pos.getX()][pos.getY()]=value;
    }

    private void initMaximums(Collection<PointInt> dotsToDraw) {
        maxX = getMax(dotsToDraw,d -> d.getX());
        maxY = getMax(dotsToDraw,d -> d.getY());
    }

    public int getMax(Collection<PointInt> dots, ToIntFunction<PointInt> mapToInt) {
        return dots.stream().mapToInt(mapToInt).max().getAsInt();
    }

    public String getPaper(String lineSep) {
        StringBuilder result = new StringBuilder();
        for(int y=0;y<=maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                result.append(getValue(x,y)==1?"#":".");
            }
            result.append(lineSep);
        }
        return result.toString();
    }


    static class FoldInstruction {
        Axe foldAxe;
        int position;

        public FoldInstruction(Axe foldAxe, int position) {
            this.foldAxe = foldAxe;
            this.position = position;
        }

        public PointInt applyTransformation(PointInt original) {
            switch(foldAxe) {
                case Y:
                    int y = transform(original.getY());
                    return PointIntFactory.createFromCoords(original.getX(), y);
                case X:
                    int x = transform(original.getX());
                    return PointIntFactory.createFromCoords(x, original.getY());
            }
            throw new IllegalArgumentException("unknown fold axe"+foldAxe);
        }

        public int transform(int orig) {
            if(orig>position)  return (2*position-orig);
            if(orig==position) throw new IllegalArgumentException("no dot on fold line");
            else return orig;
        }
        public boolean isNotFoldLine(PointInt point) {
            switch(foldAxe) {
                case Y:
                   return point.getY()!=position;
                case X:
                    return point.getX()!=position;
            }
            throw new IllegalArgumentException("unknown fold axe"+foldAxe);
        }

        public PointInt newSize(int maxX, int maxY) {
            switch(foldAxe) {
                case Y:
                    return PointIntFactory.createFromCoords(maxX, (maxY/2));
                case X:
                    return PointIntFactory.createFromCoords((maxX/2), maxY);
            }
            throw new IllegalArgumentException("unknown fold axe"+foldAxe);
        }
    }

    public TransparentPaper fold(FoldInstruction instruction) {
        TransparentPaper result = new TransparentPaper();

        result.drawDots(this.myDots
                .stream()
                .filter(
                instruction::isNotFoldLine
                ).map(instruction::applyTransformation)
                .collect(Collectors.toSet()), instruction.newSize(maxX, maxY));

        return result;
    }

    public void setInstruction(ArrayList<FoldInstruction> instructions) {
        this.instructions=instructions;
    }

    public Collection<FoldInstruction> getInstructions() {
        return instructions;
    }

}
