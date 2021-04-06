package pl.sudoku.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SudokuFieldGroupTest {

    @Test
    public void verify_ValidGroup_ShouldReturnTrue() {
        SudokuRow sudokuRow = new SudokuRow(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)});
        assertTrue(sudokuRow.verify());
    }

    @Test
    public void verify_InvalidGroup_ShouldReturnFalse() {
        SudokuRow sudokuRow = new SudokuRow(new SudokuField[]{
                new SudokuField(2),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)});
        assertFalse(sudokuRow.verify());
    }

    @Test
    public void equals_CompareToGroupWithDifferentSize_ShouldReturnFalse() {
        SudokuRow sudokuRow = new SudokuRow(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8)});
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
    public void equals_CompareToNull_ShouldReturnFalse() {
        SudokuRow sudokuRow = new SudokuRow(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)});
        assertNotEquals(null, sudokuRow);
    }

    @Test
    public void equals_CompareToItself_ShouldReturnTrue() {
        SudokuRow sudokuRow = new SudokuRow(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)});
        assertEquals(sudokuRow, sudokuRow);
    }

    @Test
    public void equals_CompareToDifferentClass_ShouldReturnFalse() {
        SudokuRow sudokuRow = new SudokuRow(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)});

        String string = "test";
        assertNotEquals(sudokuRow, string);
    }

    @Test
    void hashCode_TwoGroupsWithTheSameContent_ShouldReturnTheSameHash() {
        SudokuField[] content = new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)};

        SudokuColumn sudokuColumn = new SudokuColumn(content);
        SudokuBox sudokuBox = new SudokuBox(content);

        assertEquals(sudokuColumn.hashCode(), sudokuBox.hashCode(), "Hashes for two groups with the same content should be equal.");
    }

    @Test
    void hashCode_TwoGroupsWithDifferentContent_ShouldReturnDifferentHash() {
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
        assertNotEquals(sudokuRow.hashCode(), sudokuRow1.hashCode(), "Hashes for two different groups should be different.");
    }

    @Test
    void ToString_Contains_List_Values() {
        SudokuRow sudokuRow = new SudokuRow(new SudokuField[]{
                new SudokuField(1),
                new SudokuField(2),
                new SudokuField(3),
                new SudokuField(4),
                new SudokuField(5),
                new SudokuField(6),
                new SudokuField(7),
                new SudokuField(8),
                new SudokuField(9)});
        assertThat(sudokuRow.toString(), containsString("value=4"));
    }
}