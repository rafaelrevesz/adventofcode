package com.celadonsea.adventofcode.year2021.dec15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dec15 {

    private static List<List<Point>> weights = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec15/dec15.txt"))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                weights.add(new ArrayList<>());
                for (int x = 0; x < line.length(); x++) {
                    Point point = new Point();
                    point.number = Integer.parseInt("" + line.charAt(x));
                    if (x == 0 && y == 0) {
                        point.number = 0;
                    }
                    weights.get(y).add(point);
                }
                y++;
            }
        }
        discover(0, 0);
        Point endPoint = weights.get(weights.size() - 1).get(weights.get(0).size() - 1);
        System.out.println("Minimal weight: " + (endPoint.weight + endPoint.number));
    }

    // not complete
    private static void discover(int x, int y) {
        Point right = weights.get(y).get(x + 1);
        Point bottom = weights.get(y + 1).get(x);
        weights.get(y).get(x).visited = true;
        if (!right.visited) {
            weights.get(y).get(x + 1).weight = weights.get(y).get(x).weight + weights.get(y).get(x).number;
            discover(x + 1, y);
        }
        if (!bottom.visited) {
            weights.get(y + 1).get(x).weight = weights.get(y).get(x).weight + weights.get(y).get(x).number;
            discover(x, y + 1);
        }
    }

    private static class Point {
        int weight;
        int number;
        boolean visited = false;

        @Override
        public String toString() {
            return "Point{" +
                    "ownWeight=" + number +
                    ", weight=" + weight +
                    '}';
        }
    }
}
