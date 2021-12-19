package com.celadonsea.adventofcode.year2021.dec18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dec18 {

    public void readInput() throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("year2021/dec18.txt");
        List<Pair> pairs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                pairs.add(new Pair(line));
            }
        }
    }
}
