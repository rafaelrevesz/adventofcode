package com.celadonsea.adventofcode.year2021.dec04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dec04 {

    private static final Map<Integer, BoardReferences> boardNumbers = new HashMap<>();
    private static final Map<Integer, Board> boards = new HashMap<>();

    public static void main(String[] args) throws IOException {
        String numbers = null;
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/celadonsea/adventofcode/year2021/dec04/dec04.txt"))) {
            String line;
            int boardId = 0;
            int rowId = 0;
            while ((line = br.readLine()) != null) {
                if (numbers == null) {
                    numbers = line;
                } else if (line.isEmpty()) {
                    boardId++;
                    rowId = 0;
                } else {
                    addNumbersToBoard(line, boardId, rowId);
                    rowId++;
                }
            }
        }
        playBingo(numbers);
    }

    private static void playBingo(String numbers) {
        String[] nums = numbers.split(",");
        try {
            for (String num : nums) {
                int number = Integer.parseInt(num);
                if (boardNumbers.containsKey(number)) {
                    boardNumbers.get(number).drawNumber(number);
                }
            }
        } catch (Bingo b) {
            System.out.println("BINGO!!! Board win: " + b.id + ", result: " + b.result);
        }
    }

    private static void addNumbersToBoard(String boardRow, int boardId, int rowId) {

        if (!boards.containsKey(boardId)) {
            boards.put(boardId, new Board(boardId));
        }

        String[] nums = boardRow.trim().replaceAll("  ", " ").split(" ");
        int colId = 0;
        for (String num : nums) {
            int number = Integer.parseInt(num);
            if (!boardNumbers.containsKey(number)) {
                boardNumbers.put(number, new BoardReferences());
            }
            BoardReference boardReference = new BoardReference(boards.get(boardId), rowId, colId);
            boardNumbers.get(number).refs.add(boardReference);
            colId++;
            boards.get(boardId).addNumber(number);
        }
    }

    private static class BoardReferences {
        List<BoardReference> refs = new ArrayList<>();
        void drawNumber(int number) {
            refs.forEach(r -> r.drawNum(number));
        }
    }

    private static class BoardReference {
        Board board;
        int rowId;
        int colId;
        BoardReference(Board board, int rowId, int colId) {
            this.board = board;
            this.rowId = rowId;
            this.colId = colId;
        }
        void drawNum(int number) {
            board.newNumber(rowId, colId, number);
        }
    }

    private static class Board {
        int id;
        int sum = 0;
        int[] countOfNumbersDrawnInRow = {0, 0, 0, 0, 0};
        int[] countOfNumbersDrawnInCol = {0, 0, 0, 0, 0};

        Board(int id) {
            this.id = id;
        }

        void addNumber(int number) {
            sum += number;
        }

        void newNumber(int row, int col, int number) {
            sum -= number;
            countOfNumbersDrawnInCol[col]++;
            countOfNumbersDrawnInRow[row]++;
            if (countOfNumbersDrawnInCol[col] == 5 || countOfNumbersDrawnInRow[row] == 5) {
                throw new Bingo(id, sum * number);
            }
        }
    }

    private static class Bingo extends RuntimeException {
        int id;
        int result;
        Bingo(int id, int result) {
            this.id = id;
            this.result = result;
        }
    }
}