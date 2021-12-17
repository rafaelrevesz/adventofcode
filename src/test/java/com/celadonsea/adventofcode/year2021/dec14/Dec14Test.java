package com.celadonsea.adventofcode.year2021.dec14;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Dec14Test {

    @Test
    public void task1Puzzle() throws IOException {
        Dec14 dec14 = new Dec14();
        Assertions.assertEquals(3230L, dec14.produce(10));
    }

    @Test
    public void task2Puzzle() throws IOException {
        Dec14 dec14 = new Dec14();
        Assertions.assertEquals(3542388214529L, dec14.produce(40));
    }

    @Test
    public void task1Example() throws IOException {
        ByteArrayInputStream stream = getExampleAsStream();
        Dec14 dec14 = new Dec14();
        Assertions.assertEquals(1588, dec14.produce(stream, 10));
    }

    @Test
    public void task2Example() throws IOException {
        ByteArrayInputStream stream = getExampleAsStream();
        Dec14 dec14 = new Dec14();
        Assertions.assertEquals(2188189693529L, dec14.produce(stream, 40));
    }

    private ByteArrayInputStream getExampleAsStream() {
        StringBuilder example = new StringBuilder();
        example
                .append("NNCB").append("\n")
                .append(" \n")
                .append("CH -> B").append("\n")
                .append("HH -> N").append("\n")
                .append("CB -> H").append("\n")
                .append("NH -> C").append("\n")
                .append("HB -> C").append("\n")
                .append("HC -> B").append("\n")
                .append("HN -> C").append("\n")
                .append("NN -> C").append("\n")
                .append("BH -> H").append("\n")
                .append("NC -> B").append("\n")
                .append("NB -> B").append("\n")
                .append("BN -> B").append("\n")
                .append("BB -> N").append("\n")
                .append("BC -> B").append("\n")
                .append("CC -> N").append("\n")
                .append("CN -> C").append("\n");
        ByteArrayInputStream stream = new ByteArrayInputStream(example.toString().getBytes());
        return stream;
    }
}
