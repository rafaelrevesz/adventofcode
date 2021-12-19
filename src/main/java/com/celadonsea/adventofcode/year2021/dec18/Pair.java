package com.celadonsea.adventofcode.year2021.dec18;

import static com.celadonsea.adventofcode.year2021.dec18.Direction.LEFT;
import static com.celadonsea.adventofcode.year2021.dec18.Direction.RIGHT;

public class Pair {

    private int level;
    private Pair leftPair;
    private Pair rightPair;
    private Integer leftValue;
    private Integer rightValue;
    private Direction direction;

    private Pair parent;

    public Pair(Pair left, Pair right) {
        leftPair = left;
        rightPair = right;
        left.setParent(this);
        right.setParent(this);
        right.direction = RIGHT;
        left.direction = LEFT;
        increaseLevel();
    }

    public Pair(Integer left, Integer right, Pair parent, Direction direction) {
        leftValue = left;
        rightValue = right;
        this.parent = parent;
        level = parent.level + 1;
        if (level > 4) {
            System.out.println("UNHANDLED EXPLOSION: " + this.toString());
        }
        this.direction = direction;
    }

    public Pair(String input) {
        this(input, 1, null, null);
    }

    public Pair(String input, int level, Pair parent, Direction direction) {
        this.level = level;
        this.parent = parent;
        this.direction = direction;
        String content = input.substring(1, input.length() - 1);
        if (content.charAt(0) == '[') {
            String leftPairInput = readPair(content);
            leftPair = new Pair(leftPairInput, level + 1, this, LEFT);
            content = content.substring(leftPairInput.length() + 1);
        } else {
            leftValue = Integer.parseInt("" + content.charAt(0));
            content = content.substring(2);
        }
        if (content.charAt(0) == '[') {
            String leftPairInput = readPair(content);
            rightPair = new Pair(leftPairInput, level + 1, this, RIGHT);
        } else {
            rightValue = Integer.parseInt("" + content.charAt(0));
        }
    }

    private String readPair(String input) {
        int openBrackets = 0;
        StringBuilder result = new StringBuilder();
        int position = 0;
        do {
            result.append(input.charAt(position));
            if (input.charAt(position) == '[') {
                openBrackets++;
            } else if (input.charAt(position) == ']') {
                openBrackets--;
            }
            position++;
        } while (openBrackets > 0);
        return result.toString();
    }

    public int getLevel() {
        return level;
    }

    public Pair getLeftPair() {
        return leftPair;
    }

    public Pair getRightPair() {
        return rightPair;
    }

    public Integer getLeftValue() {
        return leftValue;
    }

    public Integer getRightValue() {
        return rightValue;
    }

    public void increaseLevel() {
        level++;
        if (level == 5) {
            throw new ExplosionException(this);
        }
        if (leftPair != null) {
            try {
                leftPair.increaseLevel();
            } catch (ExplosionException e) {
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
            }
        }
        if (rightPair != null) {
            try {
                rightPair.increaseLevel();   // [3,2] explodes
            } catch (ExplosionException e) { // [7,[6,[5,[4,[3,2]]]]] becomes [7,[6,[5,[7,0]]]]
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
            }
        }
    }

    private void addValueToLeft(int value) {
        if (leftValue + value < 10) {
            leftValue += value;
        } else {
            int sum = leftValue + value;
            leftValue = null;
            leftPair = new Pair(sum / 2, sum - sum / 2, this, LEFT);
            System.out.println("SPLIT: " + sum + "->" + leftPair.toString());
        }
    }

    private void addValueToRight(int value) {
        if (rightValue + value < 10) {
            rightValue += value;
        } else {
            int sum = rightValue + value;
            rightValue = null;
            rightPair = new Pair(sum / 2, sum - sum / 2, this, RIGHT);
            System.out.println("SPLIT: " + sum + "->" + rightPair.toString());
        }
    }

    public void setParent(Pair parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        String a = leftValue == null ? leftPair.toString() : ("" + leftValue);
        String b = rightValue == null ? rightPair.toString() : ("" + rightValue);
        return "[" + a + "," + b + "]";
    }

    public long magnitude() {
        long left;
        long right;
        if (leftValue != null) {
            left = leftValue * 3L;
        } else {
            left = leftPair.magnitude() * 3;
        }
        if (rightValue != null) {
            right = rightValue * 2L;
        } else {
            right = rightPair.magnitude() * 2;
        }
        return left + right;
    }
}
