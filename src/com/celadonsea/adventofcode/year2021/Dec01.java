package com.celadonsea.adventofcode.year2021;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dec01 {

    public static void main(String[] args) throws IOException {
        System.out.println("2021 dec. 1.");
        sonar();
        windowMeasurements();
    }

    private static void sonar() throws IOException {
        System.out.println("Task 1: sonar");
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec01.txt"))) {
            String line;
            Integer previousNumber = null;
            int increaseCount = 0;
                while ((line = br.readLine()) != null) {
                    int currentNumber = Integer.parseInt(line);
                if (previousNumber == null) {
                    System.out.println(currentNumber + " (N/A - no previous measurement)");
                } else if (currentNumber < previousNumber) {
                    System.out.println(currentNumber + " (decreased)");
                } else if (currentNumber > previousNumber) {
                    System.out.println(currentNumber + " (increased)");
                    increaseCount++;
                } else {
                    throw new IllegalArgumentException("EQUALS");
                }
                previousNumber = currentNumber;
            }
            System.out.println("Number of increases: " + increaseCount);
        }
    }

    private static void windowMeasurements() throws IOException {
        System.out.println("Task 2: measurement windows");
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec01.txt"))) {
            String line;
            MeasurementWindow w1 = new MeasurementWindow();
            MeasurementWindow w2 = new MeasurementWindow();
            int increaseCount = 0;
            Integer previousNumber = null;
            while ((line = br.readLine()) != null) {
                int currentNumber = Integer.parseInt(line);
                if (previousNumber != null) {
                    w1.add(previousNumber);
                    w2.add(currentNumber);
                }
                if (w1.isReady() && w2.isReady()) {
                    if (w1.sum() < w2.sum()) {
                        System.out.println(w2.sum() + " (increased)");
                        increaseCount++;
                    } else if (w1.sum() > w2.sum()) {
                        System.out.println(w2.sum() + " (decreased)");
                    } else {
                        System.out.println(w2.sum() + " (no change)");
                    }
                } else {
                    System.out.println(w2.sum() + " (N/A - no previous measurement)");
                }

                previousNumber = currentNumber;
            }
            System.out.println("Number of increases: " + increaseCount);
        }
    }

    private static class MeasurementWindow extends ArrayList<Integer> {
        private static final int WINDOW_SIZE = 3;
        @Override
        public boolean add(Integer entry) {
            boolean result = super.add(entry);
            if (size() > WINDOW_SIZE) {
                remove(0);
            }
            return result;
        }

        public boolean isReady() {
            return size() == WINDOW_SIZE;
        }

        public int sum() {
            return stream().reduce(0, Integer::sum);
        }
    }
}
