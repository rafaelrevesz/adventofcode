package com.celadonsea.adventofcode.year2021.dec09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dec09 {

    private static List<Point> lowPoints = new ArrayList<>();
    private static List<Basin> basins = new ArrayList<>();
    private static List<String> lines = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int result = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec09/dec09.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            String up = i == 0 ? null : lines.get(i - 1);
            String down = i == lines.size() - 1 ? null : lines.get(i + 1);
            result += findLowPoints(up, lines.get(i), down, i);
        }
        for (Point lowPoint : lowPoints) {
            Basin basin = new Basin(lowPoint);
            basins.add(basin);
            mapABasin(lowPoint, basin);
        }
        System.out.println("Result: " + result);
        Collections.sort(basins, (lhs, rhs) -> {
            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
            return Integer.compare(rhs.points.size(), lhs.points.size());
        });
        int basinResult = basins.get(0).points.size() *
                basins.get(1).points.size() *
                basins.get(2).points.size();
        System.out.println("Basin result: " + basinResult);
        // 600
        // 987840
    }

    private static void mapABasin(Point lowPoint, Basin basin) {
        Integer up = getBasinPoint(lowPoint.x, lowPoint.y - 1);
        Integer down = getBasinPoint(lowPoint.x, lowPoint.y + 1);
        Integer left = getBasinPoint(lowPoint.x - 1, lowPoint.y);
        Integer right = getBasinPoint(lowPoint.x + 1, lowPoint.y);
        if (up != null && up > lowPoint.number && !basin.pointExists(lowPoint.x, lowPoint.y - 1)) {
            Point point = new Point(lowPoint.x, lowPoint.y - 1, up);
            basin.points.add(point);
            mapABasin(point, basin);
        }
        if (down != null && down > lowPoint.number && !basin.pointExists(lowPoint.x, lowPoint.y + 1)) {
            Point point = new Point(lowPoint.x, lowPoint.y + 1, down);
            basin.points.add(point);
            mapABasin(point, basin);
        }
        if (left != null && left > lowPoint.number && !basin.pointExists(lowPoint.x - 1, lowPoint.y)) {
            Point point = new Point(lowPoint.x - 1, lowPoint.y, left);
            basin.points.add(point);
            mapABasin(point, basin);
        }
        if (right != null && right > lowPoint.number && !basin.pointExists(lowPoint.x + 1, lowPoint.y)) {
            Point point = new Point(lowPoint.x + 1, lowPoint.y, right);
            basin.points.add(point);
            mapABasin(point, basin);
        }
    }

    private static Integer getBasinPoint(int x, int y) {
        if (x < lines.get(0).length() && x >= 0 && y < lines.size() && y >= 0) {
            int i = Integer.parseInt("" + lines.get(y).charAt(x));
            return i < 9 ? i : null;
        }
        return null;
    }

    private static int findLowPoints(String test1, String test2, String test3, int row) {
        int result = 0;
        if (test2 != null && (test1 != null || test3 != null)) {
            for (int i = 0; i < test2.length(); i++) {
                boolean left = i == 0 || test2.charAt(i - 1) > test2.charAt(i);
                boolean right = i == test2.length() - 1 || test2.charAt(i + 1) > test2.charAt(i);
                boolean up = test1 == null || test1.charAt(i) > test2.charAt(i);
                boolean down = test3 == null || test3.charAt(i) > test2.charAt(i);
                if (left && right && up && down) {
                    int lowPointValue = Integer.parseInt("" + test2.charAt(i));
                    result += lowPointValue + 1;
                    lowPoints.add(new Point(i, row, lowPointValue));
                }
            }
        }
        return result;
    }

    private static class Basin {
        Set<Point> points = new HashSet<>();

        Basin(Point startingPoint) {
            points.add(startingPoint);
        }

        boolean pointExists(int x, int y) {
            boolean exists = false;
            for (Point point : points) {
                exists |= (point.x == x && point.y == y);
            }
            return exists;
        }
    }

    private static class Point {
        int x;
        int y;
        int number;

        Point(int x, int y, int number) {
            this.x = x;
            this.y = y;
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point lowPoint = (Point) o;
            return x == lowPoint.x && y == lowPoint.y;
        }
    }
}
