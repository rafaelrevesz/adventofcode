package com.celadonsea.adventofcode.year2021.dec03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dec03 {

    private static final int DATA_LENGTH = 12;

    public static void main(String[] args) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec03/dec03.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        String gammaBin = mostCommon(lines);
        String epsilonBin = invert(gammaBin);

        int gammaDecimal = Integer.parseInt(gammaBin, 2);
        int epsilonDecimal = Integer.parseInt(epsilonBin, 2);
        System.out.println("Gamma: " + gammaBin + "=" + gammaDecimal);
        System.out.println("Epsilon: " + epsilonBin + "=" + epsilonDecimal);
        System.out.println("Result: " + gammaDecimal * epsilonDecimal);
        //Result: 2583164

        String oxygenRatingBin = selector(lines, 0, true);
        String co2RatingBin = selector(lines, 0, false);
        int oxygenRating = Integer.parseInt(oxygenRatingBin, 2);
        int co2Rating = Integer.parseInt(co2RatingBin, 2);

        System.out.println("Oxygen: " + oxygenRatingBin + "=" + oxygenRating);
        System.out.println("Co2: " + co2RatingBin + "=" + co2Rating);
        System.out.println("Result: " + oxygenRating * co2Rating);
        // 2784375
    }

    private static String mostCommon(List<String> source) {
        int[] bitContainer = new int[DATA_LENGTH];
        for (String line : source) {
            String convertedLine = line.replaceAll("1", "2");
            for (int i=0; i < DATA_LENGTH; i++) {
                bitContainer[i] += Integer.parseInt(convertedLine.substring(i, i + 1)) - 1;
            }
        }
        String result = "";
        for (int i=0; i < DATA_LENGTH; i++) {
            if (bitContainer[i] != 0) {
                bitContainer[i] /= Math.abs(bitContainer[i]);
            } else {
                bitContainer[i] = 1;
            }
            if (bitContainer[i] < 0) {
                bitContainer[i] = 0;
            }
            result += bitContainer[i];
        }
        return result;
    }

    private static String invert(String line) {
        return line
                .replaceAll("0", "2")
                .replaceAll("1", "0")
                .replaceAll("2", "1");
    }

    private static String selector(List<String> source, int bitPosition, boolean isMostCommon) {
        if (bitPosition == 12) {
            throw new IllegalArgumentException("Not found");
        }
        final String criteria;
        if (isMostCommon) {
            criteria = mostCommon(source);
        } else {
            criteria = invert(mostCommon(source));
        }
        List<String> filteredList = source.stream().filter(s -> s.charAt(bitPosition) == criteria.charAt(bitPosition)).collect(Collectors.toList());
        if (filteredList.size() == 1) {
            return filteredList.get(0);
        }
        return selector(filteredList, bitPosition + 1, isMostCommon);
    }
}
