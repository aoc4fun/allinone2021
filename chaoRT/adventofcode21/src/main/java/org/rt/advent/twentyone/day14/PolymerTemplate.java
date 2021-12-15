package org.rt.advent.twentyone.day14;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolymerTemplate {
    Atom first;
    Collection<Rule> rules= new ArrayList<>();

    public void initTemplate(String template) {
        first = Atom.createFromString(template);
    }

    void addRule(Rule rule) {
        rules.add(rule);
    }
    public Collection<InsertAction> generateAllInsert() {
        return rules.stream()
                .map(this::generateInsert)
                .flatMap(Collection::stream).collect(Collectors.toList());
    }
    public Collection<InsertAction> generateInsert(Rule rule) {
        return getAllAtoms().stream()
                .map(rule::createIfMatch)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
    public Collection<Atom> getAllAtoms() {
        ArrayList<Atom> result = new ArrayList<>();
        Atom current = first;
        while(current!=null) {
            result.add(current);
            current=current.next;
        }
        return result;
    }
    void applyRules() {
        Collection<InsertAction> allInsert = generateAllInsert();
        allInsert.stream().parallel().forEach(InsertAction::apply);
    }
    long applyRuleNTimeAndReturnScore(int time) {
        IntStream.range(0,time).forEach(
                i -> applyRules()
        );
        return calculateScore();
    }
    long calculateScore() {
        Map<Character, Long> result = getAllAtoms().stream().collect(Collectors.groupingBy(
                Atom::getValue, Collectors.counting())
        );
        return result.values().stream().max(Long::compare).orElseThrow() - result.values().stream().min(Long::compare).orElseThrow();
    }

    @Override
    public String toString() {
        return first.toString();
    }

    static PolymerTemplate createFromRule(String[] rule) {
        PolymerTemplate result = new PolymerTemplate();
        boolean first=true;

        for(String line:rule) {
            if(first) {
                first=false;
                result.initTemplate(line);
            } else {
                if(!line.isEmpty()) {
                    result.addRule(Rule.createFrom(line));
                }
            }
        }
        return result;
    }

    static class Rule {
        char firstElem;
        char secondElem;
        char toInsert;

        public Rule(char firstElem, char secondElem, char toInsert) {
            this.firstElem = firstElem;
            this.secondElem = secondElem;
            this.toInsert = toInsert;
        }

        public Optional<InsertAction> createIfMatch(Atom atom) {
            if(atom.value==firstElem
            && atom.next!=null && atom.next.value==secondElem) {
                return Optional.of(new InsertAction(toInsert, atom));
            }
            return Optional.empty();
        }

        static Rule createFrom(String ruleDef) {
            String[] parts = ruleDef.split(" -> ");
            String[] match = parts[0].split("");

            return new Rule(match[0].charAt(0),match[1].charAt(0), parts[1].charAt(0));
        }
    }
    static class InsertAction {
        char value;
        Atom anchor;

        InsertAction(char value, Atom anchor) {
            this.value = value;
            this.anchor = anchor;
        }

        void apply() {
            Atom toAdd = new Atom(value);
            anchor.insert(toAdd);
        }
    }

    static class Atom {
        char value;
        Atom next;

        public Atom(char value) {
            this.value=value;
        }

        public Atom setNext(Atom next) {
            this.next=next;
            return next;
        }
        public char getValue() {
            return value;
        }

        public void insert(Atom atom) {
            atom.next=this.next;
            this.next=atom;
        }

        static Atom createFromString(String value) {
            Atom result=null;
            Atom current=null;

            for(Atom atom: value.codePoints()
                    .mapToObj(c -> (char)c)
                    .map(Atom::new).collect(Collectors.toList())) {
                if(result==null) {
                    result = atom;
                    current = atom;
                } else {
                    current = current.setNext(atom);
                }
            }
            return result;
        }
        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            this.appendTo(str);
            return str.toString();

        }
        void appendTo(StringBuilder b) {
            b.append(value);
            if(next!=null) next.appendTo(b);
        }
    }
}
