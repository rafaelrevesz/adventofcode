package com.celadonsea.adventofcode.year2021.dec14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Dec14 {

    public static void main(String[] args) throws IOException {
        Map<String, String> rules = new HashMap<>();
        String template = null;
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec14/dec14.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (template == null) {
                    template = line;
                }
                if (line.contains(" -> ")) {
                    String[] rule = line.split(" -> ");
                    rules.put(rule[0], rule[1] + rule[0].charAt(1));
                }
            }
        }
        for (int step = 1; step <= 10; step++) {
            StringBuilder polimer = new StringBuilder();
            for (int position = 0; position < template.length() - 1; position++) {
                if (position == 0) {
                    polimer.append(template.substring(0, 1));
                }
                String pair = template.substring(position, position + 2);
                if (rules.containsKey(pair)) {
                    polimer.append(rules.get(pair));
                } else {
                    polimer.append(pair);
                }
            }
            template = polimer.toString();
            System.out.println("Length after step " + step + ": " + template.length());

            Map<Character, Integer> an = analyze(template);

            Integer min = an.values().stream().min(Integer::compareTo).get();
            Integer max = an.values().stream().max(Integer::compareTo).get();
            System.out.println("Min: " + min);
            System.out.println("Max: " + max);
            int result = max - min;
            System.out.println("Produce: " + result);
        }
    }

    private static Map<Character, Integer> analyze(String template) {
        Map<Character, Integer> result = new HashMap<>();
        for (Character c : template.toCharArray()) {
            if (result.containsKey(c)) {
                result.put(c, result.get(c) + 1);
            } else {
                result.put(c, 1);
            }
        }
        return result;
    }

}
