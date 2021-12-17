package com.celadonsea.adventofcode.year2021.dec13;

import com.celadonsea.adventofcode.year2021.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Dec13 {

    private static Set<Point> points = new HashSet<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec13/dec13.txt"))) {
            String line;
            List<Folding> foldings = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty() && !line.startsWith("fold along ")) {
                    points.add(new Point(line));
                } else if (line.startsWith("fold along ")) {
                    foldings.add(new Folding(line.replace("fold along ", "")));
                }
            }
            Set<Point> foldedPoints = new HashSet<>(points);
            boolean first = true;
            for (Folding folding : foldings) {
                foldedPoints = foldedPoints.stream()
                        .filter(folding.filter())
                        .map(folding.map())
                        .collect(Collectors.toSet());
                if (first) {
                    first = false;
                    System.out.println("Size after first folding:" + foldedPoints.size());
                }
            }
            System.out.println("Size after all folding: " + foldedPoints.size());
            print(foldedPoints); // RLBCJGLU
        }

    }

    private static void print(Set<Point> points) {
        int maxX = points.stream().map(p -> p.getX()).max(Integer::compareTo).get();
        int maxY = points.stream().map(p -> p.getY()).max(Integer::compareTo).get();
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (points.contains(new Point(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
    }

    private static class Folding {
        int position;
        String direction;
        Folding(String instruction) {
            direction = instruction.split("=")[0];
            position = Integer.parseInt(instruction.split("=")[1]);
        }
        Predicate<Point> filter() {
            if (direction.equals("x")) {
                return p -> p.getX() != position;
            } else {
                return p -> p.getY() != position;
            }
        }

        Function<Point, Point> map() {
            if (direction.equals("x")) {
                return p -> new Point(p.getX() > position ? position * 2 - p.getX() : p.getX(), p.getY());
            } else {
                return p ->new Point(p.getX(), p.getX() > position ? position * 2 - p.getY() : p.getY());
            }
        }
    }
}
