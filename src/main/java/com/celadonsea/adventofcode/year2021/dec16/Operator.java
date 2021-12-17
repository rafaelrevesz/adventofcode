package com.celadonsea.adventofcode.year2021.dec16;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class Operator extends Packet {
    private int type;
    private List<Packet> subPacket = new ArrayList<>();

    public Operator() {
        super();
        int lengthTypeId = Context.getCurrentContext().readNumber(1);
        if (lengthTypeId == 0) {
            int length = Context.getCurrentContext().readNumber(15);
            int startPosition = Context.getCurrentContext().getReadingPosition();
            while (length > Context.getCurrentContext().getReadingPosition() - startPosition) {
                addSubPacket();
                Context.getCurrentContext().readClosingZeros();
            }
        } else {
            int numberOfSubPackets = Context.getCurrentContext().readNumber(11);
            for (int i = 0; i < numberOfSubPackets; i++) {
                addSubPacket();
            }
            Context.getCurrentContext().readClosingZeros();
        }
    }

    void addSubPacket() {
        if (Context.getCurrentContext().nextType() == 4) {
            subPacket.add(new Literal());
        } else {
            subPacket.add(new Operator());
        }
    }

    public int getVersionSum() {
        int v = version;
        return v + subPacket.stream().map(Packet::getVersionSum).reduce(0, Integer::sum);
    }

    public BigInteger getValue() {
        BigInteger value;
        if (typeId == 0) {
            value = subPacket.stream().map(Packet::getValue).reduce(BigInteger.valueOf(0), BigInteger::add);
        } else if (typeId == 1) {
            value = subPacket.stream().map(Packet::getValue).reduce(BigInteger.valueOf(1), BigInteger::multiply);
        } else if (typeId == 2) {
            value = subPacket.stream().map(Packet::getValue).min(BigInteger::compareTo).get();
        } else if (typeId == 3) {
            value = subPacket.stream().map(Packet::getValue).max(BigInteger::compareTo).get();
        } else if (typeId == 5) {
            if (subPacket.get(0).getValue().compareTo(subPacket.get(1).getValue()) == 1) {
                value = BigInteger.valueOf(1);
            } else {
                value = BigInteger.valueOf(0);
            }
        } else if (typeId == 6) {
            if (subPacket.get(0).getValue().compareTo(subPacket.get(1).getValue()) == -1) {
                value = BigInteger.valueOf(1);
            } else {
                value = BigInteger.valueOf(0);
            }
        } else if (typeId == 7) {
            if (subPacket.get(0).getValue().compareTo(subPacket.get(1).getValue()) == 0) {
                value = BigInteger.valueOf(1);
            } else {
                value = BigInteger.valueOf(0);
            }
        } else {
            value = BigInteger.valueOf(0);
        }
        return value;
    }
}
