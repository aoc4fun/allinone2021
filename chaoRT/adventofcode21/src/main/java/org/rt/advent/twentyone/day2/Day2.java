package org.rt.advent.twentyone.day2;

import org.rt.advent.twentyone.util.DayPuzzle;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.Collection;

public class Day2 extends DayPuzzle {

    MoveReader reader;
    private Position pos;

    public Day2() {
        reader = new FromStreamMoveInstructionReader( this::getDayStream);
    }
    @Override
    public String puzzle1() {
        return ""+multiplyHorizontalPositionAndDepth(reader.read());
    }

    @Override
    public String puzzle2() {
        return ""+new BigDecimal(multiplyHorizontalPositionAndDepthWithAim(reader.read()));
    }

    public double multiplyHorizontalPositionAndDepth(Collection<MoveInstruction> instructions) {
        pos = new Position(0,0,0);
        return processInstructionAndMultiplyPositionAndDepth(instructions,Day2::nextPos);
    }

    public double multiplyHorizontalPositionAndDepthWithAim(Collection<MoveInstruction> instructions) {
        pos = new Position(0,0,0);
        return processInstructionAndMultiplyPositionAndDepth(instructions,Day2::nextPostWithAim);
    }

    private double processInstructionAndMultiplyPositionAndDepth(Collection<MoveInstruction> instructions, NextPositionProvider provider) {
        for(MoveInstruction move:instructions) {
            pos=provider.nextPost(pos,move);
        }
        return pos.getX()*pos.getY();
    }

    private Position processInstruction(Collection<MoveInstruction> instructions, Position pos, NextPositionProvider provider) {
        for(MoveInstruction instruction : instructions) {
            pos =provider.nextPost(pos,instruction);
        }
        return pos;
    }
    interface NextPositionProvider {
        Position nextPost(Position actual, MoveInstruction move);
    }

    protected static Position nextPos(Position actual, MoveInstruction move) {
        switch(move.getDirection()) {
            case FORWARD:
                actual.setLocation(actual.getX()+(double) move.getSteps(), actual.getY());
                return actual;
            case UP:
                actual.setLocation(actual.getX(), actual.getY()-(double) move.getSteps());
                return actual;
            case DOWN:
                actual.setLocation(actual.getX(), actual.getY() +(double) move.getSteps());
                return actual;
            default: throw new IllegalArgumentException("unknown direction:"+ move.getDirection());
        }
    }

    protected static Position nextPostWithAim(Position actual, MoveInstruction move) {
        switch(move.getDirection()) {
            case FORWARD:
                double newDepth = actual.getY() + actual.getAim()*move.getSteps();
                actual.setLocation(actual.getX()+(double) move.getSteps(), newDepth);
                return actual;
            case UP:
                actual.setAim(actual.getAim() - move.getSteps());
                return actual;
            case DOWN:
                actual.setAim(actual.getAim() + move.getSteps());
                return actual;
            default: throw new IllegalArgumentException("unknown direction:"+ move.getDirection());
        }
    }



    public static void main(String[] args) {
        Day2 day2 = new Day2();
        runPuzzles(day2);
    }



    class Position extends Point2D {
        Point2D.Double pos;
        double aim;

        public Position(double x, double y, double aim) {
            pos=new Point2D.Double(x,y);
            this.aim=aim;
        }

        @Override
        public double getX() {
            return pos.getX();
        }

        @Override
        public double getY() {
            return pos.getY();
        }


        public double getAim() {
            return aim;
        }

        public void setAim(double value) {
           this.aim=value;
        }

        @Override
        public void setLocation(double x, double y) {
            pos.setLocation(x,y);
        }
    }
}
