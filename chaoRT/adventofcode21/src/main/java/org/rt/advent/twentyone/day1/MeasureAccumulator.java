package org.rt.advent.twentyone.day1;

import org.rt.advent.twentyone.io.IntArrayReader;

import java.util.ArrayList;
import java.util.LinkedList;

public class MeasureAccumulator implements IntArrayReader
{
    private final IntArrayReader source;

    @Override
    public int[] read() {
        LinkedList<Measure> buffer = new LinkedList<Measure>();

        ArrayList<Integer> finalMeasure = new ArrayList<>();
        int index=0;
        for(int bip:source.read()) {
            buffer.addLast(new Measure());
            for(Measure measure : buffer) {
                measure.add(bip);
            }
            if(buffer.getFirst().isReady()) finalMeasure.add(buffer.removeFirst().value());
        }

        return finalMeasure.stream().mapToInt(i->i).toArray();

    }

    public MeasureAccumulator(IntArrayReader source) {
        this.source=source;
    }
}
