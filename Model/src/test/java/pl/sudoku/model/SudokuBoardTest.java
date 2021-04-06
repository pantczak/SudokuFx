package pl.sudoku.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SudokuBoardTest {

    @Test
    public void solveGame_FillOneBoard_ShouldBeFilledCorrectly() {
        SudokuField[][] board;
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        int boardSize = sudokuBoard.getBoardSize();
        int boxSize = sudokuBoard.getBoxSize();


        sudokuBoard.solveGame();
        board = sudokuBoard.getCopyOfBoard();

        // Choose each number in sudoku board one by one
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                int firstRowInBox = row - (row % boxSize);
                int firstColumnInBox = column - (column % boxSize);
                int value = board[row][column].getFieldValue();

                // Check if chosen value is duplicated in box
                assertFalse(isDuplicatedInBox(board, boxSize, row, column, firstRowInBox, firstColumnInBox, value),
                        "Found duplicate in box starting at row " + firstRowInBox + ", column " + firstColumnInBox);

                // Check if chosen value is duplicated in row
                assertFalse(isDuplicatedInRow(board, boardSize, row, column, value),
                        "Found duplicate in row " + row);

                // Check if chosen value is duplicated in column
                assertFalse(isDuplicatedInColumn(board, boardSize, row, column, value),
                        "Found duplicate in column " + column);

            }
        }
    }

    @Test
    public void solveGame_FillTwoBoards_ShouldBeDifferent() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);

        sudokuBoard1.solveGame();
        sudokuBoard2.solveGame();

        assertNotEquals(sudokuBoard1, sudokuBoard2, "Two solved sudoku boards shouldn't be filled the same");
    }

    @Test
    public void equals_CompareToNull_ShouldReturnFalse() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);

        assertNotEquals(null, sudokuBoard);
    }

    @Test
    public void equals_CompareToString_ShouldReturnFalse() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);

        String test = "Test";

        assertNotEquals(sudokuBoard, test);
    }

    @Test
    public void equals_CompareToItself_ShouldReturnTrue() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);

        assertEquals(sudokuBoard, sudokuBoard);
    }

    @Test
    public void hashCode_TwoEmptyBoards_ShouldReturnTheSameHash() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);

        assertEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode(), "Hash for two empty boards with the same size should be the same.");
    }

    @Test
    public void hashCode_TwoTheSameBoards_ShouldReturnTheSameHash() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);

        sudokuBoard1.set(3, 3, 4);
        sudokuBoard2.set(3, 3, 4);

        assertEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode(), "Hash for two empty boards with the same size should be the same.");
    }

    @Test
    public void hashCode_TwoDifferentBoards_ShouldReturnTheDifferentHashes() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);

        sudokuBoard1.solveGame();
        sudokuBoard2.solveGame();

        assertNotEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode(), "Hash for two filled boards should be different.");
    }

    private boolean isDuplicatedInColumn(SudokuField[][] board, int columnSize, int row, int column, int number) {
        for (int r = 0; r < columnSize; r++) {
            if (r != row) {
                if (board[r][column].getFieldValue() == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDuplicatedInRow(SudokuField[][] board, int rowSize, int row, int column, int number) {
        for (int c = 0; c < rowSize; c++) {
            if (c != column) {
                if (board[row][c].getFieldValue() == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDuplicatedInBox(SudokuField[][] board, int boxSize, int row, int column, int firstRowInBox, int firstColumnInBox, int number) {
        for (int r = firstRowInBox; r < firstRowInBox + boxSize; r++) {
            for (int c = firstColumnInBox; c < firstColumnInBox + boxSize; c++) {
                if (r != row && c != column) {
                    if (board[r][c].getFieldValue() == number) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Test
    public void toString_EmptyBoard_ReturnsProperValues() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);

        assertThat(sudokuBoard.toString(), containsString("value=0"));
    }

    @Test
    public void clone_CompareOriginalWithClone_ShouldBeEqual() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        sudokuBoard.solveGame();
        SudokuBoard sudokuBoardClone = sudokuBoard.clone();

        assertEquals(sudokuBoard, sudokuBoardClone);
    }

    @Test
    public void clone_ModifyClone_ShouldNotChangeOriginal() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        sudokuBoard.solveGame();
        SudokuBoard sudokuBoardClone = sudokuBoard.clone();

        sudokuBoardClone.set(0, 0, 0);

        assertNotEquals(0, sudokuBoard.get(0, 0));
    }

}