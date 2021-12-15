package org.rt.advent.twentyone.day14;

import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;



public class Day14 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
            PolymerTemplate template = PolymerTemplate.createFromRule(getDayStreamAllLines());
            return ""+template.applyRuleNTimeAndReturnScore(10);
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day14.class.getSimpleName(),e);
        }
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {
            PolymerTemplateCounter template = PolymerTemplateCounter.createFromRule(getDayStreamAllLines());
            return ""+template.applyRuleNTimeAndReturnScore(40);
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day14.class.getSimpleName(),e);
        }
    }

    public static void main(String[] args) {
        Day14 day = new Day14();
        runPuzzles(day);
    }
}
