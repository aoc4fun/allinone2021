package org.rt.advent.twentyone.day4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class BingoGame {
    Collection<Board> boards=new ArrayList<>();
    int[] draws;
    Set<Integer> currentDraws=new TreeSet<>();
    int index=0;

    Collection<Board> winners=new TreeSet<>();


    protected void nextRound() throws NoWinnerException {
        if(index>=draws.length) throw new NoWinnerException("no winner after "+index+" turns");
        int draw = draws[index++];
        currentDraws.add(draw);
        winners=boards.stream().filter(b -> b.doesBoardWin(currentDraws)).collect(Collectors.toCollection(ArrayList::new));
    }
    public int getLastDraw() {
        if(index==0) throw new IllegalStateException("game did not start yet !");
        return draws[index-1];
    }
    public boolean doWeHaveAWinner() {
        return !winners.isEmpty();
    }
    public boolean onlyOneNonWinningBoardRemains() {
        return winners.size()==boards.size()-1;
    }
    public Collection<Board> getNonWiningBoard() {
        return boards.stream().filter(b -> !b.doesBoardWin(currentDraws)).collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<Board> getWinners() {
        return new ArrayList<>(winners);
    }
    public void playGameUntilSomeoneWins() throws NoWinnerException {
        while(!doWeHaveAWinner()) nextRound();
    }

    public void playGameUntilThereIsOnlyOneLooserLeft() throws NoWinnerException {
        while(!onlyOneNonWinningBoardRemains()) nextRound();
    }


}
