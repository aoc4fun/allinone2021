package org.rt.advent.twentyone.day10;

import org.junit.jupiter.api.Test;
import org.rt.advent.twentyone.day5.Line;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LineSyntaxCherckerTest {

    String sample = "[({(<(())[]>[[{[]{<()<>>\n" +
            "[(()[<>])]({[<{<<[]>>(\n" +
            "{([(<{}[<>[]}>{[]{[(<()>\n" +
            "(((({<>}<{<{<>}{[]{[]{}\n" +
            "[[<[([]))<([[{}[[()]]]\n" +
            "[{[{({}]{}}([{[{{{}}([]\n" +
            "{<[[]]>}<{[{[{[]{()[[[]\n" +
            "[<(<(<(<{}))><([]([]()\n" +
            "<{([([[(<>()){}]>(<<{{\n" +
            "<{([{{}}[<[[[<>{}]]]>[]]";
    String corruptedsamples = "{([(<{}[<>[]}>{[]{[(<()>\n" +
            "[[<[([]))<([[{}[[()]]]\n" +
            "[{[{({}]{}}([{[{{{}}([]\n" +
            "[<(<(<(<{}))><([]([]()\n" +
            "<{([([[(<>()){}]>(<<{{";

    @Test
    public void sample_should_pass()
    {
        String[] corrupted = corruptedsamples.split("\\n");

        LineSyntaxChercker checker = new LineSyntaxChercker();
        Optional<LineSyntaxError> error = checker.checkLine(corrupted[0]);
        assertTrue(error.isPresent());
        LineSyntaxError errorDetails = error.get();
        assertEquals(ChunkChars.SQUARE, errorDetails.expected);
        assertEquals(ChunkChars.CURLY, errorDetails.found);
        assertEquals(26397, checker.analyseLinesAndGetScore(sample.split("\\n")));

    }

    @Test
    public void should_pass_part_two()
    {
        String[] samples = sample.split("\\n");

        LineSyntaxChercker checker = new LineSyntaxChercker();
        Collection<ChunkChars> test = checker.getCloseSequence(samples[0]);


        assertEquals(288957, checker.getCloseSequenceScore(samples[0]));
        assertEquals(288957, checker.analyseLinesAndGetCloseScore(samples));

    }


}