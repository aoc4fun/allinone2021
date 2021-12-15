package org.rt.advent.twentyone.day14;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolymerTemplateCounter extends PolymerTemplate {
    TreeMap<Pair,Long> count=new TreeMap<>();

    char last;


    public void initTemplate(String template) {
        countTemplateAndInit(template);
        last=template.charAt(template.length()-1);
    }
    public void countTemplateAndInit(String toConvert) {
        char last='#';
        for(char c : toConvert.toCharArray()) {
            if(last!='#') {
                Pair p = new Pair(last,c);
                count.compute(p, (pair,value) -> value==null?1:value+1);
            }
            last=c;
        }
    }
    void applyRule(Rule rule, TreeMap<Pair, Long> next) {

        Pair rulePair = Pair.fromRule(rule);
        if(count.containsKey(rulePair)) {
            Long occurrenceCount = count.get(rulePair);

            Pair[] pairsAfter = Pair.afterRule(rule);
            next.compute(pairsAfter[0], (p,value) -> incIfExists(value, occurrenceCount));
            next.compute(pairsAfter[1], (p,value) -> incIfExists(value, occurrenceCount));
        }
    }
    Long incIfExists(Long value, Long toAdd) {
        return value==null?toAdd:value+toAdd;
    }

    @Override
    void applyRules() {
        TreeMap<Pair, Long> next = new TreeMap<>();
        rules.stream().forEach(rule -> applyRule(rule, next));
        this.count=next;
    }

    @Override
    long applyRuleNTimeAndReturnScore(int time) {
        IntStream.range(0,time).forEach(
                i -> {
                    applyRules();
                    calculateScore();
                });
        return calculateScore();
    }

    @Override
    long calculateScore() {
        Map<Character, Long> computeScores=new TreeMap<>();
        count.entrySet().forEach(
                (entry) -> {
                    computeScores.compute(entry.getKey().start, (c,v) -> incIfExists(v, entry.getValue()));
                }
        );

        computeScores.compute(Character.valueOf(last), (key,val) -> val+1);

        long max = computeScores.values().stream().max(Long::compare).get();
        long min = computeScores.values().stream().min(Long::compare).get();



        return max - min;
    }

    static PolymerTemplateCounter createFromRule(String[] rule) {
        PolymerTemplateCounter result = new PolymerTemplateCounter();
        boolean first=true;

        for(String line:rule) {
            if(first) {
                first=false;
                result.initTemplate(line);
            } else {
                if(!line.isEmpty()) {
                    result.addRule(PolymerTemplate.Rule.createFrom(line));
                }
            }
        }
        return result;
    }


    static class Pair implements Comparable<Pair> {
        char start;
        char end;

        public Pair(char start, char end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return start == pair.start && end == pair.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
        static Pair fromRule(PolymerTemplate.Rule rule) {
            return new Pair(rule.firstElem, rule.secondElem);
        }
        static Pair[] afterRule(PolymerTemplate.Rule rule) {
            Pair[] pair = new Pair[2];
            pair[0]=new Pair(rule.firstElem, rule.toInsert);
            pair[1]=new Pair(rule.toInsert, rule.secondElem);
            return pair;
        }

        @Override
        public int compareTo(Pair pair) {
            int result = Character.compare(start, pair.start);
            if(result!=0) return result;
            return Character.compare(end, pair.end);
        }

        public String toString() {
            return start+""+end;
        }
    }
}
