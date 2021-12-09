package com.celadonsea.adventofcode.year2021.dec09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dec09 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec09/dec09.txt"))) {
            String line;
            int result = 0;
            String test1;
            String test2 = null;
            String test3 = null;

            while ((line = br.readLine()) != null) {
                test1 = test2;
                test2 = test3;
                test3 = line;
                result += findLowPoints(test1, test2, test3);
            }
            result += findLowPoints(test2, test3, null);
            System.out.println("Result: " + result);
        }
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
