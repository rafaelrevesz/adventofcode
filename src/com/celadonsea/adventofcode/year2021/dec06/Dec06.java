package com.celadonsea.adventofcode.year2021.dec06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dec06 {

    private static List<LaternFish> school = new ArrayList<>();
    private static List<LaternFish> kindergarten = new ArrayList<>();
    private static final int NUMBER_OF_DAYS = 80;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec06/dec06.txt"))) {
            String line = br.readLine();
            String ages[] = line.split(",");
            for (String age : ages) {
                school.add(new LaternFish(Integer.parseInt(age)));
            }
        }
        for (int i = 0; i < NUMBER_OF_DAYS; i++) {
            school.forEach(LaternFish::nextDay);
            school.addAll(kindergarten);
            kindergarten.clear();
        }
        System.out.println("Result: " + school.size());
        // task 1: 359344
    }

    private static class LaternFish {
        int timer;
        LaternFish(int timer) {
            this.timer = timer;
        }
        void nextDay() {
            if (timer < 0) {
                timer = 6;
            } else if (timer == 0) {
                kindergarten.add(new LaternFish(8));
            }
            timer--;
        }
    }
}
