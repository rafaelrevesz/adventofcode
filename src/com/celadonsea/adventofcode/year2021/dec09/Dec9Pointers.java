package com.celadonsea.adventofcode.year2021.dec09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Dec9Pointers {

    private static final List<Ref> lowPoints = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        Ref leftTop = null;
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec09/dec09.txt"))) {
            String line;

            Ref prevLineLeft = null;
            while ((line = br.readLine()) != null) {
                Ref prev = null;
                Ref currentRowLeft = null;
                Ref up = prevLineLeft;
                for (int x = 0; x < line.length(); x++) {
                    Ref ref = new Ref(Integer.parseInt("" + line.charAt(x)));
                    ref.left = prev;
                    if (prev != null) {
                        prev.right = ref;
                    }
                    if (currentRowLeft == null) {
                        currentRowLeft = ref;
                    }
                    if (x == 0 && leftTop == null) {
                        leftTop = ref;
                    }
                    if (up != null) {
                        ref.up = up;
                        up.down = ref;
                        up = up.right;
                    }
                    prev = ref;
                }
                prevLineLeft = currentRowLeft;
            }
        }
        leftTop.searchLowPoint();
        System.out.println("Lower points: " + lowPoints.stream().map(p -> p.number + 1).reduce(0, Integer::sum));

        lowPoints.forEach(p -> p.searchBasin(p));

        List<Integer> collect = lowPoints.stream().map(p -> p.basin.size() + 1).collect(Collectors.toList());
        Collections.sort(collect);
        Collections.reverse(collect);
        System.out.println("Basins: " + (collect.get(0) * collect.get(1) * collect.get(2)));
        // 600
        // 987840
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - startTime));
    }

    private static class Ref {
        Ref up;
        Ref down;
        Ref left;
        Ref right;
        int number;
        Set<Ref> basin = new HashSet<>();

        Ref(int number) {
            this.number = number;
        }

        void searchLowPoint() {
            if ((up == null || up.number > number) && (down == null || down.number > number)
                    && (left == null || left.number > number) && (right == null || right.number > number)) {
                lowPoints.add(this);
            }
            if (left == null && down != null) {
                down.searchLowPoint();
            }
            if (right != null) {
                right.searchLowPoint();
            }
        }
        void searchBasin(Ref lowerPoint) {
            checkBasin(lowerPoint, up);
            checkBasin(lowerPoint, down);
            checkBasin(lowerPoint, left);
            checkBasin(lowerPoint, right);
        }
        void checkBasin(Ref lowerPoint, Ref direction) {
            if (direction != null && direction.number > number && direction.number < 9) {
                if (!lowerPoint.basin.contains(direction)) {
                    lowerPoint.basin.add(direction);
                    direction.searchBasin(lowerPoint);
                }
            }
        }
    }
}
