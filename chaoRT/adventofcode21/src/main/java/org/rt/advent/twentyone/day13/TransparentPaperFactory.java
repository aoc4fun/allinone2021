package org.rt.advent.twentyone.day13;

import org.rt.advent.twentyone.day5.PointInt;
import org.rt.advent.twentyone.day5.PointIntFactory;

import java.util.ArrayList;

public class TransparentPaperFactory {
    static TransparentPaper create(String[] values) {
        boolean readDots=true;
        ArrayList<PointInt> dotsToDraw = new ArrayList<>();
        ArrayList<TransparentPaper.FoldInstruction> instructions = new ArrayList<>();

        for(String line:values) {
            if(readDots) {
                if(line.isEmpty()) {
                    readDots=false;
                } else {
                    dotsToDraw.add(parsePoint(line));
                }
            } else {
                instructions.add(parseInstruction(line));
            }
        }
        TransparentPaper result=new TransparentPaper();
        result.drawDots(dotsToDraw);
        result.setInstruction(instructions);
        return result;
    }

    private static PointInt parsePoint(String line) {
        return PointIntFactory.createFromString(line);
    }

    private static TransparentPaper.FoldInstruction parseInstruction(String line) {

        String[] assign = line.split(" ")[2].split("=");
        return new TransparentPaper.FoldInstruction(Axe.valueOf(assign[0].toUpperCase()), Integer.valueOf(assign[1]));
    }
}
