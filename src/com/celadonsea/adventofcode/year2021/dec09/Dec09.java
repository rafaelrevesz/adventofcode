package com.celadonsea.adventofcode.year2021.dec09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dec09 {

    public static void main(String[] args) throws IOException {
        List<String> lines = new ArrayList<>();
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
            result += findLowPoints(up, lines.get(i), down);
        }
        System.out.println("Result: " + result);
        // 15
        // 600
    }

    private static int findLowPoints(String test1, String test2, String test3) {
        int result = 0;
        if (test2 != null && (test1 != null || test3 != null)) {
            for (int i = 0; i < test2.length(); i++) {
                boolean left = i == 0 || test2.charAt(i - 1) > test2.charAt(i);
                boolean right = i == test2.length() - 1 || test2.charAt(i + 1) > test2.charAt(i);
                boolean up = test1 == null || test1.charAt(i) > test2.charAt(i);
                boolean down = test3 == null || test3.charAt(i) > test2.charAt(i);
                if (left && right && up && down) {
                    result += Integer.parseInt("" + test2.charAt(i)) + 1;
                }
            }
        }
        return result;
    }

}
