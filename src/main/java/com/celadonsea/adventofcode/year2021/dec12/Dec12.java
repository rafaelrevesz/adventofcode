package com.celadonsea.adventofcode.year2021.dec12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Dec12 {

    private static Map<String, Cave> caves = new HashMap<>();
    private static Cave start;
    private static Set<String> paths = new HashSet<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec12/dec12.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] way = line.split("-");
                Cave cave1 = getOrCreateCave(way[0]);
                Cave cave2 = getOrCreateCave(way[1]);
                cave1.wayTo.add(cave2);
                cave2.wayTo.add(cave1);
            }
        }
        discover("start", start, null);
        System.out.println("Path count: " + paths.size());
        paths.clear();
        Set<Cave> doubleVisitSmallCaves = caves.values().stream().filter(c -> !c.big && c.wayTo.size() > 1 && c != start && !c.end).collect(Collectors.toSet());
        for (Cave smallCave : doubleVisitSmallCaves) {
            System.out.println("DOUBLE VISIT: " + smallCave.id);
            discover("start", start, smallCave);
        }
        System.out.println("Path count: " + paths.size());
    }

    private static void discover(String path, Cave from, Cave doubleVisitableCave) {
        for (Cave nextCave : from.wayTo) {
            if (nextCave.end) {
                paths.add(path + "," + nextCave.id);
            } else if (!nextCave.big ) {
                if (!path.contains(nextCave.id) || (nextCave == doubleVisitableCave && path.split(nextCave.id).length < 3)) {
                    discover(path + "," + nextCave.id, nextCave, doubleVisitableCave);
                }
            } else {
                discover(path + "," + nextCave.id, nextCave, doubleVisitableCave);
            }
        }
    }

    private static Cave getOrCreateCave(String id) {
        Cave cave;
        if (caves.containsKey(id)) {
            cave = caves.get(id);
        } else {
            cave = new Cave(id, id.equals(id.toUpperCase(Locale.ROOT)), id.equals("end"));
            caves.put(id, cave);
            if (start == null && id.equals("start")) {
                start = cave;
            }
        }
        return cave;
    }

    private static class Cave {
        String id;
        boolean big;
        boolean end;
        Set<Cave> wayTo = new HashSet<>();
        Cave(String id, boolean big, boolean end) {
            this.id = id;
            this.big = big;
            this.end = end;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
