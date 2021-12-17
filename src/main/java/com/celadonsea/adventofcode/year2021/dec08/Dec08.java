package com.celadonsea.adventofcode.year2021.dec08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Dec08 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec08/dec08.txt"))) {
            String line;
            int uniqueDigitCounter = 0;
            int globalResult = 0;
            while ((line = br.readLine()) != null) {
                String[] dataFields = line.split(" \\| ");
                uniqueDigitCounter += countUniqueDigitsInALine(dataFields[1].split(" "));
                globalResult += decodeDigits(dataFields[0].split(" "), dataFields[1].split(" "));
            }
            System.out.println("Number of unique digits using numbers: " + uniqueDigitCounter);
            System.out.println("Result: " + globalResult);
        }
    }

    private static int countUniqueDigitsInALine(String[] numbers) {
        int result = 0;
        for (String number : numbers) {
            if (number.length() == 2 || number.length() == 3 || number.length() == 7 || number.length() == 4) {
                result++;
            }
        }
        return result;
    }

    private static int decodeDigits(String[] digits, String[] numbers) {
        Map<String, Integer> digitMap = new HashMap<>();
        HashMap<Integer, String> decodedDigits = new HashMap<>();
        while (digitMap.size() < 10) {
            for (String number : digits) {
                String sortedNumber = sort(number);
                if (!digitMap.containsKey(sortedNumber)) {
                    if (sortedNumber.length() == 2) {
                        digitMap.put(sortedNumber, 1);
                        decodedDigits.put(1, sortedNumber);
                    } else if (sortedNumber.length() == 3) {
                        digitMap.put(sortedNumber, 7);
                    } else if (sortedNumber.length() == 7) {
                        digitMap.put(sortedNumber, 8);
                    } else if (sortedNumber.length() == 4) {
                        digitMap.put(sortedNumber, 4);
                        decodedDigits.put(4, sortedNumber);
                    } else if (sortedNumber.length() == 5) {
                        if (decodedDigits.containsKey(1) && contains(sortedNumber, decodedDigits.get(1))) {
                            digitMap.put(sortedNumber, 3);
                            decodedDigits.put(3, sortedNumber);
                        } else if (decodedDigits.containsKey(9) && decodedDigits.containsKey(3) && contains(decodedDigits.get(9), sortedNumber)) {
                            digitMap.put(sortedNumber, 5);
                            decodedDigits.put(5, sortedNumber);
                        } else if (decodedDigits.containsKey(5)) {
                            digitMap.put(sortedNumber, 2);
                        }
                    } else if (sortedNumber.length() == 6) {
                        if (decodedDigits.containsKey(4) && contains(sortedNumber, decodedDigits.get(4))) {
                            digitMap.put(sortedNumber, 9);
                            decodedDigits.put(9, sortedNumber);
                        } else if (decodedDigits.containsKey(1) && decodedDigits.containsKey(9) && contains(sortedNumber, decodedDigits.get(1))) {
                            digitMap.put(sortedNumber, 0);
                            decodedDigits.put(0, sortedNumber);
                        } else if (decodedDigits.containsKey(0)) {
                            digitMap.put(sortedNumber, 6);
                        }
                    }
                }
            }
        }
        StringBuilder resultNumber = new StringBuilder();
        for (String number: numbers) {
            String sortedNumber = sort(number);
            resultNumber.append(digitMap.get(sortedNumber));
        }
        return Integer.parseInt(resultNumber.toString());
    }

    private static String sort(String inputString) {
        char[] tempArray = inputString.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    private static boolean contains(String textToVerify, String template) {
        boolean result = true;
        for (char c : template.toCharArray()) {
            result &= textToVerify.contains("" + c);
        }
        return result;
    }
}
