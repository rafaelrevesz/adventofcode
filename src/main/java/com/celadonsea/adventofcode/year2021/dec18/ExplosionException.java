package com.celadonsea.adventofcode.year2021.dec18;

public class ExplosionException extends RuntimeException {

    private Pair explodedPair;
    private boolean leftProcessed;
    private boolean rightProcessed;

    public ExplosionException(Pair explodedPair) {
        this.explodedPair = explodedPair;
    }

    public Pair getExplodedPair() {
        return explodedPair;
    }

    public void setExplodedPair(Pair explodedPair) {
        this.explodedPair = explodedPair;
    }

    public boolean isLeftProcessed() {
        return leftProcessed;
    }

    public void setLeftProcessed(boolean leftProcessed) {
        this.leftProcessed = leftProcessed;
    }

    public boolean isRightProcessed() {
        return rightProcessed;
    }

    public void setRightProcessed(boolean rightProcessed) {
        this.rightProcessed = rightProcessed;
    }
}
