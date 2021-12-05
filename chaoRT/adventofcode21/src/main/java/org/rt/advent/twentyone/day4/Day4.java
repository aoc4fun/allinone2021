package org.rt.advent.twentyone.day4;

import org.rt.advent.twentyone.util.DayPuzzle;
import org.rt.advent.twentyone.util.PuzzleFailedException;

import java.io.IOException;

public class Day4 extends DayPuzzle {
    @Override
    public String puzzle1() throws PuzzleFailedException {
        try {
        BingoGame game = getGame();
        game.playGameUntilSomeoneWins();

        return ""+game.getWinners().iterator().next().getScore(game.currentDraws, game.getLastDraw());
        } catch (NoWinnerException | IOException e) {
           throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    public BingoGame getGame() throws IOException {
        return BingoGameFactory.createFromReader(this.getDayStream());
    }

    @Override
    public String puzzle2() throws PuzzleFailedException {

        try {
            BingoGame game = getGame();
            game.playGameUntilThereIsOnlyOneLooserLeft();
            Board lastLooser = game.getNonWiningBoard().iterator().next();
            game.nextRound();


            return ""+lastLooser.getScore(game.currentDraws, game.getLastDraw());
        } catch (NoWinnerException | IOException e) {
            throw new PuzzleFailedException("no result for puzzle 1",e);
        }
    }

    public static void main(String[] args) {
        Day4 day = new Day4();
        runPuzzles(day);
    }
}
