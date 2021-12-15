package org.rt.advent.twentyone.day15;

import org.rt.advent.twentyone.day5.Direction;
import org.rt.advent.twentyone.day5.PointInt;

import java.util.*;
import java.util.stream.Collectors;

class RiskLevel implements Comparable<RiskLevel> {
    PointInt pos;
    CaveMap map;

    public RiskLevel(PointInt pos, CaveMap map) {
        this.pos = pos;
        this.map = map;
    }

    public boolean isLocalMin() {
        EnumSet<Direction> toTest = Direction.getHortogonal();
        long numberOfLower = toTest.stream().filter(
                d -> (map.getLevel(pos.next(d)) <= map.getLevel(pos))
        ).count();
        return numberOfLower == 0;
    }


    public int getRiskLevel() {
        return map.getLevel(pos);
    }

    @Override
    public int compareTo(RiskLevel riskLevel) {
        int compare = Integer.compare(pos.getX(), riskLevel.pos.getX());
        if (compare != 0) return compare;
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
