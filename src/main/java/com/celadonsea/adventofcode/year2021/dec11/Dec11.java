package com.celadonsea.adventofcode.year2021.dec11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dec11 {
    private static int flashCounter = 0;
    private static int flashCounterOneStep = 0;
    private static final List<List<Octopus>> octopusMatrix = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec11/dec11.txt"))) {
            String line;

            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            for (int y = 0; y < 10; y++) {
                octopusMatrix.add(new ArrayList<>());
                for (int x = 0; x < 10; x++) {
                    Octopus octopus = new Octopus();
                    octopus.energy = Integer.parseInt("" + lines.get(y).charAt(x));
                    octopusMatrix.get(y).add(octopus);
                    addAdjacent(octopus, x - 1, y - 1);
                    addAdjacent(octopus, x, y - 1);
                    addAdjacent(octopus, x + 1, y - 1);
                    addAdjacent(octopus, x - 1, y);
                }
            }
            int step = 0;
            while (flashCounterOneStep < 100) {
                step++;
                flashCounterOneStep = 0;
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                        octopusMatrix.get(y).get(x).increaseEnergy();
                    }
                }
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                        octopusMatrix.get(y).get(x).cleanUp();
                    }
                }
                flashCounter += flashCounterOneStep;
                if (step == 100) {
                    System.out.println("Number of flashes after 100 step: " + flashCounter);
                }
            }
            System.out.println("Number of steps until synchronization: " + step);
        }
    }

    private static void addAdjacent(Octopus octopus, int x, int y) {
        if (x >= 0 && y >= 0 && x <= 9) {
            Octopus adjacent = octopusMatrix.get(y).get(x);
            octopus.adjacents.add(adjacent);
            adjacent.adjacents.add(octopus);
        }
    }

    private static class Octopus {
        int energy;
        List<Octopus> adjacents = new ArrayList<>();

        void increaseEnergy() {
            energy++;
            if (energy == 10) {
                flashCounterOneStep++;
                adjacents.forEach(Octopus::increaseEnergy);
            }
        }
        void cleanUp() {
            if (energy > 9) {
                energy = 0;
            }
        }
    }
}
