package com.celadonsea.adventofcode.year2021.dec17;

import com.celadonsea.adventofcode.year2021.Point;

public class Dec17 {

    private Point topLeft;
    private Point bottomRight;

    public int maxYFire(String input) {
        initTarget(input);
        int yPower = Math.abs(bottomRight.getY()) - 1;
        int maxY = 0;
        for (int y = 1; y <= yPower; y++) {
            maxY += y;
        }
        return maxY;
    }

    public int firePossibilities(String input) {
        initTarget(input);

        int result = 0;
        int maxYPower = Math.abs(bottomRight.getY()) - 1;
        int minYPower = bottomRight.getY();

        for (int x = 1; x <= bottomRight.getX(); x++) {
            for (int y = minYPower; y <= maxYPower; y++) {
                if (testFire(x, y)) {
                    result++;
                }
            }
        }
        return result;
    }

    private boolean testFire(int x, int y) {
        int posX = 0;
        int posY = 0;
        int dx = x;
        int dy = y;
        boolean targetHit = false;
        boolean targetMissed = false;

        while (!targetMissed && !targetHit) {
            posX += dx;
            posY += dy;
            targetMissed = posX > bottomRight.getX() || posY < bottomRight.getY();
            targetHit = posX >= topLeft.getX() && posX <= bottomRight.getX() &&
                    posY <= topLeft.getY() && posY >= bottomRight.getY();
            if (dx > 0) {
                dx--;
            }
            dy--;
        }
        return targetHit;
    }


    private void initTarget(String input) {
        String[] ranges = input.replaceAll("target area: ", "").split(", ");
        String[] xRanges = ranges[0].replaceAll("x=", "").split("\\.\\.");
        String[] yRanges = ranges[1].replaceAll("y=", "").split("\\.\\.");
        int x1 = Integer.parseInt(xRanges[0]);
        int x2 = Integer.parseInt(xRanges[1]);
        int y1 = Integer.parseInt(yRanges[0]);
        int y2 = Integer.parseInt(yRanges[1]);
        topLeft = new Point(Math.min(x1, x2), Math.max(y1, y2));
        bottomRight = new Point(Math.max(x1, x2), Math.min(y1, y2));
    }
}
