package com.celadonsea.adventofcode.year2021.dec10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Dec10 {
    private static final HashMap<Character, Integer> errorLevel = new HashMap<>();
    private static final HashMap<Character, Integer> autoCompleteScoreLevels = new HashMap<>();
    private static final List<Long> autoCompleteScores = new ArrayList<>();
    private static int score = 0;

    public static void main(String[] args) throws IOException {
        errorLevel.put(')', 3);
        errorLevel.put(']', 57);
        errorLevel.put('}', 1197);
        errorLevel.put('>', 25137);
        autoCompleteScoreLevels.put(')', 1);
        autoCompleteScoreLevels.put(']', 2);
        autoCompleteScoreLevels.put('}', 3);
        autoCompleteScoreLevels.put('>', 4);
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec10/dec10.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                verifyLine(line);
            }
            System.out.println("Score: " + score);
            Collections.sort(autoCompleteScores);
            System.out.println("Autocomplete score: " + autoCompleteScores.get(autoCompleteScores.size() / 2));
        }
        //344193
        //3241238967
    }

    private static void verifyLine(String line) {
        final List<Character> stack = new ArrayList<>();
        HashMap<Character, Character> closers = new HashMap<>();
        closers.put('(', ')');
        closers.put('[', ']');
        closers.put('<', '>');
        closers.put('{', '}');
        for (int i = 0; i < line.length(); i++) {
            if (closers.containsKey(line.charAt(i))) {
                stack.add(closers.get(line.charAt(i)));
            } else if (line.charAt(i) == stack.get(stack.size() - 1)) {
                stack.remove(stack.size() - 1);
            } else {
                score += errorLevel.get(line.charAt(i));
                return;
            }
        }
        if (!stack.isEmpty()) {
            long autoCompleteScore = 0;
            for (int i = stack.size() - 1; i >= 0; i--) {
                autoCompleteScore = autoCompleteScore * 5 + autoCompleteScoreLevels.get(stack.get(i));
            }
            autoCompleteScores.add(autoCompleteScore);
        }
    }
}
