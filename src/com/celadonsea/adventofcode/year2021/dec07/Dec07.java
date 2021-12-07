package com.celadonsea.adventofcode.year2021.dec07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Dec07 {

    public static void main(String[] args) throws IOException {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec07/dec07.txt"))) {
            line = br.readLine();
        }
        crabsMove(Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
        // task 1
        //   sample: 37
        //   puzzle: 336040
        // task 2:
        //  sample: 168
        //  puzzle: 94813675
    }

    private static void crabsMove(List<Integer> crabsPosition) {
        int min = crabsPosition.stream().mapToInt(i -> i).min().getAsInt();
        int max = crabsPosition.stream().mapToInt(i -> i).max().getAsInt();
        long minDistance = Long.MAX_VALUE;
        long minFuel = Long.MAX_VALUE;
        int minDistancePosition = 0;
        int minFuelPosition = 0;
        for (int target = min; target <= max; target++) {
            long distance = 0;
            long fuel = 0;
            for (int pos : crabsPosition) {
                distance += Math.abs(target - pos);
                fuel += gauss(Math.abs(target - pos));
            }
            if (minDistance >= distance) {
                minDistance = distance;
                minDistancePosition = target;
            }
            if (minFuel >= fuel) {
                minFuel = fuel;
                minFuelPosition = target;
            }
            minFuel = Math.min(minFuel, fuel);
        }
        System.out.println("Min distance (" + minDistancePosition + "): " + minDistance);
        System.out.println("Min fuel (" + minFuelPosition + "): " + minFuel);
    }

    private static long gauss(long n) {
        long result = 0;
        for (long i = 1; i <= n; i++)
            result += i;
        return result;
    }
}
