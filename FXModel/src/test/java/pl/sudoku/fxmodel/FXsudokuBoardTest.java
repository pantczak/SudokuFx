package pl.sudoku.fxmodel;

import org.junit.jupiter.api.Test;
import pl.sudoku.model.BacktrackingSudokuSolver;
import pl.sudoku.model.BoardSizeEnum;
import pl.sudoku.model.SudokuBoard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FXsudokuBoardTest {

    @Test
    public void addPropertyChangeListener_ShouldAddListener_Correctly() {
        FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));
        fxSudokuBoard.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

            }
        });
        assertEquals(1, fxSudokuBoard.getAllListeners().length);
    }

    @Test
    public void removePropertyChangeListener_ShouldRemoveListener_Correctly() {
        FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));
        PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {

            }
        };
        fxSudokuBoard.addPropertyChangeListener("value", propertyChangeListener);
        fxSudokuBoard.removePropertyChangeListener("value", propertyChangeListener);

        assertEquals(0, fxSudokuBoard.getAllListeners().length);
    }

    @Test
    public void getSudokuBoardPlaceholder_ShouldRetrieveWholePlaceholder_Correctly() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        sudokuBoard.solveGame();
        FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(sudokuBoard);
        assertEquals(sudokuBoard, fxSudokuBoard.getSudokuBoardPlaceholder());
    }

    @Test
    public void setSudokuBoardPlaceholder_ShouldChangeWholePlaceholder_Correctly() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        sudokuBoard.solveGame();
        FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));
        FXsudokuBoard FXsudokuBoard1 = new FXsudokuBoard(sudokuBoard);
        fxSudokuBoard.setSudokuBoardPlaceholder(sudokuBoard);
        assertEquals(FXsudokuBoard1, fxSudokuBoard);
    }

    @Test
    public void get_ShouldRetrievePlaceholderValue_Correctly() {
        int someRow = 3;
        int someColumn = 3;
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        sudokuBoard.solveGame();
        FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(sudokuBoard);
        assertEquals(sudokuBoard.get(someRow, someColumn), fxSudokuBoard.get(someRow, someColumn));
    }

    @Test
    public void set_ShouldChangePlaceholderValue_Correctly() {
        int someRow = 3;
        int someColumn = 3;
        int someValue = 8;
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC);
        FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(sudokuBoard);
        fxSudokuBoard.set(someRow, someColumn, someValue);
        assertEquals(someValue, fxSudokuBoard.get(someRow, someColumn));
    }

    @Test
    public void equals_CompareToNull_ShouldReturnFalse() {
        FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));

        assertNotEquals(null, fxSudokuBoard);
    }

    @Test
    public void equals_CompareToString_ShouldReturnFalse() {
        FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));

        String test = "Test";

        assertNotEquals(fxSudokuBoard, test);
    }

    @Test
    public void equals_CompareToItself_ShouldReturnTrue() {
        FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));

        assertEquals(fxSudokuBoard, fxSudokuBoard);
    }

    @Test
    public void hashCode_TwoEmptyBoards_ShouldReturnTheSameHash() {
        FXsudokuBoard FXsudokuBoard2 = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));
        FXsudokuBoard FXsudokuBoard1 = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));

        assertEquals(FXsudokuBoard1.hashCode(), FXsudokuBoard2.hashCode(), "Hash for two empty boards with the same size should be the same.");
    }

    @Test
    public void hashCode_TwoTheSameBoards_ShouldReturnTheSameHash() {
        FXsudokuBoard FXsudokuBoard2 = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));
        FXsudokuBoard FXsudokuBoard1 = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));

        FXsudokuBoard1.set(3, 3, 4);
        FXsudokuBoard2.set(3, 3, 4);

        assertEquals(FXsudokuBoard1.hashCode(), FXsudokuBoard2.hashCode(), "Hash for two empty boards with the same size should be the same.");
    }

    @Test
    public void hashCode_TwoDifferentBoards_ShouldReturnTheDifferentHashes() {
        FXsudokuBoard FXsudokuBoard2 = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));
        FXsudokuBoard FXsudokuBoard1 = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));

        FXsudokuBoard1.getSudokuBoardPlaceholder().solveGame();
        FXsudokuBoard2.getSudokuBoardPlaceholder().solveGame();

        assertNotEquals(FXsudokuBoard1.hashCode(), FXsudokuBoard2.hashCode(), "Hash for two filled boards should be different.");
    }

    @Test
    public void toString_EmptyBoard_ReturnsProperValues() {
        FXsudokuBoard FXsudokuBoard2 = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(), BoardSizeEnum.CLASSIC));

        assertThat(FXsudokuBoard2.toString(), containsString("value=0"));
    }

}
