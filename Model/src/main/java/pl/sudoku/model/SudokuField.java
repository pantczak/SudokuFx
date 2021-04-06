package pl.sudoku.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class SudokuField implements Serializable, Comparable<SudokuField>, Cloneable {
    /**
     * Represents value stored inside sudoku field.
     */
    private int value;

    /**
     * Ctor for sudoku field.
     */
    public SudokuField() {
    }

    /**
     * Ctor for sudoku field with stating value.
     *
     * @param newValue to store inside field
     */
    public SudokuField(final int newValue) {
        this.value = newValue;
    }

    /**
     * Gets value stored in sudoku field.
     *
     * @return integer value
     */
    public int getFieldValue() {
        return value;
    }

    /**
     * Sets value of sudoku field.
     *
     * @param newValue to be stored
     */
    public void setFieldValue(final int newValue) {
        this.value = newValue;
    }

    /**
     * Checks if two fields are the same.
     * Checks if values in the groups are the same
     * (then returns true) but returns false also when
     * given object is a different class or null.
     *
     * @param o object to compare
     * @return true if value in fields are the same, otherwise false
     */
    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuField)) {
            return false;
        }

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    /**
     * Generates hash code of SudokuField object.
     * Depends only on value in field.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            throw new NullPointerException();
        } else {
            return (this.value - o.getFieldValue());
        }
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }
}
