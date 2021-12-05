package com.celadonsea.adventofcode.year2021.dec05;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dec05 {

    private static Map<Coord, Integer> vents = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec05/sample.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                drawLine(line);
            }
        }
    }

    private static void drawLine(String line) {
        String[] lineEnds = line.trim().split(" -> ");
        Coord startCoord = new Coord(lineEnds[0]);
        Coord endCoord = new Coord(lineEnds[1]);
    }

    private static class Coord {
        int x;
        int y;
        Coord(String stringCoord) {
            String[] values = stringCoord.split(",");
            x = Integer.parseInt(values[0]);
            y = Integer.parseInt(values[1]);
        }
    }
}
