package pl.sudoku.model;

/**
 * Interface for implementing different solving algorithms.
 */
public interface SudokuSolver {

    /**
     * Sudoku solving method to implement an algorithm.
     *
     * @param board to be solved
     * @return true if solved successfully, otherwise false
     */
    boolean solve(SudokuBoard board);
}
