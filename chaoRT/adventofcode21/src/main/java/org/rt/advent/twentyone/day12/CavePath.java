package org.rt.advent.twentyone.day12;

import java.util.*;
import java.util.stream.Collectors;

public class CavePath {
    TreeMap<String, Cave> allCaves = new TreeMap<>();

    public void load(String[] pathList) {
        for (String path : pathList) addPath(path);
    }

    public void addPath(String s) {
        String[] path = s.split("-");
        obtainCave(path[0]).addDestination(path[1]);
    }

    public Set<String> getDestinationsOf(String caveName) {
        return obtainCave(caveName).getDestinations();
    }

    public Cave obtainCave(String name) {
        return allCaves.computeIfAbsent(name, s -> new Cave(name, this));
    }

    public Set<String> findAllPath(String start, String end) {
        return findAllPath(start, end, false);
    }

    public Set<String> findAllPath(String start, String end, boolean allowDoubleVisitOnce) {
        Cave startCave = obtainCave(start);
        return startCave.findAllPathTo(end, allowDoubleVisitOnce);

    }

    public class Cave implements Comparable<Cave> {
        static final String START = "start";
        static final String END = "end";

        private final CavePath caveComplex;
        TreeMap<String, Cave> destinations = new TreeMap<>();
        String name;

        public Cave(String name, CavePath caveComplex) {
            this.name = name;
            this.caveComplex = caveComplex;
        }

        public void addDestination(String destination) {
            destinations.put(destination, caveComplex.obtainCave(destination));
            caveComplex.obtainCave(destination).addDestination(this);
        }

        public void addDestination(Cave cave) {
            destinations.put(cave.name, cave);
        }

        public String getName() {
            return name;
        }

        public Set<String> getDestinations() {
            return destinations.descendingKeySet().stream().collect(Collectors.toSet());
        }

        public Set<String> findAllPathTo(String end) {
            return findAllPathTo(end, false);
        }

        public Set<String> findAllPathTo(String end, boolean iHaveTimeToVisitASmallCaveTwice) {
            ArrayList<String> currentPathStep = new ArrayList<>();
            currentPathStep.add(this.name);
            VisitorGuardian guardian = new VisitorGuardian(iHaveTimeToVisitASmallCaveTwice);
            return destinations.values()
                    .stream().map(o -> o.exploreAndReturnPathsTo(end, currentPathStep, guardian))
                    .flatMap(Set::stream).collect(Collectors.toSet());
        }


        private Set<String> exploreAndReturnPathsTo(String end, ArrayList<String> currentPathStep, VisitorGuardian guardian) {
            guardian = VisitorGuardian.copy(guardian);
            if(!guardian.canIVisit(this)) return new TreeSet<>();

            ArrayList<String> currentPathStepNew = new ArrayList<>(currentPathStep);

            if (this.name.equals(end)) {
                currentPathStepNew.add(this.name);
                TreeSet<String> result = new TreeSet<>();
                result.add(buildPath(currentPathStepNew));
                return result;
            }

            TreeSet<String> result = new TreeSet<>();
            currentPathStepNew.add(this.name);

            guardian.addVisitedCaveIfSmall(this);
            result.addAll(exploreNonVisitedDestinationAndGetPath(end, currentPathStepNew, guardian));


            return result;
        }

        private Set<String> exploreNonVisitedDestinationAndGetPath(String end, ArrayList<String> currentPathStep, VisitorGuardian guardian) {
            return this.destinations.values()
                    .stream()
                    .map(c -> c.exploreAndReturnPathsTo(end, currentPathStep, guardian))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }

        private boolean isSmallCave() {
            return this.name.toLowerCase().equals(this.name);
        }

        protected String buildPath(ArrayList<String> currentPathStep) {
            return currentPathStep.stream().collect(Collectors.joining(","));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cave)) return false;
            Cave cave = (Cave) o;
            return Objects.equals(name, cave.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public int compareTo(Cave cave) {
            return this.name.compareTo(cave.name);
        }
    }

    static class VisitorGuardian {
        boolean aSmallCaveWasVisitedTwice = false;
        boolean iHaveTimeToVisitASmallCaveTwice = false;
        protected Set<Cave> visitedCave = new TreeSet<>();

        public VisitorGuardian(boolean iHaveTimeToVisitASmallCaveTwice) {
            this.iHaveTimeToVisitASmallCaveTwice = iHaveTimeToVisitASmallCaveTwice;
        }

        public void addVisitedCaveIfSmall(Cave cave) {
            if (cave.isSmallCave()) {
                this.visitedCave.add(cave);
            }
        }

        public static VisitorGuardian copy(VisitorGuardian original) {
            VisitorGuardian clone = new VisitorGuardian(original.iHaveTimeToVisitASmallCaveTwice);
            clone.visitedCave.addAll(original.visitedCave);
            clone.aSmallCaveWasVisitedTwice = original.aSmallCaveWasVisitedTwice;
            return clone;

        }


        public boolean canIVisit(Cave cave) {
            if(cave.name.equals(Cave.START)) return false; // cannot return to start cave
            if (visitedCave.contains(cave) && cave.isSmallCave()) {
                if (iHaveTimeToVisitASmallCaveTwice) {
                    if (aSmallCaveWasVisitedTwice) return false;
                    aSmallCaveWasVisitedTwice = true;
                    return true;
                }
                return false;
            }
            return true;
        }
    }
}
