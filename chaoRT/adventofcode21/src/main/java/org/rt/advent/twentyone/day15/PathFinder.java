package org.rt.advent.twentyone.day15;

import org.rt.advent.twentyone.day5.Direction;
import org.rt.advent.twentyone.day5.PointInt;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PathFinder {

    // shortestPath to go to - from
    Map<PointInt, PointDistance> distanceFromStart = new TreeMap<>();
    CaveMap riskLevels;



    Set<Path> newPathToTest = new TreeSet<>();

    PointInt target;

    public void loadMap(BufferedReader reader) throws IOException {
        loadMap(reader, 1);
    }

    public void loadMap(BufferedReader reader, int multiplier) throws IOException {
        this.riskLevels=CaveMap.createFromLevels(reader, multiplier);
    }
    public int findBestPath(PointInt start, PointInt end) {
        calculateDistances(start);
        return distanceFromStart.get(end).distance;
    }
    public Collection<PointInt> getShortestPath(PointInt start, PointInt end) {
        Stack<PointInt> path = new Stack<>();
        path.push(end);
        PointInt current = end;
        while(!current.equals(start)) {
            final PointInt finalCurrent = current;
            Collection<PointDistance> neighboursDistances = Direction.getHortogonal().stream()
                    .map(d -> finalCurrent.next(d))
                    .map(distanceFromStart::get)
                    .filter(p -> p!=null)
                    .collect(Collectors.toList());
            PointInt predecessor  = neighboursDistances.stream()
                    .sorted(Comparator.comparingInt(p -> p.distance))
                    .iterator().next().pos;
            path.push(predecessor);
            current=predecessor;
        }
        return path;
    }

    /**
     * uses Dijkstra Algorithm
     * @param start
     */
    public void calculateDistances(PointInt start) {
        this.target=target;
        PriorityQueue<PointDistance> toAssert = new PriorityQueue<>(Comparator.comparingInt(p -> p.distance));
        toAssert.add(new PointDistance(start, 0));
        distanceFromStart.put(start, new PointDistance(start, 0));


        while(!toAssert.isEmpty()) {
            PointDistance toTest = toAssert.poll();
            Direction.getHortogonal()
                    .stream()
                    .map(d -> toTest.pos.next(d))
                    .filter(p -> !p.equals(toTest.pos))
                    .filter(riskLevels::isInMap)
                    .forEach(p -> {
                        int distance = toTest.distance+riskLevels.getLevel(p);
                        PointDistance pastResult = distanceFromStart.get(p);
                        if(pastResult != null && pastResult.distance>distance) {
                            toAssert.remove(pastResult);
                        }
                        if(pastResult == null || pastResult.distance>distance) {// put new min
                            distanceFromStart.put(p, new PointDistance(p, distance));
                            toAssert.add(new PointDistance(p, distance));
                        }
                    });
        }

    }

    public void displayPath(Set<PointInt> route) {
        System.out.println(this.riskLevels.displayMap(route));
    }

    class PointDistance {
        PointInt pos;
        int distance;

        public PointDistance(PointInt pos, int distance) {
            this.pos = pos;
            this.distance=distance;
        }
    }
}
