package com.celadonsea.adventofcode.year2021.dec14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dec14 {
    private static Map<String, Long> pairs = new HashMap<>();
    private static final Map<Character, Long> charCounter = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Map<String, String[]> rules = new HashMap<>();
        String template = null;
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec14/sample2.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (template == null) {
                    template = line;
                }
                if (line.contains(" -> ")) {
                    String[] rule = line.split(" -> ");
                    String[] adjacent = new String[3]; // NC -> X
                    adjacent[0] = rule[0].charAt(0) + rule[1]; // NX
                    adjacent[1] = rule[1] + rule[0].charAt(1); // XC
                    adjacent[2] = rule[1]; // X
                    rules.put(rule[0], adjacent);
                }
            }
        }
        for (int i = 0; i < template.length(); i++) { // NCNBCHB
            if (i < template.length() - 1) {
                addPair(template.substring(i, i + 2), 1);
            }
            addChar(template.charAt(i), 1);
        }

        for (int step = 1; step <= 40; step++) {
            Map<String, Long> basePairs = new HashMap<>(pairs);
            pairs.clear();
            for (Map.Entry<String, Long> onePair : basePairs.entrySet()) {
                if (rules.containsKey(onePair.getKey())) {
                    String[] rule = rules.get(onePair.getKey());
                    addPair(rule[0], onePair.getValue());
                    addPair(rule[1], onePair.getValue());
                    addChar(rule[2].charAt(0), onePair.getValue());
                } else {
                    addPair(onePair.getKey(), onePair.getValue());
                }
            }
            long length = charCounter.values().stream().reduce(0L, Long::sum);
            long min = charCounter.values().stream().min(Long::compareTo).get();
            long max = charCounter.values().stream().max(Long::compareTo).get();
            System.out.println("Length after step " + step + ": " + length);
            System.out.println("Produce: " + (max - min));
        }// NCNBCHB
    }

    private static void addPair(String pair, long number) {
        if (pairs.containsKey(pair)) {
            pairs.put(pair, pairs.get(pair) + number);
        } else {
            pairs.put(pair, number);
        }
    }

    private static void addChar(Character c, long number) {
        if (charCounter.containsKey(c)) {
            charCounter.put(c, charCounter.get(c) + number);
        } else {
            charCounter.put(c, number);
        }
    }
}
