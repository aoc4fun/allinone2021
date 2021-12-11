package org.rt.advent.twentyone.day10;

import java.util.*;
import java.util.stream.Collectors;

public class LineSyntaxChercker {
    Stack<ChunkChars> stack = new Stack<ChunkChars>();

    public Optional<LineSyntaxError> checkLine(String line){
        stack = new Stack<ChunkChars>();
        int offset=0;
        for(char c : line.toCharArray()) {
            Optional<ChunkChars> opt = ChunkChars.getMatchingOpenOrClose(c);
            if(opt.isEmpty()) throw  new IllegalArgumentException("invalid char:"+c);
            ChunkChars current = opt.get();
            if(stack.isEmpty()) {
                if(current.getOpen()==c) stack.push(current);
                else throw new IllegalArgumentException("got close instead of open:"+c);
            } else if(current.getOpen()==c) stack.push(current);
            if(current.getClose()==c) {
                if(stack.peek()==current) stack.pop();
                else return Optional.of(new LineSyntaxError(stack.pop(), current, offset));
            }

            offset++;
        }
        return Optional.empty();
    }

    public long analyseLinesAndGetScore(String[] lines) {
        return Arrays.stream(lines)
                .map(line -> checkLine(line))
                .filter(Optional::isPresent).map(Optional::get)
                .mapToLong(error -> error.getFound().getScore()).sum();
    }

    protected Collection<ChunkChars> getCloseSequence(String sample) {
        Optional<LineSyntaxError> possibleError = checkLine(sample);
        if(possibleError.isPresent()) return new ArrayList<>();
        List<ChunkChars> list = new ArrayList<>(stack);
        Collections.reverse(list);
        return list;
    }

    protected long getCloseSequenceScore(String sample) {
        Collection<ChunkChars> closeSequence = getCloseSequence(sample);
        if(closeSequence.size()==0) return -1;

        long result = 0;
        for(ChunkChars c:closeSequence) {
            result*=5;
            result+=c.getNonCompletionScore();
        }
        return result;
    }

    public long analyseLinesAndGetCloseScore(String[] lines) {
        long[] scores = Arrays.stream(lines)
                .mapToLong(line -> getCloseSequenceScore(line))
                .filter(
                        l -> l>0)
                .toArray();


        Arrays.sort(scores);
        if(scores.length%2 == 0) throw new IllegalArgumentException("expect a odd number of line to complete");

        int middle = scores.length/2 ;


        return scores[middle];
    }
}
