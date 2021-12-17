package com.celadonsea.adventofcode.year2021.dec14;

public class Rule {

    private String firstNewPair;
    private String secondNewPair;
    private char newCharacter;

    public Rule(String firstNewPair, String secondNewPair, Character newCharacter) {
        this.firstNewPair = firstNewPair;
        this.secondNewPair = secondNewPair;
        this.newCharacter = newCharacter;
    }

    public String getSecondNewPair() {
        return secondNewPair;
    }

    public Character getNewCharacter() {
        return newCharacter;
    }

    public String getFirstNewPair() {
        return firstNewPair;
    }
}
