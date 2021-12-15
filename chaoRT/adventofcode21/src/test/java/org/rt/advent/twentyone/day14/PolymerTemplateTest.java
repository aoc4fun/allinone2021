package org.rt.advent.twentyone.day14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolymerTemplateTest {

    @Test
    void testSimpleInsert() {
        PolymerTemplate.Atom list = PolymerTemplate.Atom.createFromString("AC");
        list.insert(new PolymerTemplate.Atom('B'));
        assertEquals("ABC", list.toString());
    }

    PolymerTemplate getSample1() {
        String sample ="NNCB\n" +
                "\n" +
                "CH -> B\n" +
                "HH -> N\n" +
                "CB -> H\n" +
                "NH -> C\n" +
                "HB -> C\n" +
                "HC -> B\n" +
                "HN -> C\n" +
                "NN -> C\n" +
                "BH -> H\n" +
                "NC -> B\n" +
                "NB -> B\n" +
                "BN -> B\n" +
                "BB -> N\n" +
                "BC -> B\n" +
                "CC -> N\n" +
                "CN -> C";

        return PolymerTemplate.createFromRule(sample.split("\n"));

    }
    PolymerTemplate getSample1Counter() {
        String sample ="NNCB\n" +
                "\n" +
                "CH -> B\n" +
                "HH -> N\n" +
                "CB -> H\n" +
                "NH -> C\n" +
                "HB -> C\n" +
                "HC -> B\n" +
                "HN -> C\n" +
                "NN -> C\n" +
                "BH -> H\n" +
                "NC -> B\n" +
                "NB -> B\n" +
                "BN -> B\n" +
                "BB -> N\n" +
                "BC -> B\n" +
                "CC -> N\n" +
                "CN -> C";

        return PolymerTemplateCounter.createFromRule(sample.split("\n"));

    }
    @Test
    void should_match_sample() {
        PolymerTemplate test = getSample1();

        assertEquals("NNCB", test.toString());

        test.applyRules();

        assertEquals("NCNBCHB", test.toString());
        test.applyRules();
        assertEquals("NBCCNBBBCBHCB", test.toString());
        test.applyRules();
        assertEquals("NBBBCNCCNBBNBNBBCHBHHBCHB", test.toString());
        test.applyRules();
        assertEquals("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB", test.toString());
    }

    @Test
    void should_match_score() {

        assertEquals(1588, getSample1Counter().applyRuleNTimeAndReturnScore(10));
    }
    @Test
    void should_match_score_after_40iterations() {

        assertEquals(2188189693529L, getSample1Counter().applyRuleNTimeAndReturnScore(40));
    }

}