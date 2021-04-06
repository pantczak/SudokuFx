package pl.sudoku.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of backtracking algorithm for solving sudoku.
 *
 * @author Bartosz Kepka 224326
 */
public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {

    /**
     * Generates shuffled list of integers that could be inserted
     * into board cell depends on its size.
     * eg. for 9x9 board returns shuffled list of {1, 2, ..., 8, 9}
     *
     * @param boardSize size of the board to generate candidates for
     * @return list of candidates
     */
    private List<Integer> generateShuffledCandidates(final int boardSize) {
        List<Integer> candidates = Arrays.asList(new Integer[boardSize]);
        for (int candidate = 1; candidate <= boardSize; candidate++) {
            candidates.set(candidate - 1, candidate);
        }
        Collections.shuffle(candidates);
        return candidates;
    }

    /**
     * Fills board following sudoku rules.
     * Called recursively, implements backtracking algorithm.
     * Steps:
     * 1. Checks for first unassigned cell.
     * Going from left to right, top to bottom.
     * 2. If found, tries to insert each integer
     * from 1 to 9 in random order.
     * Otherwise sudoku is solved and returns true.
     * 3. Inserts first candidate,
     * if sudoku is solvable,
     * calls itself recursively.
     * Otherwise tries next number.
     * If none of them can be inserted, returns false
     * and goes back to previous call.
     *
     * @return true if succeeded, otherwise false
     */
    @Override
    public boolean solve(final SudokuBoard board) {
        int row = -1;
        int column = -1;
        int boardSize = board.getBoardSize();
        List<Integer> candidates = generateShuffledCandidates(boardSize);

        // Check for unassigned cells on sudoku board
        boolean isSolved = true;
        for (int i = 0; i < boardSize && isSolved; i++) {
            for (int j = 0; j < boardSize && isSolved; j++) {
                if (board.get(i, j) == 0) {
                    row = i;
                    column = j;
                    isSolved = false;
                }
            }
        }

        // If all of the cells are assigned then sudoku is solved
        if (isSolved) {
            return true;
        }

        // Try to find a correct number for unassigned cell
        // If successful then assign and call solveSudoku() recursively
        // If not then go back to previous call on the stack
        for (int i = 0; i < boardSize; i++) {
            board.set(row, column, candidates.get(i));
            if (board.getRow(row).verify()
                    && board.getColumn(column).verify()
                    && board.getBox(row, column).verify()) {
                if (solve(board)) {
                    return true;
                } else {
                    board.set(row, column, 0);
                }
            } else {
                board.set(row, column, 0);
            }
        }
        return false;
    }
}
