package org.rt.advent.twentyone.day12;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class CavePathTest {
    String sample1="start-A\n" +
            "start-b\n" +
            "A-c\n" +
            "A-b\n" +
            "b-d\n" +
            "A-end\n" +
            "b-end";

    @Test
    void should_load_sample() {
        CavePath path = new CavePath();
        path.load(sample1.split("\\n"));

        Set<String> expected = new TreeSet<>();
        expected.add("c");
        expected.add("start");
        expected.add("b");
        expected.add("end");
        assertEquals(expected,path.getDestinationsOf("A"));
 

    }

    @Test
    void should_find_path() {

        String expectedPath = "start,A,b,A,c,A,end\n" +
                "start,A,b,A,end\n" +
                "start,A,b,end\n" +
                "start,A,c,A,b,A,end\n" +
                "start,A,c,A,b,end\n" +
                "start,A,c,A,end\n" +
                "start,A,end\n" +
                "start,b,A,c,A,end\n" +
                "start,b,A,end\n" +
                "start,b,end";
        creatCaveAndCompareResults(sample1, expectedPath);

    }

    @Test
    void should_find_path_in_larger_sample() {

        String largerCave ="dc-end\n" +
                "HN-start\n" +
                "start-kj\n" +
                "dc-start\n" +
                "dc-HN\n" +
                "LN-dc\n" +
                "HN-end\n" +
                "kj-sa\n" +
                "kj-HN\n" +
                "kj-dc";
        String expectedPath ="start,HN,dc,HN,end\n" +
                "start,HN,dc,HN,kj,HN,end\n" +
                "start,HN,dc,end\n" +
                "start,HN,dc,kj,HN,end\n" +
                "start,HN,end\n" +
                "start,HN,kj,HN,dc,HN,end\n" +
                "start,HN,kj,HN,dc,end\n" +
                "start,HN,kj,HN,end\n" +
                "start,HN,kj,dc,HN,end\n" +
                "start,HN,kj,dc,end\n" +
                "start,dc,HN,end\n" +
                "start,dc,HN,kj,HN,end\n" +
                "start,dc,end\n" +
                "start,dc,kj,HN,end\n" +
                "start,kj,HN,dc,HN,end\n" +
                "start,kj,HN,dc,end\n" +
                "start,kj,HN,end\n" +
                "start,kj,dc,HN,end\n" +
                "start,kj,dc,end";
        creatCaveAndCompareResults(largerCave, expectedPath);

    }
    private void creatCaveAndCompareResults(String largerCave, String expectedPath) {
        creatCaveAndCompareResults(largerCave, expectedPath, false);
    }
    private void creatCaveAndCompareResults(String largerCave, String expectedPath, boolean allowDoubleVisitOnce) {
        Set<String> possible = createCaveAndGetPathFromStartToEnd(largerCave, allowDoubleVisitOnce);


        Set<String> expected = new TreeSet<>();
        expected.addAll(List.of(expectedPath.split("\\n")));

        assertEquals(expected, possible);
    }

    private Set<String> createCaveAndGetPathFromStartToEnd(String largerCave) {
        return createCaveAndGetPathFromStartToEnd(largerCave, false);
    }
    private Set<String> createCaveAndGetPathFromStartToEnd(String largerCave, boolean allowDoubleVisit) {
        CavePath path = new CavePath();
        path.load(largerCave.split("\\n"));

        Set<String> possible = path.findAllPath("start", "end", allowDoubleVisit);
        return possible;
    }

    @Test
    void even_larger_sample_path_count_should_match() {
        String evenLargerSample = "fs-end\n" +
                "he-DX\n" +
                "fs-he\n" +
                "start-DX\n" +
                "pj-DX\n" +
                "end-zg\n" +
                "zg-sl\n" +
                "zg-pj\n" +
                "pj-he\n" +
                "RW-he\n" +
                "fs-DX\n" +
                "pj-RW\n" +
                "zg-RW\n" +
                "start-pj\n" +
                "he-WI\n" +
                "zg-he\n" +
                "pj-fs\n" +
                "start-RW";
        Set<String> possible = createCaveAndGetPathFromStartToEnd(evenLargerSample);
        assertEquals(226, possible.size());

    }

    @Test
    void guardian_should_allow_two_visit() {
        CavePath.VisitorGuardian guardian = new CavePath.VisitorGuardian(true);

        CavePath path = new CavePath();
        path.load(sample1.split("\\n"));
        guardian.addVisitedCaveIfSmall(path.obtainCave("b"));
        assertTrue(guardian.canIVisit(path.obtainCave("b")));
        assertFalse(guardian.canIVisit(path.obtainCave("b"))); // not tree time

        assertTrue(guardian.canIVisit(path.obtainCave("c")));
        guardian.addVisitedCaveIfSmall(path.obtainCave("c"));
        assertFalse(guardian.canIVisit(path.obtainCave("c")));// a small cave was visited twice allready




    }

    @Test
    void sample1_have_lot_more_path_is_double_visit_is_allowed() {
        String expected ="start,A,b,A,b,A,c,A,end\n" +
                "start,A,b,A,b,A,end\n" +
                "start,A,b,A,b,end\n" +
                "start,A,b,A,c,A,b,A,end\n" +
                "start,A,b,A,c,A,b,end\n" +
                "start,A,b,A,c,A,c,A,end\n" +
                "start,A,b,A,c,A,end\n" +
                "start,A,b,A,end\n" +
                "start,A,b,d,b,A,c,A,end\n" +
                "start,A,b,d,b,A,end\n" +
                "start,A,b,d,b,end\n" +
                "start,A,b,end\n" +
                "start,A,c,A,b,A,b,A,end\n" +
                "start,A,c,A,b,A,b,end\n" +
                "start,A,c,A,b,A,c,A,end\n" +
                "start,A,c,A,b,A,end\n" +
                "start,A,c,A,b,d,b,A,end\n" +
                "start,A,c,A,b,d,b,end\n" +
                "start,A,c,A,b,end\n" +
                "start,A,c,A,c,A,b,A,end\n" +
                "start,A,c,A,c,A,b,end\n" +
                "start,A,c,A,c,A,end\n" +
                "start,A,c,A,end\n" +
                "start,A,end\n" +
                "start,b,A,b,A,c,A,end\n" +
                "start,b,A,b,A,end\n" +
                "start,b,A,b,end\n" +
                "start,b,A,c,A,b,A,end\n" +
                "start,b,A,c,A,b,end\n" +
                "start,b,A,c,A,c,A,end\n" +
                "start,b,A,c,A,end\n" +
                "start,b,A,end\n" +
                "start,b,d,b,A,c,A,end\n" +
                "start,b,d,b,A,end\n" +
                "start,b,d,b,end\n" +
                "start,b,end";
        creatCaveAndCompareResults(sample1, expected, true);

    }




    @Test
    void load_one_path() {
        CavePath path = new CavePath();
        path.addPath("start-A");
        path.addPath("start-b");

        Set<String> expected = new TreeSet<>();
        expected.add("b");
        expected.add("A");

        assertEquals(expected,path.getDestinationsOf("start"));

        expected = new TreeSet<>();
        expected.add("start");

        assertEquals(expected,path.getDestinationsOf("b"));
        assertEquals(expected,path.getDestinationsOf("A"));

    }

}