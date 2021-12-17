package com.celadonsea.adventofcode.year2021.dec14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Dec14 {
    private Map<String, Long> pairs = new HashMap<>();
    private final Map<Character, Long> charCounter = new HashMap<>();

    public long produce(int steps) throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("year2021/dec14.txt");
        return produce(resourceAsStream, steps);
    }

    public long produce(InputStream stream, int steps) throws IOException {
        Map<String, Rule> rules = new HashMap<>();
        String template = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (template == null) {
                    template = line;
                }
                if (line.contains(" -> ")) {
                    String[] ruleParts = line.split(" -> ");
                    String rulePair = ruleParts[0];
                    String newChar = ruleParts[1];
                    Rule rule = new Rule(rulePair.charAt(0) + newChar, newChar + rulePair.charAt(1), newChar.charAt(0));
                    rules.put(rulePair, rule);
                }
            }
        }
        for (int i = 0; i < template.length(); i++) { // NCNBCHB
            if (i < template.length() - 1) {
                addPair(template.substring(i, i + 2), 1);
            }
            addChar(template.charAt(i), 1);
        }

        for (int step = 1; step <= steps; step++) {
            Map<String, Long> basePairs = new HashMap<>(pairs);
            pairs.clear();
            for (Map.Entry<String, Long> onePair : basePairs.entrySet()) {
                if (rules.containsKey(onePair.getKey())) {
                    Rule rule = rules.get(onePair.getKey());
                    addPair(rule.getFirstNewPair(), onePair.getValue());
                    addPair(rule.getSecondNewPair(), onePair.getValue());
                    addChar(rule.getNewCharacter(), onePair.getValue());
                } else {
                    addPair(onePair.getKey(), onePair.getValue());
                }
            }
        }// NCNBCHB
        long min = charCounter.values().stream().min(Long::compareTo).get();
        long max = charCounter.values().stream().max(Long::compareTo).get();
        return max -min;
    }

    private void addPair(String pair, long number) {
        if (pairs.containsKey(pair)) {
            pairs.put(pair, pairs.get(pair) + number);
        } else {
            pairs.put(pair, number);
        }
    }

    private void addChar(Character c, long number) {
        if (charCounter.containsKey(c)) {
            charCounter.put(c, charCounter.get(c) + number);
        } else {
            charCounter.put(c, number);
        }
    }
}
