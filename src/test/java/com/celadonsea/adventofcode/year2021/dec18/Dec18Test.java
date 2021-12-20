package com.celadonsea.adventofcode.year2021.dec18;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Dec18Test {

    private Dec18 dec18 = new Dec18();

    @Test
    public void task1Puzzle() throws IOException {
        Assertions.assertEquals(4140, dec18.processSum());
    }

    @Test
    public void testNumbers() {
        Pair pair = new Pair("[3,4]");
        Assertions.assertEquals(3, pair.getLeftValue());
        Assertions.assertEquals(4, pair.getRightValue());
    }

    @Test
    public void testLeftPair() {
        Pair pair = new Pair("[[1,2],4]");
        Assertions.assertEquals(1, pair.getLeftPair().getLeftValue());
        Assertions.assertEquals(2, pair.getLeftPair().getRightValue());
        Assertions.assertEquals(2, pair.getLeftPair().getLevel());
        Assertions.assertEquals(4, pair.getRightValue());
    }

    @Test
    public void testRightPair() {
        Pair pair = new Pair("[5,[6,7]]");
        Assertions.assertEquals(6, pair.getRightPair().getLeftValue());
        Assertions.assertEquals(7, pair.getRightPair().getRightValue());
        Assertions.assertEquals(2, pair.getRightPair().getLevel());
        Assertions.assertEquals(5, pair.getLeftValue());
    }

    @Test
    public void testPairs() {
        Pair pair = new Pair("[[8,9],[6,7]]");
        Assertions.assertEquals(8, pair.getLeftPair().getLeftValue());
        Assertions.assertEquals(9, pair.getLeftPair().getRightValue());
        Assertions.assertEquals(6, pair.getRightPair().getLeftValue());
        Assertions.assertEquals(7, pair.getRightPair().getRightValue());
        Assertions.assertEquals(2, pair.getRightPair().getLevel());
        Assertions.assertEquals(2, pair.getLeftPair().getLevel());
    }

    @Test
    public void testDeeperPairs() {
        Pair pair = new Pair("[5,[[1,[2,3]],7]]");
        Assertions.assertEquals(5, pair.getLeftValue());
        Assertions.assertEquals(7, pair.getRightPair().getRightValue());
        Assertions.assertEquals(1, pair.getRightPair().getLeftPair().getLeftValue());
        Assertions.assertEquals(2, pair.getRightPair().getLeftPair().getRightPair().getLeftValue());
        Assertions.assertEquals(3, pair.getRightPair().getLeftPair().getRightPair().getRightValue());
        Assertions.assertEquals(4, pair.getRightPair().getLeftPair().getRightPair().getLevel());
    }

    @Test
    public void testAdditionWithSplit() {
        Pair pair1 = new Pair("[1,2]");
        Pair pair2 = new Pair("[5,[[1,[2,3]],7]]");
        Pair addition = new Pair(pair1, pair2);
        dec18.reduce(addition);
        Assertions.assertEquals(0, addition.getRightPair().getRightPair().getLeftPair().getRightValue());
        Assertions.assertEquals(3, addition.getRightPair().getRightPair().getLeftPair().getLeftValue());
        Assertions.assertEquals(5, addition.getRightPair().getRightPair().getRightPair().getRightValue());
        Assertions.assertEquals(5, addition.getRightPair().getRightPair().getRightPair().getLeftValue());
        Assertions.assertEquals("[[1,2],[5,[[3,0],[5,5]]]]", addition.toString());
    }

    @Test
    public void testAddition() {
        Pair pair1 = new Pair("[1,2]");
        Pair pair2 = new Pair("[5,[[1,[2,3]],6]]");
        Pair addition = new Pair(pair1, pair2);
        dec18.reduce(addition);
        Assertions.assertEquals(0, addition.getRightPair().getRightPair().getLeftPair().getRightValue());
        Assertions.assertEquals(3, addition.getRightPair().getRightPair().getLeftPair().getLeftValue());
        Assertions.assertEquals(9, addition.getRightPair().getRightPair().getRightValue());
        Assertions.assertEquals("[[1,2],[5,[[3,0],9]]]", addition.toString());
    }

    @Test
    public void testAddition2() {
        Pair pair1 = new Pair("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]");
        Pair pair2 = new Pair("[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]");
        Pair addition = new Pair(pair1, pair2);
        dec18.reduce(addition);
        Assertions.assertEquals("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]", addition.toString());
    }

    @Test
    public void testToString() {
        Pair pair = new Pair("[5,[[1,[2,3]],7]]");
        Assertions.assertEquals("[5,[[1,[2,3]],7]]", pair.toString());
    }

    @Test
    public void testExample() {
        Pair pair1 = new Pair("[[[[4,3],4],4],[7,[[8,4],9]]]");
        Pair pair2 = new Pair("[1,1]");
        Pair addition = new Pair(pair1, pair2);
        dec18.reduce(addition);
        Assertions.assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", addition.toString());
    }

    @Test
    public void testExample2() {
        Pair pair1 = new Pair("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]");
        Pair pair2 = new Pair("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]");
        Pair addition = new Pair(pair1, pair2);
        dec18.reduce(addition);
        Assertions.assertEquals("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]", addition.toString());
    }

    @Test
    public void testMagnitudeCalculation() {
        Assertions.assertEquals(1384, new Pair("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").magnitude());
        Assertions.assertEquals(3488, new Pair("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").magnitude());
    }
}
