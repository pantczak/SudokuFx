package pl.sudoku.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class SudokuBoxTest {

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
        SudokuBox sudokuBox = new SudokuBox(content);
        SudokuRow sudokuRow = new SudokuRow(content);

        assertNotEquals(sudokuBox, sudokuRow);
    }

    @Test
    public void equals_CompareToBoxWithTheSameContent_ShouldReturnTrue() {
        SudokuBox sudokuBox = new SudokuBox(content);
        SudokuBox sudokuBox1 = new SudokuBox(content);

        assertEquals(sudokuBox, sudokuBox1);
    }

    @Test
    public void equals_CompareToBoxWithDifferentContent_ShouldReturnFalse() {
        SudokuBox sudokuBox = new SudokuBox(new SudokuField[]{
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(8),
                new SudokuField(9)});

        SudokuBox sudokuBox1 = new SudokuBox(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)});
        assertNotEquals(sudokuBox, sudokuBox1);
    }

    @Test
    public void clone_CompareOriginalWithClone_ShouldBeEqual() throws CloneNotSupportedException {
        SudokuBox sudokuBox = new SudokuBox(content);
        SudokuBox sudokuBoxClone = sudokuBox.clone();

        assertEquals(sudokuBox, sudokuBoxClone);
    }
}