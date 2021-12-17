package com.celadonsea.adventofcode.year2021.dec16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Dec16 {

    public int getVersionSum() throws IOException {
        return getVersionSum(readExample());
    }

    public BigInteger getValue() throws IOException {
        return getValue(readExample());
    }

    public int getVersionSum(String example) {
        Context.getCurrentContext().init(example);
        return getPacket().getVersionSum();
    }

    public BigInteger getValue(String example) {
        Context.getCurrentContext().init(example);
        return getPacket().getValue();
    }


    private Packet getPacket() {
        Packet packet;
        if (Context.getCurrentContext().nextType() == Literal.TYPE) {
            return new Literal();
        } else {
            return new Operator();
        }
    }

    private String readExample() throws IOException {
        String result;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("year2021/dec16.txt")))) {
            result = br.readLine();
        }
        return result;
    }
}
