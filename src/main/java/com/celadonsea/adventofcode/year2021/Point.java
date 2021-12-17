package com.celadonsea.adventofcode.year2021;

import java.util.Objects;

public class Point {
    private int x;
    private int y;

    public Point(String line) {
        x = Integer.parseInt(line.split(",")[0]);
        y = Integer.parseInt(line.split(",")[1]);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
