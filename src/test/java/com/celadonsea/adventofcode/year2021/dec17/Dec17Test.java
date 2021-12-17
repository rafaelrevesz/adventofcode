package com.celadonsea.adventofcode.year2021.dec17;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Dec17Test {

    private Dec17 dec17 = new Dec17();

    @Test
    public void task1Example() {
        String input = "target area: x=20..30, y=-10..-5";
        Assertions.assertEquals(45, dec17.maxYFire(input));
    }

    @Test
    public void task1Puzzle() {
        String input = "target area: x=248..285, y=-85..-56";
        Assertions.assertEquals(3570, dec17.maxYFire(input));
    }

    @Test
    public void task2Example() {
        String input = "target area: x=20..30, y=-10..-5";
        Assertions.assertEquals(112, dec17.firePossibilities(input));
    }

    @Test
    public void task2Puzzle() {
        String input = "target area: x=248..285, y=-85..-56";
        Assertions.assertEquals(1919, dec17.firePossibilities(input));
    }

}
