package com.celadonsea.adventofcode.year2021.dec16;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;

public class Dec16Test {

    private Dec16 dec16 = new Dec16();

    @Test
    public void puzzleTask1() throws IOException {
        Assertions.assertEquals(925, dec16.getVersionSum());
    }

    @Test
    public void puzzleTask2() throws IOException {
        Assertions.assertEquals(BigInteger.valueOf(342997120375L), dec16.getValue());
        //                                         342656732622
    }

    @Test
    public void task1Examples() {
        Assertions.assertEquals(16, dec16.getVersionSum("8A004A801A8002F478"));
        Assertions.assertEquals(12, dec16.getVersionSum("620080001611562C8802118E34"));
        Assertions.assertEquals(23, dec16.getVersionSum("C0015000016115A2E0802F182340"));
        Assertions.assertEquals(31, dec16.getVersionSum("A0016C880162017C3686B18A3D4780"));
    }
}
