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
        if (leftPair != null) {
            leftPair.increaseLevel();
        }
        if (rightPair != null) {
            rightPair.increaseLevel();
        }
    }

    public void setParent(Pair parent) {
        this.parent = parent;
    }

    public Pair getParent() {
        return parent;
    }

    public void setRightPair(Pair rightPair) {
        this.rightPair = rightPair;
    }

    public void setLeftPair(Pair leftPair) {
        this.leftPair = leftPair;
    }

    public void setLeftValue(Integer leftValue) {
        this.leftValue = leftValue;
    }

    public void setRightValue(Integer rightValue) {
        this.rightValue = rightValue;
    }

    public Direction getDirection() {
        return direction;
    }

    public void addValueToLeft(int value) {
        this.leftValue += value;
    }

    public void addValueToRight(int value) {
        this.rightValue += value;
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
