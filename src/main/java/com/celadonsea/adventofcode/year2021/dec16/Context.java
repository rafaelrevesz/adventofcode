package com.celadonsea.adventofcode.year2021.dec16;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private static Context instance;

    private int readingPosition = 0;
    private String bitOrder;


    public static Context getCurrentContext() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public void init(String input) {
        Map<Character, String> conversionMap = new HashMap<>();

        conversionMap.put('0', "0000");
        conversionMap.put('1', "0001");
        conversionMap.put('2', "0010");
        conversionMap.put('3', "0011");
        conversionMap.put('4', "0100");
        conversionMap.put('5', "0101");
        conversionMap.put('6', "0110");
        conversionMap.put('7', "0111");
        conversionMap.put('8', "1000");
        conversionMap.put('9', "1001");
        conversionMap.put('A', "1010");
        conversionMap.put('B', "1011");
        conversionMap.put('C', "1100");
        conversionMap.put('D', "1101");
        conversionMap.put('E', "1110");
        conversionMap.put('F', "1111");

        StringBuilder bits = new StringBuilder();
        for (char c : input.toCharArray()) {
            bits.append(conversionMap.get(c));
        }
        bitOrder = bits.toString();
        readingPosition = 0;
    }

    public void readClosingZeros() {
        if (!bitOrder.substring(readingPosition).contains("1")) {
            readingPosition = bitOrder.length();
        }
    }

    public int readNumber(int bitCount) {
        return Integer.parseInt(readBits(bitCount), 2);
    }

    public String readBits(int bitCount) {
        String result = bitOrder.substring(readingPosition, readingPosition + bitCount);
        readingPosition += bitCount;
        return result;
    }

    public int getReadingPosition() {
        return readingPosition;
    }

    public int nextType() {
        Context.getCurrentContext().readBits(3);
        int result = readNumber(3);
        readingPosition -= 6;
        return result;
    }


}
