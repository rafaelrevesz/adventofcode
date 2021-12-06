package com.celadonsea.adventofcode.year2021.dec06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Dec06 {

    private static final int NUMBER_OF_DAYS = 256;

    private static final Map<Integer, BigDecimal> laternFishAges = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec06/dec06.txt"))) {
            line = br.readLine();
        }
        laterfishLife(line.split(","));
        // task 1
        //   sample: 5934
        //   puzzle: 359344
        // task 2:
        //  sample: 26984457539
        //  puzzle: 1629570219571
    }

    private static void laterfishLife(String[] ages) {
        for (int age = 0; age < 9; age++) {
            laternFishAges.put(age, new BigDecimal(0));
        }
        for (String age : ages) {
            int ageKey = Integer.parseInt(age);
            BigDecimal currentCount = laternFishAges.get(ageKey);
            laternFishAges.put(ageKey, currentCount.add(new BigDecimal(1)));
        }
        for (int i = 0; i < NUMBER_OF_DAYS; i++) {
            BigDecimal countOfAge0 = laternFishAges.get(0);
            for (int age = 0 ; age < 8; age++) {
                laternFishAges.put(age, laternFishAges.get(age + 1));
            }
            laternFishAges.put(8, countOfAge0);
            laternFishAges.put(6, laternFishAges.get(6).add(countOfAge0));
        }
        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal ageCounter : laternFishAges.values()) {
            sum = sum.add(ageCounter);
        }
        System.out.println("Result: " + sum.toString());
    }
}
