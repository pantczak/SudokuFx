package pl.sudoku.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SudokuFieldTest {

    @Test
    public void getFieldValue_DefaultValue_ShouldReturn0() {
        SudokuField sudokuField = new SudokuField();

        assertEquals(sudokuField.getFieldValue(), 0);
    }

    @Test
    public void setFieldValue_SetTo5_GetterShouldReturn5() {
        SudokuField sudokuField = new SudokuField();

        sudokuField.setFieldValue(5);
        assertEquals(sudokuField.getFieldValue(), 5);
    }

    @Test
    public void equals_CompareToItself_ShouldReturnTrue() {
        SudokuField sudokuField = new SudokuField();

        assertEquals(sudokuField, sudokuField);
    }

    @Test
    public void equals_CompareToInstanceOfDifferentClass_ShouldReturnFalse() {
        SudokuField sudokuField = new SudokuField();
        String string = "test";

        assertNotEquals(sudokuField, string);
    }

    @Test
    public void hashCode_TwoTheSameFields_ShouldReturnTheSameHash() {
        SudokuField sudokuField = new SudokuField(4);
        SudokuField sudokuField1 = new SudokuField(4);

        assertEquals(sudokuField.hashCode(), sudokuField1.hashCode(), "Hash for two fields with the same value should be the same.");
    }

    @Test
    public void hashCode_TwoDifferentFields_ShouldReturnTheDifferentHashes() {
        SudokuField sudokuField = new SudokuField(4);
        SudokuField sudokuField1 = new SudokuField(9);

        assertNotEquals(sudokuField.hashCode(), sudokuField1.hashCode(), "Hash for two fields with the different values should not be the same.");
    }

    @Test
    public void compareTo_CompareToLower_ShouldReturnPositiveValue() {
        SudokuField sudokuField = new SudokuField(4);
        SudokuField sudokuField1 = new SudokuField(2);

        assertTrue(sudokuField.compareTo(sudokuField1) > 0);
    }

    @Test
    public void compareTo_CompareToEqual_ShouldReturnZero() {
        SudokuField sudokuField = new SudokuField(4);
        SudokuField sudokuField1 = new SudokuField(4);

        assertTrue(sudokuField.compareTo(sudokuField1) == 0);
    }

    @Test
    public void compareTo_CompareToHigher_ShouldReturnPositiveValue() {
        SudokuField sudokuField = new SudokuField(4);
        SudokuField sudokuField1 = new SudokuField(6);

        assertTrue(sudokuField.compareTo(sudokuField1) < 0);
    }

    @Test
    public void compareTo_CompareToNull_ShouldThrowNullPointerException () {
        SudokuField sudokuField = new SudokuField(4);

        assertThrows(NullPointerException.class, () -> {
            sudokuField.compareTo(null);
        });
    }

    @Test
    public void clone_CompareFields_ShouldBeEqual() throws CloneNotSupportedException {
        SudokuField sudokuField = new SudokuField(1);
        SudokuField sudokuFieldClone = sudokuField.clone();

        assertEquals(sudokuField, sudokuFieldClone);
    }

    @Test
    public void clone_ModifyClone_ShouldNotModifyOriginal() throws CloneNotSupportedException {
        SudokuField sudokuField = new SudokuField(1);
        SudokuField sudokuFieldClone = sudokuField.clone();
        sudokuFieldClone.setFieldValue(2);

        assertEquals(1, sudokuField.getFieldValue());
    }


}