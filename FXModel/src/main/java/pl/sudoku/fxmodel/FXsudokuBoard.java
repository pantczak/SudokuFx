package pl.sudoku.fxmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.sudoku.model.SudokuBoard;

public class FXsudokuBoard {

    public static final String FIELD_VALUE_PROPERTY = "fieldValue";

    /**
     * PropertyChangeSupport for implementing Observer/Observable pattern.
     */
    private final transient PropertyChangeSupport propertyChangeSupport;

    /**
     * Placeholder containing SudokuBoard instance for integration
     * with javaFX bidirectional binding.
     */
    private SudokuBoard sudokuBoardPlaceholder;

    /**
     * Constructor for FXsudokuBoard.
     *
     * @param sudokuBoardPlaceholder SudokuBoard to integrate
     */
    public FXsudokuBoard(SudokuBoard sudokuBoardPlaceholder) {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.sudokuBoardPlaceholder = sudokuBoardPlaceholder;
    }


    /**
     * Add a PropertyChangeListener for a specific property.
     *
     * @param propertyName property that will fire propertyChange.
     * @param listener     listens to value change
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a PropertyChangeListener for a specific property.
     *
     * @param propertyName -> property that listener will be removed from
     * @param listener     -> listener to remove
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Returns an array of all the listeners that were added to the
     * PropertyChangeSupport object with addPropertyChangeListener().
     *
     * @return array of all the listeners
     */
    public PropertyChangeListener[] getAllListeners() {
        return propertyChangeSupport.getPropertyChangeListeners();
    }

    /**
     * Returns SudokuBoard stored in placeholder.
     *
     * @return SudokuBoard
     */
    public SudokuBoard getSudokuBoardPlaceholder() {
        return sudokuBoardPlaceholder;
    }

    /**
     * Sets a new SudokuBoard to be stored in placeholder.
     * Swaps all values independently.
     *
     * @param newBoard SudokuBoard to swap
     */
    public void setSudokuBoardPlaceholder(SudokuBoard newBoard) {
        SudokuBoard oldBoard = getSudokuBoardPlaceholder();
        for (int row = 0; row < oldBoard.getBoardSize(); row++) {
            for (int column = 0; column < oldBoard.getBoardSize(); column++) {
                set(row, column, newBoard.get(row, column));
            }
        }
    }

    /**
     * Returns value in sudoku board cell at certain position.
     * 0 means that this cell is unassigned.
     *
     * @param row    number of row starting from 0
     * @param column number of column starting from 0
     * @return value in cell at at position [row][column]
     */
    public int get(int row, int column) {
        return sudokuBoardPlaceholder.get(row, column);
    }

    /**
     * Sets value in sudoku board cell at certain position.
     * 0 means that this cell is unassigned.
     *
     * @param row    number of row starting from 0
     * @param column number of column starting from 0
     * @param value  value to assign to cell at position [row][column]
     */
    public void set(int row, int column, int value) {
        int boardSize = sudokuBoardPlaceholder.getBoardSize();
        int oldValue = sudokuBoardPlaceholder.get(row, column);
        sudokuBoardPlaceholder.set(row, column, value);
        int index = row * boardSize + column;
        propertyChangeSupport.fireIndexedPropertyChange(
                FIELD_VALUE_PROPERTY, index, oldValue, value);
    }

    /**
     * Checks if two placeholders(SudokuBoards) are the same.
     *
     * @param o object to compare
     * @return true if content is the same, otherwise return false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof FXsudokuBoard)) {
            return false;
        }

        FXsudokuBoard that = (FXsudokuBoard) o;

        return new EqualsBuilder()
                .append(sudokuBoardPlaceholder, that.sudokuBoardPlaceholder)
                .isEquals();
    }

    /**
     * Generates hash code of sudokuBoardPlaceholder.
     * Depends on content of the placeholder.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sudokuBoardPlaceholder)
                .toHashCode();
    }

    /**
     * Stringifies placeholder and instance ID.
     *
     * @return String with values
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sudokuBoardPlaceholder", sudokuBoardPlaceholder)
                .toString();
    }

}
