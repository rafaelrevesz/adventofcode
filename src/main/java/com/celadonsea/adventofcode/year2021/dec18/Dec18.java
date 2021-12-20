package com.celadonsea.adventofcode.year2021.dec18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.celadonsea.adventofcode.year2021.dec18.Direction.LEFT;
import static com.celadonsea.adventofcode.year2021.dec18.Direction.RIGHT;

public class Dec18 {

    private static final boolean LOG_ENABLED = true;

    public long processSum() throws IOException {
        long result = 0;
        InputStream stream = getClass().getClassLoader().getResourceAsStream("year2021/dec18.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            Pair sum = null;
            while ((line = br.readLine()) != null) {
                Pair pair = new Pair(line);
                if (sum == null) {
                    sum = pair;
                } else {
                    sum = new Pair(sum, pair);
                    reduce(sum);
                }
                System.out.println("Sum: " + sum);
            }
            result = sum.magnitude();
        }
        return result;
    }

    public Pair reduce(Pair pair) {

        boolean explosionFound = true;
        boolean splitFound = true;
        if (LOG_ENABLED) System.out.println("Reduce: " + pair);

        while (explosionFound || splitFound) {
            explosionFound = false;
            splitFound = false;

            Pair pairToSplit = searchForSplit(pair);
            if (pairToSplit != null) {
                splitFound = true;
                if (LOG_ENABLED) System.out.println("SPLIT: " + pairToSplit);
                handleSplit(pairToSplit);
            } else {
                Pair pairToExplode = searchForExplosion(pair);
                if (pairToExplode != null) {
                    explosionFound = true;
                    if (LOG_ENABLED) System.out.println("EXPLODE: " + pairToExplode);
                    handleExplosion(pairToExplode);
                }
            }

            if (explosionFound && LOG_ENABLED) {
                System.out.println("After explosion: " + pair);
            }
            if (splitFound &&LOG_ENABLED) {
                System.out.println("After split: " + pair);
            }
        }

        return pair;
    }

    private void handleSplit(Pair pairToSplit) {
        if (pairToSplit.getLeftValue() != null && pairToSplit.getLeftValue() > 9) {
            int number = pairToSplit.getLeftValue();
            pairToSplit.setLeftValue(null);
            pairToSplit.setLeftPair(new Pair(number / 2, number - number / 2, pairToSplit, LEFT));
        }
        if (pairToSplit.getRightValue() != null && pairToSplit.getRightValue() > 9) {
            int number = pairToSplit.getRightValue();
            pairToSplit.setRightValue(null);
            pairToSplit.setRightPair(new Pair(number / 2, number - number / 2, pairToSplit, RIGHT));
        }
    }

    private Pair searchForSplit(Pair pair) {
        if ((pair.getLeftValue() != null && pair.getLeftValue() > 9) ||
                (pair.getRightValue() != null && pair.getRightValue() > 9)) {
            return pair;
        }
        Pair result = null;
        if (pair.getLeftPair() != null) {
            result = searchForSplit(pair.getLeftPair());
        }
        if (result == null && pair.getRightPair() != null) {
            result = searchForSplit(pair.getRightPair());
        }
        return result;
    }

    private void handleExplosion(Pair pair) {
        while (pair.getLeftValue() == null || pair.getRightValue() == null) {
            if (pair.getLeftPair() != null) {
                pair = pair.getLeftPair();
            } else if (pair.getRightPair() != null) {
                pair = pair.getRightPair();
            }
        }
        int left = pair.getLeftValue();
        int right = pair.getRightValue();

        Pair parent = pair.getParent();
        pair.setParent(null);
        if (pair.getDirection() == RIGHT) {
            parent.setRightPair(null);
            parent.setRightValue(0);

            parent.setRightPair(null); // [3,2] -> 0
            parent.setRightValue(0);
            if (parent.getLeftValue() != null) { // [4,0] -> [7,0]
                parent.addValueToLeft(left);
            } else {
                Pair pairToCheck = parent.getLeftPair();
                while (pairToCheck.getRightValue() == null) { // look for the left neighbour
                    pairToCheck = pairToCheck.getRightPair();
                }
                pairToCheck.addValueToRight(left); // add left value to the left neighbour
            }
            Pair parentPair = parent;  // [[6,[5,[4,[3,2]]]],1] becomes [[6,[5,[7,0]]],3]
            while (parentPair != null && parentPair.getDirection() == RIGHT) { // look for the right neighbour, go to that parent where
                parentPair = parentPair.getParent();
            }
            if (parentPair != null && parentPair.getParent() != null && parentPair.getDirection() == LEFT) { // there is something on the right
                if (parentPair.getParent().getRightValue() != null) { // it's a number
                    parentPair.getParent().addValueToRight(right);
                } else { // it's a pair
                    Pair pairToCheck = parentPair.getParent().getRightPair(); // go to the right side of the parent
                    while (pairToCheck.getLeftValue() == null) { // look for the first left side number
                        pairToCheck = pairToCheck.getLeftPair();
                    }
                    pairToCheck.addValueToLeft(right); // add right value to the right neighbour
                }
            }

            // move numbers
        } else {
            parent.setLeftPair(null);
            parent.setLeftValue(0);
            if (parent.getRightValue() != null) {
                parent.addValueToRight(right);
            } else {
                Pair pairToCheck = parent.getRightPair();
                while (pairToCheck.getLeftValue() == null) {
                    pairToCheck = pairToCheck.getLeftPair();
                }
                pairToCheck.addValueToLeft(right);
            }

            Pair parentPair = parent;  // [[6,[5,[4,[3,2]]]],1] becomes [[6,[5,[7,0]]],3]
            while (parentPair != null && parentPair.getDirection() == LEFT) { // look for the left neighbour, go to that parent where
                parentPair = parentPair.getParent();
            }
            if (parentPair != null && parentPair.getParent() != null && parentPair.getDirection() == RIGHT) { // there is something on the left
                if (parentPair.getParent().getLeftValue() != null) { // it's a number
                    parentPair.getParent().addValueToLeft(left);
                } else { // it's a pair
                    Pair pairToCheck = parentPair.getParent().getLeftPair(); // go to the left side of the parent
                    while (pairToCheck.getRightValue() == null) { // look for the first right side number
                        pairToCheck = pairToCheck.getRightPair();
                    }
                    pairToCheck.addValueToRight(left); // add left value to the left neighbour
                }
            }
        }
    }

    private Pair searchForExplosion(Pair pairToVerify) {
        if (pairToVerify.getLevel() > 4) {
            return pairToVerify;
        } else {
            Pair result = null;
            if (pairToVerify.getLeftPair() != null) {
                result = searchForExplosion(pairToVerify.getLeftPair());
            }
            if (result == null && pairToVerify.getRightPair() != null) {
                result = searchForExplosion(pairToVerify.getRightPair());
            }
            return result;
        }
    }

    /*
                    System.out.println("EXPLOSION: " + e.getExplodedPair().toString());
                leftPair = null; // [3,2] -> 0
                leftValue = 0;
                if (rightValue != null) { // [4,0] -> [7,0]
                    addValueToRight(e.getExplodedPair().rightValue);
                } else {
                    Pair pair = rightPair;
                    while (pair.leftValue == null) { // look for the right neighbour
                        pair = pair.leftPair;
                    }
                    pair.addValueToLeft(e.getExplodedPair().rightValue); // add right value to the right neighbour
                }
                e.setRightProcessed(true);
                Pair parentPair = this;  // [[6,[5,[4,[3,2]]]],1] becomes [[6,[5,[7,0]]],3]
                while (parentPair != null && parentPair.direction == LEFT) { // look for the left neighbour, go to that parent where
                    parentPair = parentPair.parent;
                }
                if (parentPair != null && parentPair.parent != null && parentPair.direction == RIGHT) { // there is something on the left
                    if (parentPair.parent.leftValue != null) { // it's a number
                        parentPair.parent.addValueToLeft(e.getExplodedPair().leftValue);
                    } else { // it's a pair
                        Pair pair = parentPair.parent.leftPair; // go to the left side of the parent
                        while (pair.rightValue == null) { // look for the first right side number
                            pair = pair.rightPair;
                        }
                        pair.addValueToRight(e.getExplodedPair().leftValue); // add left value to the left neighbour
                    }
                }

     */

    /*
                    System.out.println("EXPLOSION: " + e.getExplodedPair().toString());
                rightPair = null; // [3,2] -> 0
                rightValue = 0;
                if (leftValue != null) { // [4,0] -> [7,0]
                    addValueToLeft(e.getExplodedPair().leftValue);
                } else {
                    Pair pair = leftPair;
                    while (pair.rightValue == null) { // look for the left neighbour
                        pair = pair.rightPair;
                    }
                    pair.addValueToRight(e.getExplodedPair().leftValue); // add left value to the left neighbour
                }
                e.setLeftProcessed(true);
                Pair parentPair = this;  // [[6,[5,[4,[3,2]]]],1] becomes [[6,[5,[7,0]]],3]
                while (parentPair != null && parentPair.direction == RIGHT) { // look for the right neighbour, go to that parent where
                    parentPair = parentPair.parent;
                }
                if (parentPair != null && parentPair.parent != null && parentPair.direction == LEFT) { // there is something on the right
                    if (parentPair.parent.rightValue != null) { // it's a number
                        parentPair.parent.addValueToRight(e.getExplodedPair().rightValue);
                    } else { // it's a pair
                        Pair pair = parentPair.parent.rightPair; // go to the right side of the parent
                        while (pair.leftValue == null) { // look for the first left side number
                            pair = pair.leftPair;
                        }
                        pair.addValueToLeft(e.getExplodedPair().rightValue); // add right value to the right neighbour
                    }
                }

     */
}
