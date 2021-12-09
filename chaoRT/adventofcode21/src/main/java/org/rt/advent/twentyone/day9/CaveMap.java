package org.rt.advent.twentyone.day9;

import org.rt.advent.twentyone.day5.Direction;
import org.rt.advent.twentyone.day5.PointInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CaveMap {
    int[][] levels=null;
    int nbRow=0;
    int nbCol=0;

    private CaveMap() {


    }

    public static CaveMap createFromLevels(BufferedReader reader) throws IOException {
        CaveMap result = new CaveMap();

        String line = reader.readLine();
        while (line != null) {
            result.addRow(Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray());
            line = reader.readLine();
        }
        return result;
    }

    public void addRow(int[] row) {
        if( levels==null)  levels=new int[row.length][];
        levels[nbRow++]=row;
        nbCol=row.length;
    }

    public int getLevel(PointInt pos) {
        if(pos.getX() < 0 || pos.getX() >= nbCol) return Integer.MAX_VALUE;
        if(pos.getY() < 0 || pos.getY() >= nbRow) return Integer.MAX_VALUE;

        return levels[pos.getY()][pos.getX()];
    }

    public Collection<RiskLevel> getLocalMins() {
        return IntStream.range(0,nbRow)
                .mapToObj(this::getLocalMinsInLine)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));

    }
    public long getRiskysurfaceSize() {
        return getLocalMins().stream().map(
                RiskLevel::getBassin
        ).mapToInt(Collection::size)
                .mapToLong(Long::valueOf)
                .boxed()
                .sorted(Collections.reverseOrder())
                .limit(3).reduce((l1,l2) -> l1*l2).orElseThrow(() -> new IllegalArgumentException("risky point expected"));
    }

    public Collection<RiskLevel> getLocalMinsInLine(int y) {
            return IntStream.range(0,nbCol)
                    .mapToObj(
                            x -> new RiskLevel(PointInt.PointIntFactory.createFromCoords(x,y),this)
                    ).filter(RiskLevel::isLocalMin).collect(Collectors.toCollection(ArrayList::new));
    }


    class RiskLevel implements  Comparable<RiskLevel> {
        PointInt pos;
        CaveMap map;

        public RiskLevel(PointInt pos, CaveMap map) {
            this.pos = pos;
            this.map = map;
        }

        public boolean isLocalMin() {
            EnumSet<Direction> toTest = Direction.getHortogonal();
            long numberOfLower = toTest.stream().filter(
                    d-> (map.getLevel(pos.next(d)) <= map.getLevel(pos))
            ).count();
            return numberOfLower==0;
        }
        public Collection<RiskLevel> getBassin() {
            EnumSet<Direction> toTest  = Direction.getHortogonal();
            Set<RiskLevel> alreadyFound = new TreeSet<>();

            return toTest.stream().map(
                    d -> this.getOtherNeighbours(d, alreadyFound)
            ).flatMap(Collection::stream).collect(Collectors.toSet());
        }
        private boolean pointInThisDirectionIsTooHigh(Direction d) {
            PointInt nextPos = this.pos.next(d);
            return map.getLevel(nextPos) <9;
        }
        public Collection<RiskLevel> getNeighboursNeighbours(Direction d, Set<RiskLevel> alreadyFound) {
            RiskLevel neighbour = new RiskLevel(pos.next(d), this.map);
            if(!alreadyFound.contains(neighbour)) {
                return neighbour.getOtherNeighbours(d, alreadyFound);
            } else {
                return new ArrayList<>();
            }
        }
        protected Collection<RiskLevel> getOtherNeighbours(Direction from, Set<RiskLevel> alreadyFound) {

            EnumSet<Direction> toTest  = Direction.getHortogonal();
            toTest.remove(from.invert());
            ArrayList<RiskLevel> result = new ArrayList<>();
            if(getLevel(pos)<9) {
                result.add(this);
                alreadyFound.add(this);
            }

            result.addAll(toTest.stream().filter(
                    this::pointInThisDirectionIsTooHigh
            ).map(
                    d -> getNeighboursNeighbours(d, alreadyFound)
            ).flatMap(Collection::stream).collect(Collectors.toSet()));
            return result;
        }

        public int getRiskLevel() {
            return map.getLevel(pos)+1;
        }

        @Override
        public int compareTo(RiskLevel riskLevel) {
            int compare = Integer.compare(pos.getX(), riskLevel.pos.getX());
            if(compare!=0) return compare;
            compare = Integer.compare(pos.getY(), riskLevel.pos.getY());
            return compare;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RiskLevel)) return false;
            RiskLevel riskLevel = (RiskLevel) o;
            return pos.equals(riskLevel.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos);
        }
    }
}
