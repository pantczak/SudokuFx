package pl.sudoku.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class SudokuRowTest {

    private SudokuField[] content = new SudokuField[]{
            new SudokuField(1),
            new SudokuField(2),
            new SudokuField(3),
            new SudokuField(4),
            new SudokuField(5),
            new SudokuField(6),
            new SudokuField(7),
            new SudokuField(8),
            new SudokuField(9)};

    @Test
    public void equals_CompareToDifferentClassWithTheSameContent_ShouldReturnFalse() {
        SudokuRow sudokuRow = new SudokuRow(content);
        SudokuColumn sudokuColumn = new SudokuColumn(content);

        assertNotEquals(sudokuRow, sudokuColumn);
    }

    @Test
    public void equals_CompareToRowWithTheSameContent_ShouldReturnTrue() {
        SudokuRow sudokuRow = new SudokuRow(content);
        SudokuRow sudokuRow1 = new SudokuRow(content);

        assertEquals(sudokuRow, sudokuRow1);
    }

    @Test
    public void equals_CompareToRowWithDifferentContent_ShouldReturnFalse() {
        SudokuRow sudokuRow = new SudokuRow(new SudokuField[]{
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(8),
                new SudokuField(9)});

        SudokuRow sudokuRow1 = new SudokuRow(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)});
        assertNotEquals(sudokuRow, sudokuRow1);
    }

    @Test
    public void clone_CompareOriginalWithClone_ShouldBeEqual() throws CloneNotSupportedException {
        SudokuRow sudokuRow = new SudokuRow(content);
        SudokuRow sudokuRowClone = sudokuRow.clone();

        assertEquals(sudokuRow, sudokuRowClone);
    }

}