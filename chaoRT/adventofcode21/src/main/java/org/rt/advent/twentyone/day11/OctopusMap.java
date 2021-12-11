package org.rt.advent.twentyone.day11;

import org.rt.advent.twentyone.day5.Direction;
import org.rt.advent.twentyone.day5.PointInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OctopusMap {

    int[][] levels=null;
    int nbRow=0;
    int nbCol=0;
    private long flashCount;

    private OctopusMap() {
        resetFlashCount();
    }


    public void addRow(int[] row) {
        if( levels==null)  levels=new int[row.length][];
        levels[nbRow++]=row;
        nbCol=row.length;
    }

    public int getLevel(PointInt pos) {
        if(pos.getX() < 0 || pos.getX() >= nbCol) return Integer.MIN_VALUE;
        if(pos.getY() < 0 || pos.getY() >= nbRow) return Integer.MIN_VALUE;

        return levels[pos.getX()][pos.getY()];
    }

    public static OctopusMap createFromLevels(BufferedReader reader) throws IOException {
        OctopusMap result = new OctopusMap();

        String line = reader.readLine();
        while (line != null) {
            result.addRow(Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray());
            line = reader.readLine();
        }

        return result;
    }

    public void resetFlashCount() {
        this.flashCount=0L;
    }

    public long flashAndCount(int count) {

        for(int i=0;i<count;i++) {
            flashAndCount();
        }
        return flashCount;
    }

    public String getMapAsString(String lineSep) {
        StringBuilder result = new StringBuilder();
        IntStream.range(0,nbRow)
                .forEach(row -> {
                    result.append(showLine(levels[row]));
                    result.append(lineSep);
                });
        return result.toString();
    }

    public String showLine(int[] line) {
        return IntStream.range(0,nbCol)
                .mapToObj(col ->  String.valueOf(line[col]))
                .collect(Collectors.joining(""));
    }

    public void flashAndCount() {
        flashCount+= applyStepRuleAndReturnOctoThatFlashed().size();
    }

    public Set<EnergyLevel> applyStepRuleAndReturnOctoThatFlashed() {
        incrementEnergyLevelOfOctopuses();
        Set<EnergyLevel> flashed = findOctopusThatFlashesAndCascade();
        consumeEnergyOfOctopusThatFlashed(flashed);
        limitEnergy();
        return flashed;
    }

    protected void incrementEnergyLevelOfOctopuses() {
        getAllOctopuses().forEach(EnergyLevel::incLevel);
    }

    protected Set<EnergyLevel> findOctopusThatFlashesAndCascade() {
        Set<EnergyLevel> octoReadToFlash = getOctopusWithLevel(EnergyLevel.MAX_VALUE);
        Set<EnergyLevel> flashed = new TreeSet<>();

        while(!octoReadToFlash.isEmpty()) {
            flashed.addAll(octoReadToFlash);
            octoReadToFlash =
                    octoReadToFlash.stream()
                            .map(octo -> octo.flashAndGetNewOctoReadyToFlash(flashed))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toSet());
        }
        return flashed;
    }

    private void consumeEnergyOfOctopusThatFlashed(Set<EnergyLevel> flashed) {
        flashed.stream().forEach(EnergyLevel::consumeEnergy);
    }
    private void limitEnergy() {
        getAllOctopuses().forEach(EnergyLevel::limitEnergy);
    }

    public Set<EnergyLevel> getOctopusWithLevel(int targetLevel) {
        return getAllOctopuses()
                .filter(octo -> octo.getEnergyLevel()==targetLevel)
                .collect(Collectors.toSet());
    }
    private Stream<EnergyLevel> getAllOctopuses() {
        return IntStream.range(0, nbRow).mapToObj(this::getOctopusInRow).flatMap(Function.identity());
    }
    private Stream<EnergyLevel> getOctopusInRow(int y) {
        return IntStream.range(0, nbCol)
                .mapToObj(x -> getEnergyLevelAt(PointInt.PointIntFactory.createFromCoords(x,y))
                );
    }

    public EnergyLevel getEnergyLevelAt(PointInt pos) {
        return new EnergyLevel(pos, this);
    }

    public int getNextFullFlash() {
        int result =1;
        while(applyStepRuleAndReturnOctoThatFlashed().size()<100) {
            result++;
            if(result>100000) throw new IllegalStateException("it's too long no ?");
        };
        return result;

    }


    class EnergyLevel implements  Comparable<EnergyLevel> {
        public static final int MAX_VALUE = 10;
        PointInt pos;
        OctopusMap map;

        public EnergyLevel(PointInt pos, OctopusMap map) {
            this.pos = pos;
            this.map = map;
        }

        protected Collection<EnergyLevel> flashAndGetNewOctoReadyToFlash(Set<EnergyLevel> alreadyFlashed) {
            incrementNeighboursLevels();
            return collectNewFlasher(alreadyFlashed);
        }

        protected Collection<EnergyLevel> collectNewFlasher(Set<EnergyLevel> alreadyFlashed) {

            return getAllNeighbourStream()
                    .filter(Predicate.not(alreadyFlashed::contains))
                    .filter(octo -> {
                        return octo.isReadyToFlash();
                    })
                    .collect(Collectors.toSet());
        }

        private Stream<EnergyLevel> getAllNeighbourStream() {
            return EnumSet.allOf(Direction.class)
                    .stream().map(this::getNeighbour);
        }

        private void incrementNeighboursLevels() {
            for(EnergyLevel neibourg:getAllNeighbourStream().collect(Collectors.toSet())) {
                neibourg.incLevel();
            }
        }

        public void incLevel() {

            map.incrementEnergyLevelAt(pos);

        }

        @Override
        public String toString() {
            return "("+pos.getX()+","+pos.getY()+") -> "+this.map.getLevel(this.pos);
        }

        public boolean isReadyToFlash() {
            return map.getLevel(pos)>=MAX_VALUE;
        }
        public EnergyLevel getNeighbour(Direction d) {
            return map.getEnergyLevelAt(pos.next(d));
        }

        public int getEnergyLevel() {
            return map.getLevel(pos);
        }

        @Override
        public int compareTo(EnergyLevel energyLevel) {
            int compare = Integer.compare(pos.getX(), energyLevel.pos.getX());
            if(compare!=0) return compare;
            compare = Integer.compare(pos.getY(), energyLevel.pos.getY());
            return compare;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EnergyLevel)) return false;
            EnergyLevel EnergyLevel = (EnergyLevel) o;
            return pos.equals(EnergyLevel.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos);
        }

        public void consumeEnergy() {
            map.consumeEnergy(this.pos);
        }
        public void limitEnergy() {
            map.limitEnergy(this.pos);
        }

    }

    private void limitEnergy(PointInt pos) {
        if(levels[pos.getX()][pos.getY()]>EnergyLevel.MAX_VALUE-1) levels[pos.getX()][pos.getY()]=EnergyLevel.MAX_VALUE-1;
    }

    private void consumeEnergy(PointInt pos) {
        levels[pos.getX()][pos.getY()]=0;
    }

    private void incrementEnergyLevelAt(PointInt pos) {
        if(isOutOfBound(pos)) return;


        int val = this.levels[pos.getX()][pos.getY()];
        val++;
        this.levels[pos.getX()][pos.getY()]=val;

        limitEnergyLevelAt(pos);

    }



    private boolean isOutOfBound(PointInt pos) {
        if(pos.getX() < 0 || pos.getX() >= nbCol) return true;
        if(pos.getY() < 0 || pos.getY() >= nbRow) return true;
        return false;
    }

    public void limitEnergyLevelAt(PointInt pos) {
        if(levels[pos.getX()][pos.getY()]>EnergyLevel.MAX_VALUE) {
            levels[pos.getX()][pos.getY()]=EnergyLevel.MAX_VALUE;
        }
    }

}
