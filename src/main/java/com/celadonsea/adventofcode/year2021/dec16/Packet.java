package com.celadonsea.adventofcode.year2021.dec16;

import java.math.BigInteger;

abstract class Packet {
    protected int version;
    protected int typeId;

    public Packet() {
        version = Context.getCurrentContext().readNumber(3);
        typeId = Context.getCurrentContext().readNumber(3);
    }

    public int getVersionSum() {
        return version;
    }

    public abstract BigInteger getValue();
}
