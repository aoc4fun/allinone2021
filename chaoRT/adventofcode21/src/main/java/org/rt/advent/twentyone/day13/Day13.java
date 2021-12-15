package org.rt.advent.twentyone.day13;

import org.rt.advent.twentyone.day12.CavePath;
import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.util.Iterator;
import java.util.Set;

public class Day13 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
            TransparentPaper paper = TransparentPaperFactory.create(getDayStreamAllLines());
            Iterator<TransparentPaper.FoldInstruction> instruction = paper.getInstructions().iterator();
            TransparentPaper fold = paper.fold(instruction.next());
            return ""+fold.getDotCount();
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day13.class.getSimpleName(),e);
        }
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {
        try {
            TransparentPaper paper = TransparentPaperFactory.create(getDayStreamAllLines());
            Iterator<TransparentPaper.FoldInstruction> instruction = paper.getInstructions().iterator();
            TransparentPaper fold = paper.fold(instruction.next());
            while(instruction.hasNext()) {
                fold=fold.fold(instruction.next());
            }

            return ""+fold.getPaper(System.lineSeparator());
        } catch (Exception e) {
            throw new PuzzleFailedException("no result for puzzle "+ Day13.class.getSimpleName(),e);
        }
    }

    public static void main(String[] args) {
        Day13 day = new Day13();
        runPuzzles(day);
    }
}
