package pl.sudoku.model;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a sudoku board.
 */
public final class SudokuBoard implements Serializable, Cloneable {

    /**
     * Represents side size of sudoku board.
     * For future development (different board sizes)
     */
    private final int boardSize;

    /**
     * Enum representing side size of sudoku board.
     * For future development (different board sizes)
     */
    private final BoardSizeEnum boardSizeEnum;

    /**
     * Represents side size of box in sudoku board.
     * For future development (different board sizes)
     */
    private final int boxSize;

    /**
     * Board is stored as a square array of ints.
     * Size: {@link #boardSize}x{@link #boardSize}.
     * 0 means that cell in unassigned
     */
    private final SudokuField[][] board;

    /**
     * Implementation of sudokuSolver to use for solving sudoku game.
     */
    private final SudokuSolver sudokuSolver;

    /**
     * Fills sudoku board using solver given in constructor.
     */
    public void solveGame() {
        sudokuSolver.solve(this);
    }

    /**
     * Creates an instance of sudoku board with given solver and size.
     *
     * @param solver        instance of SudokuSolver to use for solving
     * @param boardSizeEnum size of board to create
     */
    public SudokuBoard(final SudokuSolver solver, BoardSizeEnum boardSizeEnum) {
        this.boardSizeEnum = boardSizeEnum;
        this.boardSize = this.boardSizeEnum.getSize();
        boxSize = (int) Math.sqrt(boardSize);
        board = new SudokuField[boardSize][boardSize];

        for (int row = 0; row < this.boardSize; row++) {
            for (int column = 0; column < this.boardSize; column++) {
                this.board[row][column] = new SudokuField();
            }
        }
        this.sudokuSolver = solver;
    }

    /**
     * Accessor for sudoku board.
     *
     * @return copy of board
     */
    public SudokuField[][] getCopyOfBoard() {
        return Arrays.copyOf(board, board.length);
    }

    /**
     * Accessor for sudoku board.
     *
     * @return side size of the board
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Accessor for sudoku board.
     *
     * @return side size of a box
     */
    public int getBoxSize() {
        return boxSize;
    }

    /**
     * Returns value in sudoku board cell at certain position.
     * 0 means that this cell is unassigned.
     *
     * @param row    number of row starting from 0
     * @param column number of column starting from 0
     * @return value in cell at given at position [row][column]
     */
    public int get(final int row, final int column) {
        return board[row][column].getFieldValue();
    }

    /**
     * Sets value in sudoku board cell at certain position.
     * 0 means that this cell is unassigned.
     *
     * @param row    number of row starting from 0
     * @param column number of column starting from 0
     * @param value  value to assign to cell at position [row][column]
     * @throws IllegalFieldValueException if given value cannot be stored in this instance of
     *                                    SudokuBoard
     */
    public void set(final int row, final int column, final int value) {
        this.board[row][column].setFieldValue(value);
    }

    /**
     * Gets certain sudoku row by it's index.
     *
     * @param row number of row in sudoku board
     * @return copy of row
     */
    public SudokuRow getRow(final int row) {
        SudokuField[] fields = new SudokuField[boardSize];
        for (int i = 0; i < boardSize; i++) {
            fields[i] = board[row][i];
        }
        return new SudokuRow(fields);
    }

    /**
     * Gets certain sudoku column by it's index.
     *
     * @param column number of column in sudoku board
     * @return copy of column
     */
    public SudokuColumn getColumn(final int column) {
        SudokuField[] fields = new SudokuField[boardSize];
        for (int i = 0; i < boardSize; i++) {
            fields[i] = board[i][column];
        }

        return new SudokuColumn(fields);
    }

    /**
     * Gets sudokuBox indicated by coordinates.
     *
     * @param row    number of row in sudoku board
     * @param column number of column in sudoku board
     * @return copy of indicated sudokuBox
     */
    public SudokuBox getBox(final int row, final int column) {
        int firstRowInBox = row - (row % boxSize);
        int firstColumnInBox = column - (column % boxSize);

        SudokuField[] fields = new SudokuField[boardSize];
        int index = 0;
        for (int i = 0; i < boxSize; i++) {
            for (int j = 0; j < boxSize; j++) {
                fields[index++] =
                        board[firstRowInBox + i][firstColumnInBox + j];
            }
        }

        return new SudokuBox(fields);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board", board)
                .toString();
    }

    /**
     * Checks if two boards are the same.
     * Checks if values in the board are the same
     * (then returns true) but returns false also when
     * given object is a different class, null or has
     * different board size (for future development).
     * DOESN'T depend on sudoku solver.
     *
     * @param o object to compare
     * @return true if content of array is the same, otherwise return false
     */
    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuBoard)) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder()
                .append(board, that.board)
                .isEquals();
    }

    /**
     * Generates hash code of sudoku board.
     * Depends on content of the board,
     * NOT on the sudoku solver.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37)
                .append(board)
                .toHashCode();
    }

    /**
     * Returns deep copy of this SudokuBoard.
     * SudokuSolver not considered because has no mutable elements.
     *
     * @return deep copy
     */
    @Override
    public SudokuBoard clone() {
        SudokuBoard clone = new SudokuBoard(sudokuSolver, boardSizeEnum);

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                int fieldValue = this.get(row, column);
                clone.set(row, column, fieldValue);
            }
        }
        return clone;
    }
}
