package com.celadonsea.adventofcode.year2021;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Dec02 {
    public static void main(String[] args) throws IOException {
        positioning();
        extraPositioning();
    }

    private static void positioning() throws IOException {
        System.out.println("Task 1: positioning");
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec02.txt"))) {
            String line;

            Map<String, BiConsumer<Coord, Long>> pilot = new HashMap<>();
            pilot.put("forward", (c, x) -> c.x += x);
            pilot.put("up", (c, y) -> c.y -= y);
            pilot.put("down", (c, y) -> c.y += y);

            Coord coordinate = new Coord();

            while ((line = br.readLine()) != null) {
                System.out.print(line + " - ");
                String[] params = line.split(" ");
                pilot.get(params[0]).accept(coordinate, Long.parseLong(params[1]));
                System.out.println(coordinate);
            }
            System.out.println("Result: " + coordinate.y * coordinate.x);
        }
    }

    private static void extraPositioning() throws IOException {
        System.out.println("Task 2: extra positioning");
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec02.txt"))) {
            String line;

            Map<String, BiConsumer<Coord, Long>> pilot = new HashMap<>();
            pilot.put("forward", (c, x) -> {c.x += x; c.y += (c.aim * x);});
            pilot.put("up", (c, y) -> c.aim -= y);
            pilot.put("down", (c, y) -> c.aim += y);

            Coord coordinate = new Coord();

            while ((line = br.readLine()) != null) {
                System.out.print(line + " - ");
                String[] params = line.split(" ");
                pilot.get(params[0]).accept(coordinate, Long.parseLong(params[1]));
                System.out.println(coordinate);
            }
            System.out.println("Result: " + coordinate.y * coordinate.x);
        }
    }

    private static class Coord {
        long x;
        long y;
        long aim;

        @Override
        public String toString() {
            return "Coord{" + "x=" + x + ", y=" + y + ", aim=" + aim + '}';
        }
    }
}
