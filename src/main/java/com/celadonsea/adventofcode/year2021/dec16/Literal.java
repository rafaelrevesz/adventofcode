package com.celadonsea.adventofcode.year2021.dec16;

import java.math.BigInteger;

class Literal extends Packet {
    public static final int TYPE = 4;
    private BigInteger number;

    public Literal() {
        super();
        StringBuilder numberCache = new StringBuilder();
        int continueFlag = 1;
        while (continueFlag == 1) {
            continueFlag = Context.getCurrentContext().readNumber(1);
            numberCache.append(Context.getCurrentContext().readBits(4));
            if (continueFlag == 0) {
                number = new BigInteger(numberCache.toString(), 2);
            }
        }
    }

    public BigInteger getValue() {
        return number;
    }

}
