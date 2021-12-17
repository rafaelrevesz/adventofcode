package com.celadonsea.adventofcode.year2021.dec07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
        List<Integer> list = new ArrayList<>();
        for (int i = min; i <= max; i++) list.add(i);
        int minDistance = list
                .stream()
                .map(target -> crabsPosition.stream().map(p -> Math.abs(target - p)).reduce(0, Integer::sum))
                .min(Integer::compareTo)
                .get();
        int minFuel = list
                .stream()
                .map(target -> crabsPosition.stream().map(p -> Math.abs(target - p)).map(Dec07::gauss).reduce(0, Integer::sum))
                .min(Integer::compareTo)
                .get();
        System.out.println("Min distance: " + minDistance);
        System.out.println("Min fuel: " + minFuel);
    }

    private static int gauss(int n) {
        int result = 0;
        for (int i = 1; i <= n; i++)
            result += i;
        return result;
    }
}
