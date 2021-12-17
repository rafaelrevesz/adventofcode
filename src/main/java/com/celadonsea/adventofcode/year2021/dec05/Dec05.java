package com.celadonsea.adventofcode.year2021.dec05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Dec05 {

    private static Map<Coord, Integer> vents = new HashMap<>();
    private static final boolean DRAW_ALSO_DIAGONAL = true;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec05/dec05.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                drawLine(line);
            }
        }
        long crosses = vents.values().stream().filter(v -> v > 1).count();
        System.out.println("Result: " + crosses);
        // task1: 5576
        // task2: 18144
    }

    private static void drawLine(String line) {
        String[] lineEnds = line.trim().split(" -> ");
        Coord startCoord = new Coord(lineEnds[0]);
        Coord endCoord = new Coord(lineEnds[1]);
        if (DRAW_ALSO_DIAGONAL || (startCoord.x == endCoord.x || startCoord.y == endCoord.y)) {
            drawLine(startCoord, endCoord);
        }
    }

    private static void drawLine(Coord startCoord, Coord endCoord) {
        int stepX = startCoord.x == endCoord.x ? 0 : (endCoord.x - startCoord.x) / Math.abs(endCoord.x - startCoord.x);
        int stepY = startCoord.y == endCoord.y ? 0 : (endCoord.y - startCoord.y) / Math.abs(endCoord.y - startCoord.y);
        int x = startCoord.x;
        int y = startCoord.y;
        do {
            Coord coordToCheck = new Coord(x, y);
            int point = 0;
            if (vents.containsKey(coordToCheck)) {
                point = vents.get(coordToCheck);
            }
            point++;
            vents.put(coordToCheck, point);

            x += stepX;
            y += stepY;
        } while (x != endCoord.x + stepX || y != endCoord.y + stepY);
    }

    private static class Coord {
        int x;
        int y;
        Coord(String stringCoord) {
            String[] values = stringCoord.split(",");
            x = Integer.parseInt(values[0]);
            y = Integer.parseInt(values[1]);
        }
        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return x == coord.x && y == coord.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
